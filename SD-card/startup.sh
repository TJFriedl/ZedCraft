#!/bin/bash
#START MINECRAFT SERVER
cd server/
#START INPUT SCRIPT
./server.sh &

#START MINECRAFT BACKDOOR
./backdoor.sh &
