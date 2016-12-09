package com.zhangyi.reboundrecyclelayout.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    private View convertView;

    private Activity activity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(getLayoutId(), container, false);
        initView(convertView);
        lazyLoadData();
        return convertView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public Activity getCurActivity() {
        return activity != null ? activity : getActivity();
    }

    private void lazyLoadData() {
        initData();
    }

    /**
     * 加载页面布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
    protected abstract void initView(View convertView);

    /**
     * 加载要显示的数据
     */
    protected abstract void initData();


}
