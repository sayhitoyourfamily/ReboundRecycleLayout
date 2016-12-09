package com.zhangyi.reboundrecyclelayout.view;


import com.zhangyi.reboundrecyclelayout.bean.ChapterBean;
import com.zhangyi.reboundrecyclelayout.bean.ProductionBean;

import java.util.List;

/**
 * Created by zhangyi on 2016/12/2.
 * <p>
 */

public interface ProductionDetailFragmentView extends FragmentBaseView {


    /**
     * 显示章节列表数据
     */
    void showChapterList(List<ChapterBean> chapterBeanList);


    /**
     * 显示作品信息
     */
    void showProductionInfo(ProductionBean productionBean);


}
