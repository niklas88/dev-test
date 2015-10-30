#!/bin/sh
mvn compile assembly:single
cp target/devtest-*-SNAPSHOT-jar-with-dependencies.jar GoEuroTest.jar
