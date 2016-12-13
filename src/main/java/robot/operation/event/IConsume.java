package robot.operation.event;

public interface IConsume {
	public boolean consume(String msg) throws Exception;
}
