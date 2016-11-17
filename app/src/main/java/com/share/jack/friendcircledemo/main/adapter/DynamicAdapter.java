package com.share.jack.friendcircledemo.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.share.jack.cygtool.http.callback.RxBus;
import com.share.jack.cygtool.recyclerview.adapter.CygBaseRecyclerAdapter;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.main.bean.DynamicData;
import com.share.jack.friendcircledemo.main.event.PraiseEvent;
import com.share.jack.friendcircledemo.main.viewholder.DynamicHolder;

/**
 *
 */
public class DynamicAdapter extends CygBaseRecyclerAdapter<DynamicData, DynamicHolder> {

    public DynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public DynamicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DynamicHolder(parent, R.layout.item_recycler);
    }

    @Override
    public void onBindViewHolder(final DynamicHolder dynamicHolder, final int position) {
        super.onBindViewHolder(dynamicHolder, position);

        dynamicHolder.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post(new PraiseEvent(position));
            }
        });

        dynamicHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击了评论", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
