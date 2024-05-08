#!/bin/bash
#Make sure test.sock is gone
rm /tmp/test.sock
#Make a new First-in First-out test.sock
mkfifo /tmp/test.sock
while true; do
    cat /tmp/test.sock 
done | java -Xmx400M -Xms400M -jar server.jar
# Cat anything in the socket and put it into the server.jar executable
#This will also start the server with 400Megabytes of RAM
