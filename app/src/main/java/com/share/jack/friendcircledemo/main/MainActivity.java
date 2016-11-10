package com.share.jack.friendcircledemo.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.share.jack.cygtool.recyclerview.MyRecyclerView;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.friendcircledemo.BaseActivity;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.login.model.UserProfile;
import com.share.jack.friendcircledemo.main.adapter.DynamicAdapter;
import com.share.jack.friendcircledemo.main.bean.CommentData;
import com.share.jack.friendcircledemo.main.bean.DynamicDate;
import com.share.jack.friendcircledemo.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页
 */
public class MainActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    @Bind(R.id.am_titlebar)
    CustomToolbar toolbar;
    @Bind(R.id.am_recyclerview)
    MyRecyclerView amRecyclerview;


    private DynamicAdapter mAdapter;
    private String[] imgs = {"http://avatar.csdn.net/3/B/9/1_baiyuliang2013.jpg",
            "https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=a22d53b052fbb2fb2b2b5f127f482043/ac345982b2b7d0a2f7375f70ccef76094a369a65.jpg",
            "https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=57c485df7cec54e75eec1d1e893a9bfd/241f95cad1c8a786bfec42ef6009c93d71cf5008.jpg",
            "https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=f3f6ab70cc134954611eef64664f92dd/dcc451da81cb39db1bd474a7d7160924ab18302e.jpg",
            "https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/472309f7905298221dd4c458d0ca7bcb0b46d442.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1564533037,3918553373&fm=116&gp=0.jpg"};

    public static void start(Context context) {
        CygActivity.start(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        toolbar.setMainTitle("圈朋友");
        toolbar.setMainTitleLeftText();
        toolbar.setTvMainTitleLeftOnClick(thisActivity());
        mAdapter = new DynamicAdapter(this);
        amRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        amRecyclerview.setAdapter(mAdapter);
        amRecyclerview.setLoadingListener(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void initData() {
        List<DynamicDate> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DynamicDate dynamicDate = new DynamicDate();
            dynamicDate.setId((i + 1));
            dynamicDate.setTime(new Date().getTime());
            dynamicDate.setImageUrl(imgs[i % 6]);
            dynamicDate.setContent("TEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXT" + (i + 1));
            UserProfile userProfile = new UserProfile();
            userProfile.setId((i + 1));
            userProfile.setUsername("24K纯帅" + (i + 1));
            dynamicDate.setUserProfile(userProfile);
            dynamicDate.setCommentDataList(getCommentList((i + 1) % 4));
            list.add(dynamicDate);
        }
        mAdapter.setDataList(list);
    }

    private List<CommentData> getCommentList(int flag) {
        List<CommentData> list = new ArrayList<>();
        for (int i = 0; i < flag; i++) {
            CommentData commentData = new CommentData();
            commentData.setId((i + 1));
            commentData.setTime(1478764743571L);
            commentData.setFromName("24K纯帅");
            commentData.setToName("你特么");
            commentData.setContent("你瞅啥,瞅你咋地");
            list.add(commentData);
        }
        return list;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                amRecyclerview.refreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        amRecyclerview.loadMoreComplete();
    }
}