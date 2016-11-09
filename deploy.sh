#!/bin/sh
export JAVA_HOME=/usr/lib/jvm/java-8-oracle/
mvn clean
mvn -e package spring-boot:repackage
cp target/smtplistener.war $1
