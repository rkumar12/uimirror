#!/usr/bin/env bash


port = $1

JAVA_OPTS='-Denv=prod' -port=$port ./location-endpoint & 

echo "$port :  $!" >> /tmp/uimirror.pid


