package com.spit.techfest.matrix;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Darshan on 9/3/2014.
 */
public class ApiConnector {

    public JSONArray getListItems()
    {
        //Log.d("Api", "ApiConnector called");
        //url for getting all the items

        String url_images = "http://www.matrixthefest.org/app_php/get_images.php";
        String url = "http://www.matrixthefest.org/app_php/get_event_info.php";

        //String url_images = "http://10.0.2.2/android_connect/get_images.php";
        //String url = "http://10.0.2.2/android_connect/get_event_info.php";


        HttpEntity httpEntity = null;
        HttpEntity httpEntity_images = null;


        try{
            //Log.d("Test1", "1");
            DefaultHttpClient httpClient = new DefaultHttpClient();

            //Log.d("Test2", "2");
            HttpGet httpGet = new HttpGet(url);

            //Log.d("Test3", httpGet.toString());
            HttpResponse httpResponse = httpClient.execute(httpGet);

            //Log.d("Test4", httpResponse.toString());
            httpEntity = httpResponse.getEntity();

            DefaultHttpClient httpClient2 = new DefaultHttpClient();

            HttpGet httpGet2 = new HttpGet(url_images);
            //Log.d("Test5", "1");

            HttpResponse httpResponse2 = httpClient2.execute(httpGet2);
            //Log.d("Test6", "1");

            httpEntity_images = httpResponse2.getEntity();
            //Log.d("Test7", "1");

        }
        catch(ClientProtocolException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //JSONArray jsonArray[] = new JSONArray[2];
        JSONArray jsonArray1 = null;
        JSONArray jsonArray2 = null;

        if(httpEntity != null && httpEntity_images != null)
        {
            try{
                String entityResponse1 = EntityUtils.toString(httpEntity);

                jsonArray1 = new JSONArray(entityResponse1);

                String entityResponse2 = EntityUtils.toString(httpEntity_images);

                jsonArray2 = new JSONArray(entityResponse2);

                for (int i = 0; i < jsonArray2.length(); i++) {
                    jsonArray1.put(jsonArray2.getJSONObject(i));
                }

            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            //Log.d("httpEntity is null", httpEntity.toString());
        }

        return jsonArray1;

    }
}
