(function(request){
	load("/lib/common.js");
	var filePath=request.filePath;
	var duration=request.duration;
	var startWait=request.startWait;
	load("/lib/RobotUtil.js");
	filePath="e:/"+filePath;
	RobotUtil.record(filePath,duration,startWait);
	$_response_$={
			c:"ok"
	}
})($_request_param_$)