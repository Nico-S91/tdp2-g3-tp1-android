package tp1.g3.tdp2.hoycomo.Exceptions;

import android.util.Log;

public class BusinessException extends RuntimeException {

    private BusinessException(String msg, Boolean logError, Throwable e) {
        super(msg, e);
        if (logError) Log.e("business_error",msg,this);
    }

    public BusinessException(String msg) {
        super(msg);
        Log.d("business_error", msg, this);
    }

    public BusinessException(String msg, Integer status) {
        super("[status:"+status+"] - "+msg);
        Log.d("business_error", msg, this);
    }

    public BusinessException(String msg, Throwable e) {
        this(msg, true, e);
    }
}
