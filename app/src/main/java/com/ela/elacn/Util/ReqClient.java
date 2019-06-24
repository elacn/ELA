package com.ela.elacn.Util;

import android.util.Log;

import com.ela.elacn.$;
import com.ela.elacn.Model.Result;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class ReqClient {

    private static String cookies = "";
    private static final int TIME_OUT_MILLIS = 10000;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String LOG_TAG = "ReqClient";
    private static Handler DEFAULT_RESPONSE_HANDLER = new Handler<String>(String.class) {
        @Override
        public void res(Result<String> res) {
            Log.d(LOG_TAG, " DEFAULT_HANDLER: res: " + res.json());
        }
    };

    static{
        client.setTimeout(TIME_OUT_MILLIS);
    }


    public static String getCookies(){

        return cookies;
    }

    public static abstract class Handler<T> extends AsyncHttpResponseHandler {

        Class<T> c;

        public Handler(Class c){
            this.c = c;
        }

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String data = StringUtils.fromBytes(bytes);
            Result<String> res = Result.success();

            for (Header h:headers) {

                if(h.getName().equals("Set-Cookie")){

                    cookies = h.getValue();
                }
            }
            if(StringUtils.isNotBlank(data))
                res = JSON.from(data, JSON.type(Result.class, String.class));
            if(res == null)
                res = Result.create(0, data, "", "format Result class error, create Result..");
            res(res);
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            String data = StringUtils.fromBytes(bytes);
            res(Result.create(-1000, data, "network_error", "访问网络失败"));
        }

        public abstract void res(Result<String> res);
    }

    public static void get(String url, RequestParams params) {
        client.get(url, params, DEFAULT_RESPONSE_HANDLER);
    }

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params) {
        client.post(url, params, DEFAULT_RESPONSE_HANDLER);
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    public static void postNoapp(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){

        client.post(url,params,responseHandler);
    }



}
