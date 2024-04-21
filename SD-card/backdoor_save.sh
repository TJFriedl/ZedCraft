#!/bin/bash
sleep 2
exec 3>&1
exec 2>&1
tmpPath=/tmp/temp.txt
sockPath=/tmp/test.sock
echo $tmpPath
exec 1>$tmpPath


tail -f logs/latest.log | while read p; do
	if grep ">>" <<<$p >> /dev/null; then 
		p=`sed 's/.*>>//' <<<$p`  	
        	echo hi>&3
		command=$p
        	$command
	        exec 1>&3

       		 tmp=`cat $tmpPath`
       	 #echo ===== $tmp
       	 #prepend with say for minecraft
		echo -n "say " > $sockPath
	        cat $tmpPath | tr '\n' ' ' > $sockPath
		echo > $sockPath
	        cat "say " $tmpPath | tr '\n' ' ' >&3
	        exec 1>$tmpPath

	fi  
  
done 