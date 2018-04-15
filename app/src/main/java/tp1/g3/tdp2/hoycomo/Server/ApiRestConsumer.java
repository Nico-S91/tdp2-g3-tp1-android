package tp1.g3.tdp2.hoycomo.Server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import tp1.g3.tdp2.hoycomo.Exceptions.ApiCallException;
import tp1.g3.tdp2.hoycomo.Exceptions.ErrorMatcher;
import tp1.g3.tdp2.hoycomo.Exceptions.ServerErrorException;
import tp1.g3.tdp2.hoycomo.Helpers.ToastMessage;

public class ApiRestConsumer {

    private static final int DEFAULT_READ_TIMEOUT = 60000; //60s
    private static final int SERVER_TIMEOUT = 60000; //60s

    private final ResponseParse parser;

    public ApiRestConsumer(ResponseParse parser) {
        this.parser = parser;
    }

    public Object get(String endpoint) throws RuntimeException {
        return connect("GET", endpoint, null, null);
    }

    public Object get(String endpoint, Map<String, String> headers) throws RuntimeException {
        return connect("GET", endpoint, null, headers);
    }

    public Object post(String endpoint, String body, Map<String, String> headers) throws RuntimeException {
        return connect("POST", endpoint, body, headers);
    }

    public Object put(String endpoint, String body, Map<String, String> headers) throws RuntimeException {
        return connect("PUT", endpoint, body, headers);
    }

    public Object put(String endpoint, Map<String, String> headers) throws RuntimeException {
        return connect("PUT", endpoint, null, headers);
    }

    public Object delete(String endpoint, String body, Map<String, String> headers) throws RuntimeException {
        return connect("DELETE", endpoint, body, headers);
    }

    public Object delete(String endpoint, Map<String, String> headers) throws RuntimeException {
        return connect("DELETE", endpoint, null, headers);
    }

    private Object connect(String method, String endpoint, String body, Map<String,String> headers) throws RuntimeException {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String json;
        Object expectedReturn = null;
        URL url = null;
        int responseStatus = 0;
        InputStream inputStream = null;
        String errorMsg = null;
        Boolean isError = false;
        try {
            url = new URL(endpoint);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (headers != null) {
                for(String key : headers.keySet()) {
                    urlConnection.setRequestProperty(key, headers.get(key));
                }
            }
            urlConnection.setReadTimeout(DEFAULT_READ_TIMEOUT);
            urlConnection.setConnectTimeout(SERVER_TIMEOUT);
            //writing the request
            if (method == "POST" || method == "PUT" || method=="DELETE") {
                urlConnection.setRequestMethod(method);
                if (body != null) {
/*                    byte[] outputInBytes = body.getBytes("UTF-8");
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(outputInBytes);*/
                    OutputStream os = urlConnection.getOutputStream();
                    InputStream stream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
                    BufferedInputStream bis = new BufferedInputStream(stream, 8 * 1024);
                    byte[] buffer = new byte[8192];
                    int availableByte = 0;
                    while ((availableByte = bis.read(buffer)) != -1) {
                        os.write(buffer, 0, availableByte);
                        os.flush();
                    }
                    os.close();
                }
            } else if (method == "GET") {
                urlConnection.setRequestMethod(method);
            } else {
                throw new ApiCallException(url.getPath());
            }

            //debug
            Log.d("ApiRestCustomer","["+getCurl(method,url,body,headers)+"]" + urlConnection.getConnectTimeout());

            //makes the connection
            urlConnection.connect();

            //reading the response
            responseStatus = urlConnection.getResponseCode();
            errorMsg = urlConnection.getResponseMessage();
            isError = responseStatus >= HttpURLConnection.HTTP_BAD_REQUEST;
            if (isError) {
                inputStream = urlConnection.getErrorStream();
            } else {
                inputStream = urlConnection.getInputStream();
            }

            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return expectedReturn;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            if (buffer.length() == 0) {
                return expectedReturn;
            }
            json = buffer.toString();
            try {
                if (isError) {
                    readErrorResponse(json, urlConnection.getResponseCode());
                } else {
                    expectedReturn = parser.readResponse(json);
                }
            } catch (JSONException e) {
                Log.e("parse_response_error", "Error al leer el response de " + url.getPath(), e);
            }
        } catch (SocketTimeoutException e) {
            throw new ServerErrorException("El server no pudo responder antes del timeout ["+SERVER_TIMEOUT+"]");
        } catch (IOException e) {
            throw new ServerErrorException(method,url,body,headers,responseStatus,errorMsg);
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("server_stream_error", "Error closing stream", e);
                }
            }
        }
        return expectedReturn;
    }

    private void readErrorResponse(String json, Integer status) throws ServerErrorException {
        String errorKey = "";
        String errorValue = "";
        try {
            Log.i("readErrorResponse", json.toString());
            if (json.startsWith("<!DOCTYPE html>")) throw new ServerErrorException("Error en el servidor");
            JSONObject error = new JSONObject(json);
            errorKey = error.getString("code");
            errorValue = error.getString("message");
            throw ErrorMatcher.valueOf(errorKey).getThrowable(errorValue,status);
        } catch (JSONException e) {
            throw new ServerErrorException("Error parseando server errorJson",e);
        } catch (IllegalArgumentException e) {
            throw ErrorMatcher.DEFAULT_ERROR.getThrowable(errorValue, status);
        }
    }

    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean isOnline = netInfo != null && netInfo.isConnectedOrConnecting();
        if (!isOnline) {
            ToastMessage.toastMessage(ctx, "Estás desconectado!");
        }
        return isOnline;

    }

    public static String getCurl(String method, URL url, String body, Map<String, String> headers) {
        return "curl -X" + method + " '" + getUrl(url)+ "' " + getBody(body) + getHeaders(headers);
    }

    private static String getUrl(URL url) {
        String res = url.getPath();
        String q = url.getQuery();
        if (q!=null) res+="?"+q;
        return res;
    }

    private static String getHeaders(Map<String, String> headers) {
        String headersStr="";
        if (headers != null){
            for(String k : headers.keySet()){
                headersStr+="-H '"+k+":"+headers.get(k)+"' ";
            }
        }
        return headersStr;
    }
    private static String getBody(String body){
        String bodyStr = "";
        if (body != null) {
            bodyStr = "-d '" + body + "' ";
        }
        return bodyStr;
    }

    public interface ResponseParse {
        Object readResponse(String json) throws JSONException;
    }
}
