(function(request){
	load("/lib/common.js");
	load("/lib/JRobotUtil.js");
//	var filePath=request.filePath;
//	JRobotUtil.shortCut("switchDesk");
	
//	JRobotUtil.shortCut("switchPage");
//	JRobotUtil.mouseMove(724,282,false);
//	JRobotUtil.mouseClick("left",10);
	
	JRobotUtil.shortCut("switchPage");
	var rowNum=1;
	for (var i = 0; i < rowNum; i++) {
		for (var j = 1; j < 6; j++) {
			JRobotUtil.mouseMove(j/6,0.5,false);
			JRobotUtil.mouseClick("right",1);
			JRobotUtil.sleep(500);
			JRobotUtil.keyClick("V");
			JRobotUtil.sleep(500);
			JRobotUtil.keyClick("Enter");
			JRobotUtil.sleep(500);
		}
	}
	$_response_$={
			c:"ok"
	}
})($_request_param_$)