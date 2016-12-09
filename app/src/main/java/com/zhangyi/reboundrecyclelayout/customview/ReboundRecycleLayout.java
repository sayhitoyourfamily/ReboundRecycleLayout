package com.zhangyi.reboundrecyclelayout.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

/**
 * Created by zhangyi on 2016/12/8.
 * <p>
 * 有弹性的RecycleView容器,有背景可以下拉放大，上拉时背景不动
 */

public class ReboundRecycleLayout extends RelativeLayout {
    /**
     * 记录手指按下是的y坐标
     */
    private int startY;
    /**
     * 记录要拖动的view
     */
    private RecyclerView reboundView;
    /**
     * 回弹动画的时间
     */
    private final int ANIMTIME = 300;
    /**
     * 是否可以下拉
     */
    private boolean canPullDown;
    /**
     * 是否可以上拉
     */
    private boolean canPullUp;
    private boolean isMoved;

    private ImageView imageView;
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout.LayoutParams reboundViewLayoutParams;


    private int moveY = 0;
    private final float FACTOR = 0.3f;

    private final int imageHeight = 470;
    private int imageHeightEnd = 0;
    private RecycleViewUtil recycleViewUtil;
    private ValueAnimator valueAnimator;


    public ReboundRecycleLayout(Context context) {
        this(context, null);
    }

