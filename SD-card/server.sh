#!/bin/bash
rm /tmp/test.sock
mkfifo /tmp/test.sock
while true; do
    cat /tmp/test.sock 
done | java -Xmx512M -Xms512M -jar server.jar
# | java -jar at the end of the file