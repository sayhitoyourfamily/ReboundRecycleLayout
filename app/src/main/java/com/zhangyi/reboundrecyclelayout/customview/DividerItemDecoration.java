package com.zhangyi.reboundrecyclelayout.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.zhangyi.reboundrecyclelayout.R;

import static android.support.v7.widget.RecyclerView.ItemDecoration;

/**
 * RecyclerView的分割线
 */
public class DividerItemDecoration extends ItemDecoration {


    public static final int HORIZONTAL_LIST = 0;

    public static final int VERTICAL_LIST = 1;


    /**
     * 分割线的颜色,白色
     */
    public static final int WHITE = 1;

    private Drawable mDivider;
    private int tag = 0;
    private int mOrientation;


    /**
     * @param context
     * @param orientation 分割线分向
     * @param type        分割线类型
     * @param tag  底部忽略分割线的数目 如tag =1,则最后一个item下方没有分割线
     */
    public DividerItemDecoration(Context context, int orientation, int type, int tag) {
        this.tag = tag;
        setDivider(context, type);
        setOrientation(orientation);
    }

    private void setDivider(Context context, int type) {
        switch (type) {
            case WHITE:
                mDivider = context.getResources().getDrawable(R.drawable.kting_radio_list_divider_white);
                break;
        }
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
//            final int left = child.getRight() + params.rightMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = mDivider.getIntrinsicWidth();
            Log.e("DividerItemDecoration", i + " x:" + left + " y:" + top + " width:" + right + " height:" + bottom);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}