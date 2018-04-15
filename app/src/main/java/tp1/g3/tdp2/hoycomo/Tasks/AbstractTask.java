package tp1.g3.tdp2.hoycomo.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import tp1.g3.tdp2.hoycomo.Helpers.ToastMessage;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;


import tp1.g3.tdp2.hoycomo.Helpers.MyData;
import tp1.g3.tdp2.hoycomo.Helpers.ToastMessage;
import tp1.g3.tdp2.hoycomo.Server.ApiRestConsumer;

public abstract class AbstractTask<Params,Process,Return,Reference> extends AsyncTask<Params,Process,Return> implements ApiRestConsumer.ResponseParse {

    protected final ApiRestConsumer apiRestConsumer;

    protected WeakReference<Reference> weakReference;

    public AbstractTask(Reference ref) {
        weakReference = new WeakReference<Reference>(ref);
        apiRestConsumer = new ApiRestConsumer(this);
    }

    protected final boolean isOnline(Context ctx) {
        boolean isOnline = ApiRestConsumer.isOnline(ctx);
        if (!isOnline) {
            ToastMessage.toastMessage(ctx, "Estás desconectado!");
        }
        return isOnline;
    }

    protected final Map<String, String> withAuth(Context ctx, Map<String, String> headers) {
        MyData myData = new MyData(ctx);
        String token = myData.get("token","");
        if (!token.isEmpty()) {
            if (headers == null) headers = new HashMap<>();
            headers.put("authorization","authorization: "+token);
            Log.e("header_authorization",headers.get("authorization"));
        } else {
            Log.e("auth_error_empty", "El token es vacio!");
        }
        return headers;
    }

    protected final Map<String, String> withAuth(Context ctx) {
        return withAuth(ctx, null);
    }

}
