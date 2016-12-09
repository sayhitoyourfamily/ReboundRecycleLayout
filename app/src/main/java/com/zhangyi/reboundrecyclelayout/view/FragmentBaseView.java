package com.zhangyi.reboundrecyclelayout.view;

/**
 * Created by zhangyi on 2016/12/2.
 * <p>
 * fragment页面里面的通用接口
 */

public interface FragmentBaseView {
    /**
     * 展示loading页面
     */
    void showLoading();

    /**
     * 隐藏loading页面
     */
    void hideLoading();


    /**
     * 显示数据加载失败的页面
     */
    void showDataErrorView(String message);

    /**
     * 隐藏数据加载失败的页面
     */
    void hideDataErrorView();

    /**
     * 显示网络异常的页面
     */
    void showNetErrorView();

    /**
     * 隐藏网络异常的页面
     */
    void hideNetErrorView();
}
