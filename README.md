# 101 concurrency

Detecting concurrency issues with JCStress

## How to run

```
./gradlew bootRun
http://localhost:8080/swagger-ui/index.html
./gradlew clean build jcstress
./gradlew clean jcstress --tests "Calculator4Test"
```

## Documentation

```
sdk env
jwebserver -p 8000 -d  $PWD/docs
```

## References

- https://plugins.gradle.org/plugin/io.github.reyerizo.gradle.jcstress
- https://github.com/reyerizo/jcstress-gradle-plugin
