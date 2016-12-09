package com.zhangyi.reboundrecyclelayout.view;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;


import com.zhangyi.reboundrecyclelayout.R;
import com.zhangyi.reboundrecyclelayout.adapter.ChapterRvAdapter;
import com.zhangyi.reboundrecyclelayout.base.BaseFragment;
import com.zhangyi.reboundrecyclelayout.bean.ChapterBean;
import com.zhangyi.reboundrecyclelayout.bean.ProductionBean;
import com.zhangyi.reboundrecyclelayout.customview.DividerItemDecoration;
import com.zhangyi.reboundrecyclelayout.customview.ReboundRecycleLayout;
import com.zhangyi.reboundrecyclelayout.customview.ryadapter.RecyclerAdapterWithHF;
import com.zhangyi.reboundrecyclelayout.presenter.ProductionDetailPresenter;
import com.zhangyi.reboundrecyclelayout.presenter.ProductionDetailPresenterImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2016/12/2.
 * <p>
 * 作品（电台或有声书）信息的详情页fragment
 */
public class ProductionDetailFragment extends BaseFragment implements View.OnClickListener, ProductionDetailFragmentView {

    private ImageView playListTitleabrBackIv;
    private TextView playListTitleabrTitleTv;
    private RecyclerView chapterListRv;
    private View loadingView;
    private View netErrorView;
    private View convertView;
    private List<ChapterBean> chapterBeanList;
    private RecyclerAdapterWithHF recyclerAdapterWithHF;
    private ProductionDetailHeader productionDetailHeader;
    private ProductionDetailPresenter productionDetailPresenter;

    /**
     * 每一页的条数
     */
    private final int PAGESIZE = 20;

    /**
     * @param title 页面标题
     */
    public static ProductionDetailFragment newInstance(String title) {
        ProductionDetailFragment fragment = new ProductionDetailFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_production_detail;
    }


    @Override
    protected void initData() {
        if (getArguments() != null) {
            playListTitleabrTitleTv.setText(getArguments().getString("title"));
        }
        productionDetailPresenter = new ProductionDetailPresenterImp(this);
        productionDetailPresenter.loadProductionDetailData();
        productionDetailPresenter.loadChapterListData(1);
    }

    private void setListener() {
        playListTitleabrBackIv.setOnClickListener(this);
        playListTitleabrTitleTv.setOnClickListener(this);
        chapterListRv.setOnClickListener(this);
    }

    @Override
    protected void initView(View convertView) {
        this.convertView = convertView;
        loadingView = convertView.findViewById(R.id.play_list_loading_view);
        playListTitleabrBackIv = (ImageView) convertView.findViewById(R.id.play_list_titleabr_back_iv);
        playListTitleabrTitleTv = (TextView) convertView.findViewById(R.id.play_list_titleabr_title_tv);
        chapterListRv = (RecyclerView) convertView.findViewById(R.id.play_list_rv);
        chapterBeanList = new ArrayList<>();
        ChapterRvAdapter chapterRvAdapter = new ChapterRvAdapter(getCurActivity(), R.layout.item_chapter, chapterBeanList);
        recyclerAdapterWithHF = new RecyclerAdapterWithHF(chapterRvAdapter, getCurActivity());
        chapterListRv.setLayoutManager(new LinearLayoutManager(getCurActivity()));
        productionDetailHeader = new ProductionDetailHeader(getCurActivity());
        recyclerAdapterWithHF.addHeader(productionDetailHeader.inflate());
        chapterListRv.setAdapter(recyclerAdapterWithHF);
        chapterListRv.addItemDecoration(new DividerItemDecoration(getCurActivity(), DividerItemDecoration.VERTICAL_LIST, DividerItemDecoration.WHITE, 1));
        setListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_list_titleabr_back_iv:
                getCurActivity().onBackPressed();
                break;
        }
    }


    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showDataErrorView(String message) {

    }

    @Override
    public void hideDataErrorView() {

    }

    @Override
    public void showNetErrorView() {
        if (netErrorView == null) {
            ViewStub viewStub = (ViewStub) convertView.findViewById(R.id.play_net_error_view);
            netErrorView = viewStub.inflate();
            netErrorView.setVisibility(View.VISIBLE);
        } else {
            netErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideNetErrorView() {
        if (netErrorView != null) {
            netErrorView.setVisibility(View.GONE);
        }
    }


    @Override
    public void showChapterList(List<ChapterBean> chapterBeanList) {
        this.chapterBeanList.addAll(chapterBeanList);
        recyclerAdapterWithHF.notifyDataSetChanged();
    }

    @Override
    public void showProductionInfo(ProductionBean productionBean) {
        ((ReboundRecycleLayout) chapterListRv.getParent()).getBackgroundImageView().setImageResource(R.drawable.book_cover_1);
        productionDetailHeader.updateUI(productionBean);
    }
}
