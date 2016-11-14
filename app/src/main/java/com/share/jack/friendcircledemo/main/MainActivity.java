package com.share.jack.friendcircledemo.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.share.jack.cygtool.http.callback.CygSubscriberApi;
import com.share.jack.cygtool.recyclerview.MyRecyclerView;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.friendcircledemo.BaseActivity;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.login.model.UserProfile;
import com.share.jack.friendcircledemo.login.model.UserSession;
import com.share.jack.friendcircledemo.main.adapter.DynamicAdapter;
import com.share.jack.friendcircledemo.main.bean.CommentData;
import com.share.jack.friendcircledemo.main.bean.DynamicData;
import com.share.jack.friendcircledemo.main.model.MainModel;
import com.share.jack.friendcircledemo.publish.PublishActivity;
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
    private String[] imgs = {"http://t1.niutuku.com/960/21/21-262687.jpg",
            "http://f1.94uv.com/yuedu/2015-09/20150924135035661.png",
            "http://pic34.nipic.com/20131028/2455348_171218804000_2.jpg",
            "http://t1.niutuku.com/960/10/10-202370.jpg",
            "http://file.cbda.cn/uploadfile/2015/0330/20150330041852447.jpg",
            "http://img.taopic.com/uploads/allimg/121209/234928-12120Z0543764.jpg"};

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
        toolbar.setTvMainTitleRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishActivity.start(thisActivity());
            }
        });
        mAdapter = new DynamicAdapter(this);
        amRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        amRecyclerview.setAdapter(mAdapter);
        amRecyclerview.setLoadingListener(this);
        amRecyclerview.setRefreshing(true);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void initData() {
        List<DynamicData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DynamicData dynamicData = new DynamicData();
            dynamicData.setId((i + 1));
            dynamicData.setTime(new Date().getTime());
            dynamicData.setImageUrl(imgs[i % 6]);
            dynamicData.setContent("TEXTTEXTTEXTTEXTTEXTTEXTTEXTTEXT" + (i + 1));
            UserProfile userProfile = new UserProfile();
            userProfile.setId((i + 1));
            userProfile.setUsername("24K纯帅" + (i + 1));
            dynamicData.setUserProfile(userProfile);
            dynamicData.setCommentDataList(getCommentList((i + 1) % 11));
            list.add(dynamicData);
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
            if (i == 0) {
                commentData.setIsRootComment(true);
            } else {
                commentData.setIsRootComment(false);
                commentData.setToName("你特么");
            }
            commentData.setContent("你瞅啥,瞅你咋地");
            list.add(commentData);
        }
        return list;
    }

    @Override
    public void onRefresh() {
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