    public ReboundRecycleLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundRecycleLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof RecyclerView) {
                reboundView = (RecyclerView) getChildAt(i);
                reboundView.setOverScrollMode(OVER_SCROLL_NEVER);
                reboundView.setLayoutParams(reboundViewLayoutParams);
            }
        }

    }

    public ImageView getBackgroundImageView() {
        return imageView;
    }

    private void init(Context context) {
        imageView = new ImageView(context);
        imageView.setPadding(0, 0, 0, 0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setImageResource(R.drawable.book_cover_1);
        layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, imageHeight);
        layoutParams.addRule(CENTER_HORIZONTAL);
        imageView.setLayoutParams(layoutParams);
        addView(imageView, 0);
        reboundViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        recycleViewUtil = new RecycleViewUtil();
        valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(ANIMTIME);
        valueAnimator.setIntValues(0, ANIMTIME);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (moveY > 0) {
                    layoutParams.height = imageHeight + (ANIMTIME - (int) animation.getAnimatedValue()) * (imageHeightEnd - imageHeight) / ANIMTIME;
                    imageView.requestLayout();
                    reboundViewLayoutParams.setMargins(0, (ANIMTIME - (int) animation.getAnimatedValue()) * moveY / ANIMTIME, 0, 0);
                } else if (moveY < 0) {
                    reboundViewLayoutParams.setMargins(0, 0, 0, (ANIMTIME - (int) animation.getAnimatedValue()) * -moveY / ANIMTIME);
                }
                reboundView.requestLayout();
            }
        });

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) e.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (isMoved) {
                    imageHeightEnd = layoutParams.height;
                    valueAnimator.start();
                }
                canPullDown = false;
                canPullUp = false;
                isMoved = false;
                break;
            case MotionEvent.ACTION_MOVE:
                canPullUp = isCanPullUp();
                canPullDown = isCanPullDown();
                if (!canPullUp && !canPullDown) {
                    startY = (int) e.getY();
                    break;
                }
                if (canPullDown) {
                    moveY = (int) ((e.getY() - startY) * FACTOR);
                    if (layoutParams.height < imageView.getWidth()) {
                        layoutParams.height = (int) (imageHeight + moveY);
                        imageView.requestLayout();
                    }
                    reboundViewLayoutParams.setMargins(0, moveY, 0, 0);
                    reboundView.requestLayout();
                    isMoved = true;
                }
                if (canPullUp) {
                    moveY = (int) ((e.getY() - startY) * FACTOR);
                    reboundViewLayoutParams.setMargins(0, 0, 0, -moveY);
                    reboundView.requestLayout();
                    isMoved = true;
                }

                break;
        }
        return super.dispatchTouchEvent(e);
    }

    private boolean isCanPullDown() {

        return recycleViewUtil.isRecyclerViewToTop(reboundView);
    }

    private boolean isCanPullUp() {
        return recycleViewUtil.isRecyclerViewToBottom(reboundView);
    }


    private class RecycleViewUtil {
        public boolean isRecyclerViewToTop(RecyclerView recyclerView) {
            if (recyclerView != null) {
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager == null) {
                    return true;
                }
                if (manager.getItemCount() == 0) {
                    return true;
                }

                if (manager instanceof LinearLayoutManager) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) manager;

                    int firstChildTop = 0;
                    if (recyclerView.getChildCount() > 0) {
                        // 处理item高度超过一屏幕时的情况
                        View firstVisibleChild = recyclerView.getChildAt(0);
                        if (firstVisibleChild != null && firstVisibleChild.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                            if (android.os.Build.VERSION.SDK_INT < 14) {
                                return !(ViewCompat.canScrollVertically(recyclerView, -1) || recyclerView.getScrollY() > 0);
                            } else {
                                return !ViewCompat.canScrollVertically(recyclerView, -1);
                            }
                        }

                        // 如果RecyclerView的子控件数量不为0，获取第一个子控件的top

                        // 解决item的topMargin不为0时不能触发下拉刷新
                        View firstChild = recyclerView.getChildAt(0);
                        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) firstChild.getLayoutParams();
                        firstChildTop = firstChild.getTop() - layoutParams.topMargin - getRecyclerViewItemTopInset(layoutParams) - recyclerView.getPaddingTop();
                    }

                    if (layoutManager.findFirstCompletelyVisibleItemPosition() < 1 && firstChildTop == 0) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * 通过反射获取RecyclerView的item的topInset
         *
         * @param layoutParams
         * @return
         */
        private int getRecyclerViewItemTopInset(RecyclerView.LayoutParams layoutParams) {
            try {
                Field field = RecyclerView.LayoutParams.class.getDeclaredField("mDecorInsets");
                field.setAccessible(true);
                // 开发者自定义的滚动监听器
                Rect decorInsets = (Rect) field.get(layoutParams);
                return decorInsets.top;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        public boolean isRecyclerViewToBottom(RecyclerView recyclerView) {
            if (recyclerView != null) {
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager == null || manager.getItemCount() == 0) {
                    return false;
                }

                if (manager instanceof LinearLayoutManager) {
                    // 处理item高度超过一屏幕时的情况
                    View lastVisibleChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    if (lastVisibleChild != null && lastVisibleChild.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                        if (android.os.Build.VERSION.SDK_INT < 14) {
                            return !(ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < 0);
                        } else {
                            return !ViewCompat.canScrollVertically(recyclerView, 1);
                        }
                    }

                    LinearLayoutManager layoutManager = (LinearLayoutManager) manager;

                    //findLastCompletelyVisibleItemPosition有时候不对，所以-2
                    if (layoutManager.findLastCompletelyVisibleItemPosition() >= layoutManager.getItemCount() - 2) {

                        ReboundRecycleLayout stickyNavLayout = getStickyNavLayout(recyclerView);
                        if (stickyNavLayout != null) {
                            // 处理BGAStickyNavLayout中findLastCompletelyVisibleItemPosition失效问题
                            View lastCompletelyVisibleChild = layoutManager.getChildAt(layoutManager.findLastCompletelyVisibleItemPosition());
                            if (lastCompletelyVisibleChild == null) {
                                return true;
                            } else {
                                // 0表示x，1表示y
                                int[] location = new int[2];
                                lastCompletelyVisibleChild.getLocationOnScreen(location);
                                int lastChildBottomOnScreen = location[1] + lastCompletelyVisibleChild.getMeasuredHeight();
                                stickyNavLayout.getLocationOnScreen(location);
                                int stickyNavLayoutBottomOnScreen = location[1] + stickyNavLayout.getMeasuredHeight();
                                return lastChildBottomOnScreen <= stickyNavLayoutBottomOnScreen;
                            }
                        } else {
                            return true;
                        }
                    }
                } else if (manager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) manager;

                    int[] out = layoutManager.findLastCompletelyVisibleItemPositions(null);
                    int lastPosition = layoutManager.getItemCount() - 1;
                    for (int position : out) {
                        if (position == lastPosition) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public ReboundRecycleLayout getStickyNavLayout(View view) {
            ViewParent viewParent = view.getParent();
            while (viewParent != null) {
                if (viewParent instanceof ReboundRecycleLayout) {
                    return (ReboundRecycleLayout) viewParent;
                }
                viewParent = viewParent.getParent();
            }
            return null;
        }

    }

}
