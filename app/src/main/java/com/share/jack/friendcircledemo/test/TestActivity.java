package com.share.jack.friendcircledemo.test;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.friendcircledemo.BaseActivity;
import com.share.jack.friendcircledemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    @Bind(R.id.at_recyclerview)
    XRecyclerView atRecyclerview;
    private TestAdapter mAdapter;

    public static void start(Context context) {
        CygActivity.start(context, TestActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        mAdapter = new TestAdapter(this);
        atRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        atRecyclerview.setAdapter(mAdapter);
        atRecyclerview.setLoadingListener(this);
        atRecyclerview.setLoadingMoreEnabled(true);

        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("24K纯帅");
        }
        mAdapter.setDataList(list);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                atRecyclerview.refreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        atRecyclerview.loadMoreComplete();
    }
}