package tp1.g3.tdp2.hoycomo.Respuestas;

public class TaskResponse<T> {
    public final boolean success;
    public final int code;
    public final T data;

    public TaskResponse(boolean success, int code, T data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "success=" + success +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
