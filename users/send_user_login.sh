#!/usr/bin/env bash

curl -i -X POST -H "Content-Type:application/json" -d '{  "userName" : "mschmidt", "password" : "12345678" }' http://localhost:8080/userservice/login
