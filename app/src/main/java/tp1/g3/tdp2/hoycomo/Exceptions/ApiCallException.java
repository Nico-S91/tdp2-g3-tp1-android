package tp1.g3.tdp2.hoycomo.Exceptions;

import android.util.Log;

public class ApiCallException extends RuntimeException {

    private ApiCallException(String msg, Boolean logError) {
        super(msg);
        if (logError) Log.e("api_call_error", msg, this);
    }

    public ApiCallException(String url) {
        this("Error en el servidor, invocando [" + url + "]", true);
    }

    public ApiCallException(String url, Integer status) {
        this("Error en el servidor, invocando [" + url + "] status [" + status + "]", true);
    }
}
