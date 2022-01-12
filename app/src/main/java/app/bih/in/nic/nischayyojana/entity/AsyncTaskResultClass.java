package app.bih.in.nic.nischayyojana.entity;

public class AsyncTaskResultClass<T> {
	private T result;
	private Exception error;

	public T getResult() {
		return result;
	}

	public Exception getError() {
		return error;
	}

	public AsyncTaskResultClass(T result) {
		super();
		this.result = result;
	}

	public AsyncTaskResultClass(Exception error) {
		super();
		this.error = error;
	}
}