package com.zhangyi.reboundrecyclelayout.customview.ryadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.zhangyi.reboundrecyclelayout.customview.ryadapter.base.ItemViewDelegate;
import com.zhangyi.reboundrecyclelayout.customview.ryadapter.base.ItemViewDelegateManager;
import com.zhangyi.reboundrecyclelayout.customview.ryadapter.base.ViewHolder;

import java.util.List;

/**
 * Created by 75 on 2016/12/2.
 */

/**
 * 适用于单一样式的RecyclerView
 * T 为item的数据实体类
 */
public abstract class SingleTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context context;
    protected List<T> datas;
//    protected int mLayoutId;

    protected ItemViewDelegateManager itemViewDelegateManager;
    protected SingleTypeAdapter.OnItemClickListener onItemClickListener;


    public SingleTypeAdapter(final Context context, final int layoutId, List<T> datas) {
        this.context = context;
        this.datas = datas;
        this.itemViewDelegateManager = new ItemViewDelegateManager();
//        this.mLayoutId = layoutId;
        this.addSingleTypeItem(layoutId);
    }

    /**
     * 添加单一样式的item
     */
    private void addSingleTypeItem(final int layoutId){
       this.addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                SingleTypeAdapter.this.convert(holder, t, position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return itemViewDelegateManager.getItemViewType(datas.get(position), position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = itemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(context, parent, layoutId);
        onViewHolderCreated(holder,holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder,View itemView){

    }

    public void convert(ViewHolder holder, T t) {
        itemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    onItemClickListener.onItemClick(v, viewHolder , position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return onItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, datas.get(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = datas.size();
        return itemCount;
    }


    public List<T> getDatas() {
        return datas;
    }

    public SingleTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        itemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public SingleTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        itemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return itemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(SingleTypeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 转换数据，需要在使用时实现
     */
    protected abstract void convert(ViewHolder holder, T t, int position);
}
