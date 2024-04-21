#!/bin/bash
exec 3>&1
exec 2>&1
tmpPath=`pwd`/temp.txt
sockPath=`pwd`/test.sock
echo $tmpPath
exec 1>temp.txt
tail -f example.log | while read p; do
    firstletter=${p:0:1}
    if [ "$firstletter" = ">" ]; then
        command=${p:1}
        $command
        exec 1>&3

        tmp=`cat $tmpPath`
        #echo ===== $tmp
        #prepend with say for minecraft
        cat $tmpPath | tr '\n' ' ' > $sockPath

        exec 1>$tmpPath

    fi 
  
  
done 