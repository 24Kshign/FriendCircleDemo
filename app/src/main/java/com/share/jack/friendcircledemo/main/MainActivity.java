package com.share.jack.friendcircledemo.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.share.jack.cygtool.http.callback.CygSubscriberApi;
import com.share.jack.cygtool.http.callback.RxBus;
import com.share.jack.cygtool.recyclerview.MyRecyclerView;
import com.share.jack.cygtool.util.ControlKeyboardUtils;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.friendcircledemo.BaseActivity;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.login.model.UserSession;
import com.share.jack.friendcircledemo.main.adapter.DynamicAdapter;
import com.share.jack.friendcircledemo.main.bean.CommentData;
import com.share.jack.friendcircledemo.main.bean.DynamicData;
import com.share.jack.friendcircledemo.main.event.CommentEvent;
import com.share.jack.friendcircledemo.main.event.PraiseEvent;
import com.share.jack.friendcircledemo.main.model.CommentModel;
import com.share.jack.friendcircledemo.main.model.MainModel;
import com.share.jack.friendcircledemo.main.model.PraiseModel;
import com.share.jack.friendcircledemo.publish.PublishActivity;
import com.share.jack.friendcircledemo.widget.CustomToolbar;
import com.share.jack.jpush.event.JPushAutoRefresh;

import java.util.Date;
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
    @Bind(R.id.am_rv_comment)
    RelativeLayout mRvComment;
    @Bind(R.id.lc_btn_send)
    Button mBtnSend;
    @Bind(R.id.lc_et_comment)
    EditText mEtComment;

    private LinearLayoutManager linearLayoutManager;
    private DynamicAdapter mAdapter;
    private int articlePosition;
    private boolean isRootComment;

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

        mAdapter = new DynamicAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        amRecyclerview.setHasFixedSize(true);
        amRecyclerview.setLayoutManager(linearLayoutManager);
        amRecyclerview.setAdapter(mAdapter);
        amRecyclerview.setLoadingListener(this);
        amRecyclerview.setRefreshing(true);

        initListener();
    }

    private void initListener() {
        toolbar.setTvMainTitleRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishActivity.start(thisActivity());
            }
        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = mEtComment.getText().toString();
                if (comment.isEmpty()) {
                    Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                hide();
                DynamicData data = mAdapter.getItem(articlePosition);
                CommentData commentData = new CommentData();
                List<CommentData> commentDataList = data.getCommentDataList();
                commentData.setContent(comment);
                commentData.setIsRootComment(isRootComment);
                commentData.setTime(new Date().getTime());
                commentData.setFromUserId(UserSession.getUserProfile().getId());
                commentData.setFromName(UserSession.getUserProfile().getUsername());
                commentData.setToName(data.getUserProfile().getUsername());
                commentData.setToUserId(data.getUserProfile().getId());
                commentDataList.add(0, commentData);
                data.setCommentDataList(commentDataList);
                mAdapter.updateItem(articlePosition, data);
                commitComment(commentData);
            }
        });

        amRecyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hide();
                }
                return false;
            }
        });

        monitorPraiseOrCancelPraise();

        monitorComment();

        monitorRefresh();
    }

    private void monitorRefresh() {
        RxBus.getInstance().toObserverable(JPushAutoRefresh.class)
                .subscribe(new Action1<JPushAutoRefresh>() {
                    @Override
                    public void call(JPushAutoRefresh jPushAutoRefresh) {
                        refreshData();
                    }
                });
    }

    private void commitComment(CommentData commentData) {
        CommentModel.getInstance().execute(mAdapter.getItem(articlePosition).getId(), commentData, new CygSubscriberApi<Void>(thisActivity(), false) {
            @Override
            protected void onBaseNext(Void data) {
                Toast.makeText(MainActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //评论(RxBus监听)
    private void monitorComment() {
        RxBus.getInstance().toObserverable(CommentEvent.class).subscribe(new Action1<CommentEvent>() {
            @Override
            public void call(CommentEvent commentEvent) {
                isRootComment = commentEvent.isRootComment();
                int position = commentEvent.getPosition();
                if (isRootComment) {
                    articlePosition = position;
                    mEtComment.setHint("说点什么吧...");
                } else {
                    articlePosition = getArticlePositionByComment(position);
                    if (mAdapter.getItem(articlePosition).getCommentDataList().get(position).getFromUserId()
                            == UserSession.getUserProfile().getId()) {
                        Toast.makeText(MainActivity.this, "不能回复自己的哦!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String name = mAdapter.getItem(articlePosition).getCommentDataList().get(commentEvent.getPosition()).getFromName();
                    mEtComment.setHint("回复:" + name);
                }
                showCommentEdit();
                linearLayoutManager.scrollToPositionWithOffset(articlePosition + 1, -230);
            }
        });
    }


    private int getArticlePositionByComment(int commentPos) {
        for (int i = 0; i < mAdapter.getDataList().size(); i++) {
            for (int j = 0; j < mAdapter.getItem(i).getCommentDataList().size(); j++) {
                if (mAdapter.getItem(i).getCommentDataList().get(j).getId()
                        == mAdapter.getItem(i).getCommentDataList().get(commentPos).getId()) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void hide() {
        ControlKeyboardUtils.hideKeyboard(thisActivity());
        mEtComment.setText("");
        mRvComment.setVisibility(View.GONE);
    }

    private void showCommentEdit() {
        mRvComment.setVisibility(View.VISIBLE);
        ControlKeyboardUtils.showKeyBoard(mEtComment);
    }

    //点赞或者取消点赞(RxBus监听)
    private void monitorPraiseOrCancelPraise() {
        RxBus.getInstance().toObserverable(PraiseEvent.class).subscribe(new Action1<PraiseEvent>() {
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
                                DynamicData dynamicData = mAdapter.getItem(praiseEvent.getPosition());
                                dynamicData.setIsPriseByCurUser(!isPraise);
                                mAdapter.updateItem(praiseEvent.getPosition(), dynamicData);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mRvComment.getVisibility() == View.VISIBLE) {
                mRvComment.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
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