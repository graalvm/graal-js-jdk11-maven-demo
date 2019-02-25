# Running GraalJS on stock JDK11

This is a simple maven project that demonstrates how it's possible to run
[GraalJS](http://www.graalvm.org/docs/reference-manual/languages/js/) on a
stock JDK11. The application is a simple JavaScript benchmark embedded in a
Java application which compares performance of GraalJS and Nashorn.

## Pre requirements

- Linux or Mac OS
- [Maven](https://maven.apache.org)
- [JDK11](https://jdk.java.net/11/)

## Setup

- Clone this repository
```
git clone https://github.com/graalvm/graal-js-jdk11-maven-demo
```

- Move to the newly cloned directory
```
cd graal-js-jdk11-maven-demo
```

- Make sure that JAVA_HOME is pointed at a JDK11
```
export JAVA_HOME=/path/to/jdk11
```

- Package the project using Maven
```
mvn package
```

## Execution

This project provides two execution setups (using the
[exec-maven-plugin](https://www.mojohaus.org/exec-maven-plugin/)). One uses the
Graal compiler to JIT compile JavaScript for better performance, and the other
does not and only interprets the JavaScript code. Both executions output
benchmark results for GraalJS (via the [GraalVM Polyglot
API](https://www.graalvm.org/truffle/javadoc/index.html?com/oracle/truffle/api/instrumentation/EventContext.html)
and the [Java Scripting
API](https://docs.oracle.com/javase/8/docs/technotes/guides/scripting/prog_guide/api.html)) and Nashorn.



To Execute with Graal run
```
mvn exec:exec
```

To Execute without Graal run
```
mvn exec:exec@nograal
```

The benchmark prints the time per iteration in milliseconds, so lower values are better.

## Running on GraalVM

This project is also setup to run on GraalVM. The setup is the same except
that your JAVA_HOME should point to a directory contain GraalVM. In this case,
execution without Graal is not supported.
