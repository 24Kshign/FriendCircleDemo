package com.share.jack.friendcircledemo.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.share.jack.cygtool.http.callback.RxBus;
import com.share.jack.cygtool.recyclerview.adapter.CygBaseRecyclerAdapter;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.main.bean.CommentData;
import com.share.jack.friendcircledemo.main.event.CommentEvent;
import com.share.jack.friendcircledemo.main.viewholder.CommentHolder;

/**
 *
 */
public class CommentAdapter extends CygBaseRecyclerAdapter<CommentData, CommentHolder> {

    public CommentAdapter(Context context, OnItemClickListener<CommentHolder> listener) {
        super(context, listener);
    }

    public CommentAdapter(Context context) {
        super(context, null);
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(parent, R.layout.item_comment);
    }

    @Override
    public void onBindViewHolder(CommentHolder commentHolder, final int position) {
        super.onBindViewHolder(commentHolder, position);
        commentHolder.rcMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post(new CommentEvent(position, false));
            }
        });
    }
}