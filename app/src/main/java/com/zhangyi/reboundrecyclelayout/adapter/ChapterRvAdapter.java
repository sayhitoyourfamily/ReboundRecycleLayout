package com.zhangyi.reboundrecyclelayout.adapter;

import android.content.Context;


import com.zhangyi.reboundrecyclelayout.R;
import com.zhangyi.reboundrecyclelayout.customview.ryadapter.SingleTypeAdapter;
import com.zhangyi.reboundrecyclelayout.customview.ryadapter.base.ViewHolder;
import com.zhangyi.reboundrecyclelayout.bean.ChapterBean;

import java.util.List;

/**
 * Created by zhangyi on 2016/12/2.
 */

public class ChapterRvAdapter extends SingleTypeAdapter<ChapterBean> {

    public ChapterRvAdapter(Context context, int layoutId, List<ChapterBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ChapterBean chapterBean, int position) {
        if (chapterBean == null) {
            return;
        }
        holder.setText(R.id.item_chapter_name_tv, chapterBean.getName());
        holder.setText(R.id.item_chapter_playnumber_tv, chapterBean.getPlayNumber());
        holder.setText(R.id.item_chapter_duration_tv, chapterBean.getDuration());
        holder.setText(R.id.item_chapter_size_tv, chapterBean.getSize());
    }
}
