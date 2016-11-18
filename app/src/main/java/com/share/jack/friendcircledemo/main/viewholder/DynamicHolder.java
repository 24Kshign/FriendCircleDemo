package com.share.jack.friendcircledemo.main.viewholder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.share.jack.cygtool.recyclerview.holder.CygBaseViewHolder;
import com.share.jack.cygtool.util.CygTimeUtil;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.main.adapter.CommentAdapter;
import com.share.jack.friendcircledemo.main.bean.DynamicData;

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
    public RecyclerView recyclerView;

    public DynamicHolder(ViewGroup parent, @LayoutRes int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        avatar = getView(R.id.ir_iv_avatar);
        nickName = getView(R.id.ir_tv_name);
        time = getView(R.id.ir_tv_time);
        content = getView(R.id.ir_tv_content);
        image = getView(R.id.ir_iv_image);
        recyclerView = getView(R.id.ir_recyclerview);
        comment = getView(R.id.ir_iv_comment);
        praise = getView(R.id.ir_iv_praise);
    }

    @Override
    public void setData(DynamicData dynamicDate) {
        avatar.setImageResource(R.mipmap.icon_avatar_from);
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
        if (dynamicDate.getCommentDataList() != null) {
            adapter = new CommentAdapter(getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.setDataList(dynamicDate.getCommentDataList());
            recyclerView.setAdapter(adapter);
        }

    }
}
