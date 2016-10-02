package com.example.pixabaysearch;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LIST_SPAN_COUNT = 1;
    private static final int GRID_SPAN_COUNT = 3;
    private static final int STATE_LIST = 1;
    private static final int STATE_GRID = 2;
    private int mCurrentState = STATE_LIST;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(my_toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerLayoutManager = new StaggeredGridLayoutManager(LIST_SPAN_COUNT,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setHasFixedSize(false);
        setGaggeredGridView();
    }

    public void setGaggeredGridView(){

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(GRID_SPAN_COUNT, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<ItemObject> gaggeredList = getListItemData();

        RecyclerAdapter rcAdapter = new RecyclerAdapter(MainActivity.this, gaggeredList, true);
        recyclerView.setAdapter(rcAdapter);
    }

    private List<ItemObject> getListItemData(){
        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        Drawable myIcon = getResources().getDrawable(R.drawable.sample_0);
        Drawable myIcon1 = getResources().getDrawable(R.drawable.sample_1);
        listViewItems.add(new ItemObject(myIcon, "1","2"));
        listViewItems.add(new ItemObject(myIcon1, "1","2"));
        listViewItems.add(new ItemObject(myIcon, "1","2"));
        listViewItems.add(new ItemObject(myIcon1, "1","2"));

        return listViewItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_gridview:
                recyclerLayoutManager = new StaggeredGridLayoutManager(GRID_SPAN_COUNT,
                        StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(recyclerLayoutManager);
                break;

            case R.id.menu_listview:
                recyclerLayoutManager = new StaggeredGridLayoutManager(LIST_SPAN_COUNT,
                        StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(recyclerLayoutManager);
                break;

        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }
}
