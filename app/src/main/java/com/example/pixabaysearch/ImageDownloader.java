package com.example.pixabaysearch;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milly on 9/29/16.
 */
public class ImageDownloader extends AsyncTask<Void, Void, List<Drawable>> {

    private List<String> url;
    private Context context = null;

    public ImageDownloader(List<String> url_str, Context ctx) {
        url = url_str;
        context = ctx;
    }

    public interface OnTaskExecFinished {
        public void OnTaskExecFinishedEvent(List<Drawable> result);
    }

    private static OnTaskExecFinished mTaskFinishedEvent;

    public static void setOnTaskExecFinishedEvent(OnTaskExecFinished taskEvent) {
        if (taskEvent != null) {
            mTaskFinishedEvent = taskEvent;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<Drawable> doInBackground(Void... params) {

        List<Drawable> images = new ArrayList<Drawable>();
        for (int i = 0; i < url.size(); i++) {
            Drawable result = httpRequest(url.get(i));
            images.add(result);
        }

        return images;
    }

    @Override
    protected void onPostExecute(List<Drawable> images) {
        super.onPostExecute(images);

        if (mTaskFinishedEvent != null) {
            mTaskFinishedEvent.OnTaskExecFinishedEvent(images);
        }
    }

    private Drawable httpRequest(String url) {
        Drawable result = null;
        HttpURLConnection urlConnection = null;

        try {
            String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
            String encodeUrl = Uri.encode(url, ALLOWED_URI_CHARS);
            URL target = new URL(encodeUrl);
            urlConnection = (HttpURLConnection) target.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Bitmap imageBitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            result = new BitmapDrawable(context.getResources(), imageBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
