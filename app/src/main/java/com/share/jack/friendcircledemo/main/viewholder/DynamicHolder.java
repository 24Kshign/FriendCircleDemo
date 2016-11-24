package com.share.jack.friendcircledemo.main.viewholder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.share.jack.cygtool.http.callback.RxBus;
import com.share.jack.cygtool.recyclerview.holder.CygBaseViewHolder;
import com.share.jack.cygtool.util.CygTimeUtil;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.main.adapter.CommentAdapter;
import com.share.jack.friendcircledemo.main.bean.DynamicData;
import com.share.jack.friendcircledemo.main.event.PraiseUserClickEvent;
import com.share.jack.friendcircledemo.main.spannable.PraiseListTextView;

import rx.functions.Action1;

/**
 *
 */
public class DynamicHolder extends CygBaseViewHolder<DynamicData> {

    public ImageView avatar;
    public TextView nickName;
    public TextView time;
    public TextView content;
    public ImageView image;
    public ImageView praise;
    public ImageView comment;
    private CommentAdapter adapter;
    public RecyclerView commentRecyclerView;
    public PraiseListTextView mTvPraiseUserName;
    private LinearLayout lvCommentBody;
    public View viewLine;


    public DynamicHolder(ViewGroup parent, @LayoutRes int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        avatar = getView(R.id.ir_iv_avatar);
        nickName = getView(R.id.ir_tv_name);
        time = getView(R.id.ir_tv_time);
        content = getView(R.id.ir_tv_content);
        image = getView(R.id.ir_iv_image);
        commentRecyclerView = getView(R.id.ir_comment_recyclerview);
        comment = getView(R.id.ir_iv_comment);
        praise = getView(R.id.ir_iv_praise);
        mTvPraiseUserName = getView(R.id.ir_praise_textview);
        viewLine = getView(R.id.ir_view_line);
        lvCommentBody = getView(R.id.ir_ll_comment_body);

    }

    @Override
    public void setData(final DynamicData dynamicDate) {
        avatar.setImageResource(R.mipmap.icon_avatar);
        nickName.setText(dynamicDate.getUserProfile().getUsername());
        content.setText(dynamicDate.getContent());
        time.setText(CygTimeUtil.getStandardDate(dynamicDate.getTime()));
        if (dynamicDate.isPriseByCurUser()) {
            praise.setSelected(true);
        } else {
            praise.setSelected(false);
        }
        if (!dynamicDate.getImageUrl().isEmpty()) {
            image.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(dynamicDate.getImageUrl()).asBitmap()
                    .dontAnimate().placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(image);
        }
        if (dynamicDate.hasPraise() || dynamicDate.hasComment()) {
            lvCommentBody.setVisibility(View.VISIBLE);
            if (dynamicDate.hasPraise()) {
                mTvPraiseUserName.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                mTvPraiseUserName.setDatas(dynamicDate.getPraiseDataList());
                RxBus.getInstance().toObserverable(PraiseUserClickEvent.class)
                        .subscribe(new Action1<PraiseUserClickEvent>() {
                            @Override
                            public void call(PraiseUserClickEvent praiseUserClickEvent) {
                                Toast.makeText(getContext(), dynamicDate.getPraiseDataList().get(praiseUserClickEvent.getPosition()).getPraiseName(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                mTvPraiseUserName.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
            }
            if (dynamicDate.hasComment()) {
                adapter = new CommentAdapter(getContext());
                commentRecyclerView.setHasFixedSize(true);
                commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter.setDataList(dynamicDate.getCommentDataList());
                commentRecyclerView.setAdapter(adapter);
            }
        } else {
            lvCommentBody.setVisibility(View.GONE);
        }
    }
}
