#!/usr/bin/env bash

# Uncomment below lines if project is not built yet

#echo "Running command: \"mvn clean install -DskipTests=true\""
#
#mvn clean install -DskipTests=true

mode="local"

if [[ $# -eq 0 ]]
  then limit=10
  else limit=$1
fi

echo "Running Players application with arguments: mode=\"$mode\" limit=\"$limit\""

mvn exec:java -pl player-main -Dexec.args="$mode $limit"
