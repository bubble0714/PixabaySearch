package com.example.pixabaysearch;

import android.os.Bundle;
import android.app.SearchManager;
import android.content.Intent;
import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LIST_SPAN_COUNT = 1;
    private static final int GRID_SPAN_COUNT = 3;
    private static final int STATE_GRID = 1;
    private static final int STATE_LIST = 2;
    private int spanState = STATE_GRID;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager recyclerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setGaggeredListView();
    }

    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String keyword = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, keyword, Toast.LENGTH_SHORT).show();
        }

        //Init state is grid view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
    }

    public void setGaggeredGridView(){
        spanState = STATE_GRID;
        recyclerLayoutManager = new StaggeredGridLayoutManager(GRID_SPAN_COUNT,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this, getListItemData()));
    }

    public void setGaggeredListView() {
        spanState = STATE_LIST;
        recyclerLayoutManager = new StaggeredGridLayoutManager(LIST_SPAN_COUNT, 1);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this, getListItemData()));
    }

    private List<ItemContent> getListItemData() {
        List<ItemContent> listViewItems = new ArrayList<ItemContent>();
        Drawable myIcon = getResources().getDrawable(R.drawable.sample_0);
        Drawable myIcon1 = getResources().getDrawable(R.drawable.sample_1);
        listViewItems.add(new ItemContent(myIcon, "1", "2"));
        listViewItems.add(new ItemContent(myIcon1, "1", "2"));
        listViewItems.add(new ItemContent(myIcon, "1", "2"));
        listViewItems.add(new ItemContent(myIcon1, "1", "2"));

        return listViewItems;
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
}


