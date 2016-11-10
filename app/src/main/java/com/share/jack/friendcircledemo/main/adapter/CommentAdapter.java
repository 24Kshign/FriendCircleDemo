package com.share.jack.friendcircledemo.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.share.jack.cygtool.recyclerview.adapter.CygBaseRecyclerAdapter;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.main.bean.CommentData;
import com.share.jack.friendcircledemo.main.viewholder.CommentHolder;

/**
 *
 */
public class CommentAdapter extends CygBaseRecyclerAdapter<CommentData, CommentHolder> {

    public CommentAdapter(Context context, OnItemClickListener<CommentHolder> listener) {
        super(context, listener);
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(parent, R.layout.item_comment);
    }
}