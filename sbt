#!/bin/sh

VERSION=0.13.13
LATEST=http://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/${VERSION}/sbt-launch.jar
JAR=.sbtlib/sbt-launch-${VERSION}.jar

if [ ! -d .sbtlib ]; then
  mkdir .sbtlib
fi

if [ ! -f ${JAR} ]; then
  rm .sbtlib/*
  echo "Fetching sbt"
  curl -# -L "${LATEST}" -o "${JAR}"
fi

java \
-Duser.timezone=UTC \
-Djava.awt.headless=true \
-Dfile.encoding=UTF-8 \
-XX:MaxPermSize=256m \
-Xmx1g \
-noverify \
-jar ${JAR} \
"$@"
