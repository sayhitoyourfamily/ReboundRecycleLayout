package com.zhangyi.reboundrecyclelayout.model;

/**
 * Created by zhangyi on 2016/12/2.
 */

public interface ProductionDetailModel {
    /**
     * 加载章节列表数据
     */
    void loadChapterListData(ProductionDetailLoadListener productionDetailLoadListener, int page);

    /**
     * 加载作品详情信息
     *
     * @param productionDetailLoadListener
     */
    void loadProductionDetailData(ProductionDetailLoadListener productionDetailLoadListener);

}
