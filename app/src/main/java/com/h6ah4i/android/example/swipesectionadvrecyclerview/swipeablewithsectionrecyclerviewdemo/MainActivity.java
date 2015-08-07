package com.h6ah4i.android.example.swipesectionadvrecyclerview.swipeablewithsectionrecyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mWrapperAdapter;
    private SwipableWithSectionItemAdapter mAdapter;
    private RecyclerViewSwipeManager mSwipeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // instantiate data provider and adapter
        SectionItemDataProvider provider = new SectionItemDataProvider();
        mAdapter = new SwipableWithSectionItemAdapter(provider);


        // setup swipe manager
        mSwipeManager = new RecyclerViewSwipeManager();
        mWrapperAdapter = mSwipeManager.createWrappedAdapter(mAdapter);

        // set up recyclerview
        mRecyclerView.setAdapter(mWrapperAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new SwipeDismissItemAnimator());
        mRecyclerView.getItemAnimator().setSupportsChangeAnimations(false);

        // attach
        mSwipeManager.attachRecyclerView(mRecyclerView);
    }
}
