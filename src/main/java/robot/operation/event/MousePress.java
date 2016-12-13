package robot.operation.event;


public class MousePress  extends AMousePRC{
	public MousePress(){
		flag="NATIVE_MOUSE_PRESSED";
	}
	@Override
	public boolean consume(String msg) throws Exception {
		super.consume(msg);
		robot.mousePress(buttonMapping(button));
//		pause(200);
		return true;
	}
}
