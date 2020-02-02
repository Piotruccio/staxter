#!/usr/bin/env bash

# Uncomment below lines if project is not built yet

#echo "Running command: \"mvn clean install -DskipTests=true\""
#
#mvn clean install -DskipTests=true

if [[ $# -lt 5 ]]; then
   echo "Invalid number of arguments supplied: $#";
   exit;
fi

mode=$1 local_address=$2 remote_address=$3 local_name=$4 remote_name=$5 limit=$6

if [[ ${limit} -eq "" ]]
  then limit=10
fi

echo "Running Players application with arguments: mode=\"$mode\" local_address=\"$local_address\" remote_address=\"$remote_address\" local_name=\"$local_name\" remote_name=\"$remote_name\" limit=\"$limit\""

mvn exec:java -pl player-main -Dexec.args="$mode $local_address $remote_address $local_name $remote_name $limit"
