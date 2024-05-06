#!/bin/bash
rm /tmp/test.sock
mkfifo /tmp/test.sock
while true; do
    cat /tmp/test.sock 
done | java -Xmx400M -Xms400M -jar server.jar
# | java -jar at the end of the file