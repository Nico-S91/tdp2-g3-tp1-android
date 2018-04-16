package tp1.g3.tdp2.hoycomo.helpers;

import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.entity.StringEntity;

//clase estatica para el clioente rest que se va a comunicar con el server
public class AppServerClient {
    private static final String BASE_URL = "https://thawing-cliffs-51834.herokuapp.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postJson(Context context, String url, StringEntity JsonObject, String requestType, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), JsonObject, requestType, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
