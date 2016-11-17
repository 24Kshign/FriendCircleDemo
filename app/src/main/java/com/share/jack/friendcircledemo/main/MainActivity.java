package com.share.jack.friendcircledemo.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.share.jack.cygtool.http.callback.CygSubscriberApi;
import com.share.jack.cygtool.http.callback.RxBus;
import com.share.jack.cygtool.recyclerview.MyRecyclerView;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.friendcircledemo.BaseActivity;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.login.model.UserSession;
import com.share.jack.friendcircledemo.main.adapter.DynamicAdapter;
import com.share.jack.friendcircledemo.main.bean.DynamicData;
import com.share.jack.friendcircledemo.main.event.PraiseEvent;
import com.share.jack.friendcircledemo.main.model.MainModel;
import com.share.jack.friendcircledemo.main.model.PraiseModel;
import com.share.jack.friendcircledemo.publish.PublishActivity;
import com.share.jack.friendcircledemo.widget.CustomToolbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 首页
 */
public class MainActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    private static final String TAG = "MainActivity";

    @Bind(R.id.am_titlebar)
    CustomToolbar toolbar;
    @Bind(R.id.am_recyclerview)
    MyRecyclerView amRecyclerview;

    private DynamicAdapter mAdapter;

    public static void start(Context context) {
        CygActivity.start(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolbar.setMainTitle("圈朋友");
        toolbar.setMainTitleLeftText();
        toolbar.setTvMainTitleLeftOnClick(thisActivity());
        toolbar.setMainTitleLeftDrawable(R.mipmap.icon_back);
        toolbar.setMainTitleRightDrawable(R.mipmap.icon_add);

        initListener();

        mAdapter = new DynamicAdapter(this);
        amRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        amRecyclerview.setAdapter(mAdapter);
        amRecyclerview.setLoadingListener(this);
        amRecyclerview.setRefreshing(true);
    }

    private void initListener() {
        toolbar.setTvMainTitleRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishActivity.start(thisActivity());
            }
        });

        RxBus.getInstance().toObserverable(PraiseEvent.class)
                .subscribe(new Action1<PraiseEvent>() {
                    @Override
                    public void call(final PraiseEvent praiseEvent) {
                        final boolean isPraise = mAdapter.getItem(praiseEvent.getPosition()).isPriseByCurUser();
                        final DynamicData dynamicData = mAdapter.getItem(praiseEvent.getPosition());
                        PraiseModel.getInstance().execute(String.valueOf(isPraise), dynamicData.getId(), UserSession.getUserProfile().getId(),
                                mAdapter.getItem(praiseEvent.getPosition()).getUserProfile().getId(), new CygSubscriberApi<Void>(thisActivity(), false) {
                                    @Override
                                    protected void onBaseNext(Void data) {
                                        if (!isPraise) {
                                            Toast.makeText(MainActivity.this, "您是第" + (dynamicData.getPraiseCount() + 1) + "个点赞的", Toast.LENGTH_SHORT).show();
                                            mAdapter.getItem(praiseEvent.getPosition()).setPraiseCount(dynamicData.getPraiseCount() + 1);
                                        } else {
                                            Toast.makeText(MainActivity.this, "您前面还有" + (dynamicData.getPraiseCount() - 1) + "个人点赞", Toast.LENGTH_SHORT).show();
                                            mAdapter.getItem(praiseEvent.getPosition()).setPraiseCount(dynamicData.getPraiseCount() - 1);
                                        }
                                        mAdapter.getItem(praiseEvent.getPosition()).setIsPriseByCurUser(!isPraise);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                    }
                });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (data.getStringExtra("result").equals("true")) {
                amRecyclerview.setRefreshing(true);
            }
        }
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    private void refreshData() {
        MainModel.getInstance().execute(UserSession.getUserProfile().getId(), new CygSubscriberApi<List<DynamicData>>(thisActivity(), false) {
            @Override
            protected void onBaseNext(List<DynamicData> data) {
                mAdapter.setDataList(data);
                amRecyclerview.refreshComplete();
            }

            @Override
            protected void onBaseError(Throwable t) {
                super.onBaseError(t);
                amRecyclerview.refreshComplete();
            }
        });
    }

    @Override
    public void onLoadMore() {
        amRecyclerview.loadMoreComplete();
    }
}