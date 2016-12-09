package com.zhangyi.reboundrecyclelayout.model;


import com.zhangyi.reboundrecyclelayout.bean.ChapterBean;
import com.zhangyi.reboundrecyclelayout.bean.ProductionBean;

import java.util.List;

/**
 * Created by zhangyi on 2016/12/8.
 * <p>
 */

public interface ProductionDetailLoadListener {
    void onProductionSuccess(ProductionBean productionBean);

    void onChapterListSuccess(List<ChapterBean> list);

    void onProductionFailed(String message);

    void onChapterListFailed(String message, int page);
}
