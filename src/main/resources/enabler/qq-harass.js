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
	var talkList=[
	              'hello',
	              'yang',
	              'you',
	              'are',
	              'best',
	              ];
	var gap=1000;
	talkList.forEach(function(talk){
		JRobotUtil.inputText(talk);
		JRobotUtil.shortCut("qqSend");
		JRobotUtil.sleep(gap);
	});
	$_response_$={
			c:"ok"
	}
})($_request_param_$)