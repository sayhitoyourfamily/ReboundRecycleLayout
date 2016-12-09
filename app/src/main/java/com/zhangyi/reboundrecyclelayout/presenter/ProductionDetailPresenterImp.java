package com.zhangyi.reboundrecyclelayout.presenter;


import com.zhangyi.reboundrecyclelayout.bean.ChapterBean;
import com.zhangyi.reboundrecyclelayout.bean.ProductionBean;
import com.zhangyi.reboundrecyclelayout.model.ProductionDetailLoadListener;
import com.zhangyi.reboundrecyclelayout.model.ProductionDetailModel;
import com.zhangyi.reboundrecyclelayout.model.ProductionDetailModelImp;
import com.zhangyi.reboundrecyclelayout.view.ProductionDetailFragmentView;

import java.util.List;

/**
 * Created by zhangyi on 2016/12/2.
 */

public class ProductionDetailPresenterImp implements ProductionDetailPresenter, ProductionDetailLoadListener {
    private ProductionDetailFragmentView productionDetailFragmentView;
    private ProductionDetailModel productionDetailModel;

    public ProductionDetailPresenterImp(ProductionDetailFragmentView productionDetailFragmentView) {
        this.productionDetailFragmentView = productionDetailFragmentView;
        productionDetailModel = new ProductionDetailModelImp();
    }

    @Override
    public void loadChapterListData(int page) {
        productionDetailModel.loadChapterListData(this, page);
    }

    @Override
    public void loadProductionDetailData() {
        productionDetailFragmentView.showLoading();
        productionDetailModel.loadProductionDetailData(this);
    }

    @Override
    public void onProductionSuccess(ProductionBean productionBean) {
        productionDetailFragmentView.hideNetErrorView();
        productionDetailFragmentView.hideDataErrorView();
        productionDetailFragmentView.hideLoading();
        productionDetailFragmentView.showProductionInfo(productionBean);
    }

    @Override
    public void onChapterListSuccess(List<ChapterBean> list) {
        productionDetailFragmentView.showChapterList(list);
    }

    @Override
    public void onProductionFailed(String message) {
        productionDetailFragmentView.hideLoading();
        productionDetailFragmentView.showDataErrorView(message);
    }

    @Override
    public void onChapterListFailed(String message, int page) {

    }
}
