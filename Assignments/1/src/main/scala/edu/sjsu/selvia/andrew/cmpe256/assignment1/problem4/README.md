# Problem 4

## Running on the SJSU HPC cluster

### Foundational set up

#### Access the HPC cluster

1. Install & set up [the SJSU VPN client](https://www.sjsu.edu/it/services/network/vpn/index.php)
2. `ssh 014547273@coe-hpc1.sjsu.edu`

#### Install SDKMAN!

Follow the [SDKMAN! installation instructions](https://sdkman.io/install):
```
curl -s "https://get.sdkman.io" | bash
source ~/.sdkman/bin/sdkman-init.sh
```

#### Install dependencies

Determine which dependency versions to install by referencing the Spark shell:
```
/scratch/spark/bin/spark-shell
```

If your dependency versions do not match the versions in the Spark shell, your code will break. Currently, the Spark shell lists the following dependency versions:
```
Using Scala version 2.11.12 (OpenJDK 64-Bit Server VM, Java 1.8.0_242)
```

So, download Java, Scala, and SBT similarly to the following (taking care to respect the current versions of each):
```
sdk list java
sdk install java 8.0.242.hs-adpt
sdk install sbt
sdk list scala
sdk install scala 2.11.12
```

#### Get the IP address

You will need the IP address of your head node in order to `scp` files into your home directory. You can obtain it via:
```
ip -4 address | grep em2 | grep inet | awk '{print $2}' | cut -f1 -d"/"
```

#### Customize spark-submit script

By default, the cluster provides a script to kick off a Spark job. You can find it at `/scratch/spark/slurm-spark-submit.sh`. Its contents are:
```
#!/bin/bash
#SBATCH --nodes=1
#SBATCH --mem-per-cpu=1G
#SBATCH --cpus-per-task=2
#SBATCH --ntasks-per-node=2
#SBATCH --output=sparkjob-%j.out


## --------------------------------------
## 1. Deploy a Spark cluster and submit a job
## --------------------------------------
export SPARK_HOME=/scratch/spark
$SPARK_HOME/deploy-spark-cluster.sh $@
```

I wanted to provision more hardware resources for my jobs, though, so I created a custom script at `~/my-spark-submit.sh`. Its contents are:
```
#!/bin/bash
#SBATCH --nodes=4
#SBATCH --mem-per-cpu=4G
#SBATCH --cpus-per-task=4
#SBATCH --ntasks-per-node=4
#SBATCH --output=sparkjob-%j.out


## --------------------------------------
## 1. Deploy a Spark cluster and submit a job
## --------------------------------------
export SPARK_HOME=/scratch/spark
$SPARK_HOME/deploy-spark-cluster.sh $@
```

### Problem 4 execution

#### Copy ratings.csv to the cluster

I uploaded the data for Problem 4 via `scp`. Additionally, I uploaded my repo with my Spark code.
```
scp ~/Downloads/ratings.csv 014547273@10.31.20.1:~
```

#### Building the JAR

Once you have uploaded your repo, navigate to the directory containing your `build.sbt`. There, you can build a JAR with:
```
sbt package
```

#### Submitting Spark job

I submitted my Spark job using my customized spark-submit script, pointing to my application, and using the JAR I built. 
```
sbatch \
~/my-spark-submit.sh \
--class edu.sjsu.selvia.andrew.cmpe256.assignment1.problem4.Problem4 \
~/cmpe256/Assignments/1/target/scala-2.11/assignment-1_2.11-1.0.jar
```

I tracked the job as it executed with:
```
squeue
```

The logs from the job can be viewed with:
```
cat sparkjob-18685.out
```

#### Copy output back to local machine

In order to submit the assignment, I needed to retrieve the output from the cluster. I ran this from my local machine:
```
scp -r 014547273@10.31.20.1:~/cmpe256/Assignments/1/out/problem4.csv ~/Downloads
```

#### Concatenate part files

Since the result was shuffled into many part files, I concatenated them all on my local machine with:
```
cat part-* > problem4.csv
```

## References

* [CMPE256 HPC PySpark Job Tutorial](https://docs.google.com/document/d/1M4cjvxCqTht9fTX8CtSAvr_cvx8u1LflJ_C26u_IVx8/edit?ts=5e214c29)
* [SJSU HPC](http://coe-hpc-web.sjsu.edu)
* [Spark Quick Start: Self-Contained Applications](https://spark.apache.org/docs/latest/quick-start.html#self-contained-applications)
* [Concatenating part files](https://stackoverflow.com/questions/2150614/concatenating-multiple-text-files-into-a-single-file-in-bash)