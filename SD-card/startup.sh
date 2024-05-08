#!/bin/bash
#START MINECRAFT SERVER
cd /mnt/sd-mmnblk0p1/server/
#START INPUT SCRIPT
./../server.sh &
#WAIT FOR SERVER TO START
sleep 60
#START MINECRAFT BACKDOOR
./../backdoor.sh &
