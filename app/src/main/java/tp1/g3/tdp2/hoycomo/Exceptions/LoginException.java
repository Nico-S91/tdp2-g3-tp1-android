package tp1.g3.tdp2.hoycomo.Exceptions;

public class LoginException extends BusinessException {

    public LoginException(String msg, Throwable e) {
        super("Parece que quieres intentar colarte! Email o pass incorrectos.", e);
    }

    public LoginException(String msg, Integer status) {
        super("Parece que quieres intentar colarte! " + msg);
    }
}
