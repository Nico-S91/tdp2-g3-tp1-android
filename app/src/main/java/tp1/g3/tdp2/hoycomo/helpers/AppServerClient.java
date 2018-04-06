package tp1.g3.tdp2.hoycomo.helpers;

import com.loopj.android.http.*;

//clase estatica para el clioente rest que se va a comunicar con el server
public class AppServerClient {
    private static final String BASE_URL = "https://api.server.com/v1/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
