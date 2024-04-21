#!/bin/bash
sleep 2
exec 3>&1
exec 2>&1
tmpPath=/tmp/temp.txt
sockPath=/tmp/test.sock
echo $tmpPath
exec 1>$tmpPath


tail -f logs/latest.log | grep ">>" | sed 's/.*>>//' | while read p; do
        echo hi>&3 
	command=$p
        $command
        exec 1>&3

        tmp=`cat $tmpPath`
        #echo ===== $tmp
        #prepend with say for minecraft
        cat <<<"say " $tmpPath | tr '\n' ' ' > $sockPath

        exec 1>$tmpPath

  
  
done 