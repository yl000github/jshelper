var JRobotUtil={
		instance:null,
		init:function(){
			if(!this.instance){
				var JRobotEnabler=require("jrobot.enabler.JRobotEnabler");
				this.instance=new JRobotEnabler();
			}
		},
		shortCut:function(code){
			this.instance.shortCut(code);
		},
		inputText:function(src){
			this.instance.inputText(src);
		},
		mouseClick:function(type,times){
			this.instance.mouseClick(type,times);
		},
		getRegionText:function(sX, sY, eX, eY){
			this.instance.getRegionText(sX, sY, eX, eY);
		},
		mouseMove:function(x,y,isPress){
			this.instance.mouseMove(x,y,isPress);
		},
		keyClick:function(code){
			this.instance.keyClick(code);
		},
		sleep:function(t){
			this.instance.sleep(t);
		},
		
}
JRobotUtil.init();