package com.share.jack.friendcircledemo.test;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.share.jack.cygtool.recyclerview.holder.CygBaseViewHolder;
import com.share.jack.friendcircledemo.R;

/**
 *
 */
public class TestHolder extends CygBaseViewHolder<String> {

    public TextView text;

    public TestHolder(ViewGroup parent, @LayoutRes int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        text = getView(R.id.it_tv_text);
    }

    @Override
    public void setData(String s) {
        text.setText(s);
    }
}
