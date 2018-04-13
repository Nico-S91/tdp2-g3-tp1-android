package tp1.g3.tdp2.hoycomo.Exceptions;

public class UnknownException extends BusinessException {

    public UnknownException(String msg, Throwable e) {
        super("No se sabe el motivo", e);
    }

    public UnknownException(String msg, Integer status) {
        super("No se sabe el motivo [status:"+status+"] - " + msg);
    }
}
