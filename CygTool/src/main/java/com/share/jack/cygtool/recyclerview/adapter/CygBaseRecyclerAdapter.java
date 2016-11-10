package com.share.jack.cygtool.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.share.jack.cygtool.recyclerview.holder.CygBaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class CygBaseRecyclerAdapter<DATA, VH extends CygBaseViewHolder<DATA>> extends RecyclerView.Adapter<VH> {

    private List<DATA> mDataList;

    private final Context mContext;

    protected OnItemClickListener<VH> listener;

    public CygBaseRecyclerAdapter(Context context, OnItemClickListener<VH> listener) {
        this.mContext = context;
        this.listener = listener;
    }

    public CygBaseRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    /**
     * 给holder设置数据
     *
     * @param vh
     * @param position
     */
    @Override
    public void onBindViewHolder(VH vh, final int position) {
        vh.setData(mDataList.get(position));
        if (listener != null) {
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    /**
     * 填充数据,此方法会清空以前的数据
     *
     * @param dataList 需要显示的数据
     */
    public void setDataList(List<DATA> dataList, boolean notifyDataSetChanged) {
        if (mDataList != null) {
            mDataList.clear();
        }
        if (dataList != null) {
            if (mDataList == null) {
                mDataList = new ArrayList<>();
            }
            mDataList.addAll(dataList);
        }
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public void setDataList(List<DATA> dataList) {
        setDataList(dataList, true);
    }

    /**
     * 更新局部数据
     *
     * @param position item的位置
     * @param data     item的数据
     */
    public void updateItem(int position, DATA data) {
        mDataList.set(position, data);
        notifyDataSetChanged();
    }

    /**
     * 获取一条数据
     *
     * @param position item的位置
     * @return item对应的数据
     */
    public DATA getItem(int position) {
        return mDataList.get(position);
    }

    /**
     * 追加一条数据
     *
     * @param data 追加的数据
     */
    public void appendItem(DATA data) {
        mDataList.add(data);
        notifyDataSetChanged();
    }

    /**
     * 追加一个集合数据
     *
     * @param list 要追加的数据集合
     */
    public void appendList(List<DATA> list) {
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 在最顶部前置数据
     *
     * @param data 要前置的数据
     */
    public void frontItem(DATA data) {
        mDataList.add(0, data);
        notifyDataSetChanged();
    }

    /**
     * 在顶部前置数据集合
     *
     * @param list 要前置的数据集合
     */
    public void frontList(List<DATA> list) {
        mDataList.addAll(0, list);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener<VH> {
        void onItemClick(int position);
    }

}
