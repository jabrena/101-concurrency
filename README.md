# 101 concurrency

Detecting concurrency issues with JCStress

[![Java CI](https://github.com/jabrena/101-concurrency/actions/workflows/build.yml/badge.svg)](https://github.com/jabrena/101-concurrency/actions/workflows/build.yml)


[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=jabrena_101-concurrency)

[![](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/jabrena/101-concurrency)

## Elevator Pitch

Java is a very popular programming language
with a consistent set of multithreading and multiprocessing features, which solve
all kinds of modern enterprise problems and enable memory sharing across threads as part of the Java
concurrency model.

Developers use popular frameworks like `Spring`, `Quarkus`,
`Micronaut`, `Akka` or `Spark` every day. In some scenarios, it is necessary to use
concurrency, although in reality the learning curve is not easy and sometimes
software gets deployed to cloud environments with some hidden concurrency bugs.

The talk will review the root causes of concurrency issues, and give an
introduction into the Java tool `JCStress` by explaining how to create tests
for Java classes with shared states.

## When

- https://www.meetup.com/nl-NL/amsterdam-java-user-group/events/287658432/

## How to run locally

Running the example locally:

```
sdk env
./gradlew clean build jcstress
./gradlew clean jcstress --tests "Calculator4Test"
./gradlew clean build
./gradlew clean bootRun
```

```
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs
https://editor.swagger.io/
```

Running Visualvm, Jmeter & Taurus to add load:

```
sdk install visualvm
visualvm --jdkhome  $JAVA_HOME

java -Xms256m -Xmx256m -XX:+UseParallelGC -jar build/libs/101-concurrency-0.1.0-SNAPSHOT.jar
sdk install jmeter
jmeter -t jmeterConf.jmx
jmeter -t jmeterConf.jmx -n
jmeter -t jmeterConf2.jmx

brew install python
python3 --version
pip3 install bzt
bzt taurusConf.yaml
```

Running JCStress examples:

```
./gradlew clean jcstress --tests "API_01_Simple"
./gradlew clean jcstress --tests "API_02_Arbiters"
./gradlew clean jcstress --tests "API_04_Nesting"
./gradlew clean jcstress --tests "API_05_SharedMetadata"
./gradlew clean jcstress --tests "API_06_Descriptions"
```

## Documentation

```
sdk env
jwebserver -p 8000 -d  $PWD/docs
```

## References

- https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html
- https://download.oracle.com/otndocs/jcp/memory_model-1.0-pfd-spec-oth-JSpec/
- https://gee.cs.oswego.edu/dl/jmm/cookbook.html
- https://github.com/openjdk/jcstress
- https://github.com/reyerizo/jcstress-gradle-plugin
