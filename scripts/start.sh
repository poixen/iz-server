#!/bin/sh
export PUBLIC_PORT=8080
exec java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Xms128M -Xmx128M -Xss228k -jar iz-server.jar
