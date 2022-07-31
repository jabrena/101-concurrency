# 101 concurrency

Detecting concurrency issues with JCStress

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=jabrena_101-concurrency)
[![Java CI](https://github.com/jabrena/101-concurrency/actions/workflows/build.yml/badge.svg)](https://github.com/jabrena/101-concurrency/actions/workflows/build.yml)

## How to run in local

```
sdk env
./gradlew clean build jcstress
./gradlew clean jcstress --tests "Calculator4Test"
./gradlew clean build

./gradlew clean bootRun
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs

java -Xms256m -Xmx256m -XX:+UseParallelGC -jar build/libs/101-concurrency-0.1.0-SNAPSHOT.jar
sdk install jmeter
jmeter -t jmeterConf.jmx
jmeter -t jmeterConf.jmx -n

sdk install visualvm
visualvm --jdkhome  $JAVA_HOME

brew install python
python3 --version
pip3 install bzt
bzt taurusConf.yaml
```

## Documentation

```
sdk env
jwebserver -p 8000 -d  $PWD/docs
```

## References

- https://plugins.gradle.org/plugin/io.github.reyerizo.gradle.jcstress
- https://github.com/reyerizo/jcstress-gradle-plugin
- https://wiki.openjdk.org/display/shenandoah/Main
