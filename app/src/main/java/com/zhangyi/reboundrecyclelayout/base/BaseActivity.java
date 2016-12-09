package com.zhangyi.reboundrecyclelayout.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Activity的基类
 */

public abstract class BaseActivity extends FragmentActivity {

    /**
     * 布局中Activity的ID
     */
    protected abstract int getActivityContentId();
    /**
     * 初始化View，在onCreate调用
     */
    protected abstract void initView(Bundle savedInstanceState);


    /**
     * 生命周期方法onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityContentId());
        initView(savedInstanceState);
    }


}
