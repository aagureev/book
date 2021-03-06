package com.hafros.bookproj;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestManager {

    public static void checkConfiguration(final NetworkHandler handler){

        String url = App.getCache().getAsString("url_config");

        OkHttpClient httpclient = new OkHttpClient();

        Request request = new Request.Builder().url(url).get().build();

        httpclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                handler.failHandler(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                @Nullable Response responceTemp = response;
                String content = new String(responceTemp.body().bytes(), "UTF-8");


                if (responceTemp.body() != null) {
                    try {
                        handler.successHandler(content);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    handler.failHandler("response.body() == null");
                }
            }
        });

    }

    public static void reportURL(String url, final NetworkHandler handler){

        OkHttpClient httpclient = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("a", ""+url)
                .build();

        Request request = new Request.Builder().url("http://ppc.mopatech.com/get_address.php").post(requestBody).build();

        httpclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {



            }
        });

    }


}
