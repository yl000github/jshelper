(function(request){
	load("/lib/common.js");
	load("/lib/JRobotUtil.js");
//	var filePath=request.filePath;
//	JRobotUtil.shortCut("switchDesk");
	
//	JRobotUtil.shortCut("switchPage");
//	JRobotUtil.mouseMove(724,282,false);
//	JRobotUtil.mouseClick("left",10);
	
	JRobotUtil.shortCut("switchPage");
	JRobotUtil.mouseMove(0.5,0.5,true);
	for (var i = 0; i < 17; i++) {
		JRobotUtil.keyClick("Tab");
		var n=parseInt(Math.random()*1000);
		JRobotUtil.inputText(n);
	}
	$_response_$={
			c:"ok"
	}
})($_request_param_$)