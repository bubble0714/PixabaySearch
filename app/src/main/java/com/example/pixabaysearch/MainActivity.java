package com.example.pixabaysearch;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milly on 9/29/16.
 */

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "3415122-4ba2c33ce26ffe277e5d59caa";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int LIST_SPAN_COUNT = 1;
    private static final int GRID_SPAN_COUNT = 3;
    private static final int STATE_GRID = 1;
    private static final int STATE_LIST = 2;
    private static int spanState = STATE_GRID;
    private Context context;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager recyclerLayoutManager;
    private ProgressDialog progressDialog;
    private String searchKeyword;
    public List<String> imgUrls;
    public List<Drawable> imgDrawables;
    public List<Integer> imgWidth;
    public List<Integer> imgHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    public void initViews() {
        context = this;

        imgUrls = new ArrayList<String>();
        imgWidth = new ArrayList<Integer>();
        imgHeight = new ArrayList<Integer>();
        imgDrawables = new ArrayList<Drawable>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String keyword = searchIntent.getStringExtra(SearchManager.QUERY);

            searchKeyword = getSearchUrl(keyword);
            if (!TextUtils.isEmpty(searchKeyword)) {
                ImgSearchTask imgSearchTask = new ImgSearchTask();
                imgSearchTask.execute(searchKeyword);
            }
        }

        //Init state is grid view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_gridview:
                spanState = STATE_GRID;
                recyclerLayoutManager = new StaggeredGridLayoutManager(GRID_SPAN_COUNT,
                        StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(recyclerLayoutManager);
                break;

            case R.id.menu_listview:
                spanState = STATE_LIST;
                recyclerLayoutManager = new StaggeredGridLayoutManager(LIST_SPAN_COUNT,
                        StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(recyclerLayoutManager);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private String getSearchUrl(String strSearch) {
        if (TextUtils.isEmpty(strSearch)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://pixabay.com/api/?key=")
                .append(API_KEY)
                .append("&q=")
                .append(strSearch.trim().replace(" ", "+"))
                .append("&image_type=photo");
        return stringBuilder.toString();
    }

    private class ImgSearchTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(getString(R.string.progress_search));
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected List<String> doInBackground(String... params) {
            if (params == null || params.length <= 0) {
                return null;
            }

            JSONObject jsonObject = ImageQuery.getJsonResult(params[0]);
            // TODO: Report different error state to user, e.g., no Internet connection.
            if (jsonObject == null) {
                return null;
            }

            JSONArray jsonArray;
            imgUrls.clear();
            imgWidth.clear();
            imgHeight.clear();
            imgDrawables.clear();

            try {
                jsonArray = jsonObject.getJSONArray("hits");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);

                    imgUrls.add(object.getString("webformatURL"));
                    imgWidth.add(object.getInt("webformatWidth"));
                    imgHeight.add(object.getInt("webformatHeight"));
                }

                Log.d(TAG, "*** item urls: " + imgUrls);
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing JSON data: " + e.toString());
            }

            return imgUrls;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if (result != null && imgUrls.size() > 0) {
                downloadWebImage();
            } else {
                Toast.makeText(context,
                        getString(R.string.search_failed, searchKeyword),
                        Toast.LENGTH_SHORT)
                        .show();
            }

            progressDialog.setMessage(getString(R.string.progress_downloading));

            super.onPostExecute(result);
        }

        private void downloadWebImage() {
            ImageDownloader downloader = new ImageDownloader(imgUrls, context);

            downloader.setOnTaskExecFinishedEvent(new ImageDownloader.OnTaskExecFinished() {
                @Override
                public void OnTaskExecFinishedEvent(List<Drawable> result) {

                    imgDrawables = result;

                    if (spanState == STATE_GRID) {
                        recyclerLayoutManager = new StaggeredGridLayoutManager(GRID_SPAN_COUNT,
                                StaggeredGridLayoutManager.VERTICAL);
                    } else {
                        recyclerLayoutManager = new StaggeredGridLayoutManager(LIST_SPAN_COUNT,
                                StaggeredGridLayoutManager.VERTICAL);
                    }
                    recyclerView.setLayoutManager(recyclerLayoutManager);

                    List<ItemContent> itemSet = new ArrayList<ItemContent>();
                    for (int i = 0; i < imgDrawables.size(); i++) {

                        itemSet.add(new ItemContent(
                                imgDrawables.get(i),
                                imgWidth.get(i) + "*" + imgHeight.get(i),
                                imgUrls.get(i))
                        );
                    }

                    List<ItemContent> imageData = itemSet;
                    RecyclerAdapter imageViewAdapter = new RecyclerAdapter(context, imageData);
                    recyclerView.setAdapter(imageViewAdapter);

                    progressDialog.dismiss();
                }
            });
            downloader.execute();
        }
    }
}


