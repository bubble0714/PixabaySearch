package com.example.pixabaysearch;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Milly on 9/29/16.
 */

public class ImageQuery {
    private static final String TAG = ImageQuery.class.getSimpleName();

    private ImageQuery() {
    }

    public static JSONObject getJsonResult(String urlToConn) {
        String result = "";
        BufferedReader reader = null;
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlToConn);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.connect();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line)
                        .append("\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "Error in http connection: " + e.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }

            if (conn != null) {
                conn.disconnect();
            }
        }

        JSONObject jsonObject = null;
        try {
            if (!TextUtils.isEmpty(result)) {
                jsonObject = new JSONObject(result);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON data: " + e.toString());
        }

        return jsonObject;
    }
}
