package com.share.jack.friendcircledemo.test;

import android.content.Context;
import android.view.ViewGroup;

import com.share.jack.cygtool.recyclerview.adapter.CygBaseRecyclerAdapter;
import com.share.jack.friendcircledemo.R;

/**
 *
 */
public class TestAdapter extends CygBaseRecyclerAdapter<String, TestHolder> {

    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestHolder(parent, R.layout.item_test);
    }
}
