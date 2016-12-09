package com.zhangyi.reboundrecyclelayout.model;

import android.os.Handler;
import android.os.Looper;

import com.zhangyi.reboundrecyclelayout.bean.ChapterBean;
import com.zhangyi.reboundrecyclelayout.bean.ProductionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2016/12/2.
 */

public class ProductionDetailModelImp implements ProductionDetailModel {


    @Override
    public void loadChapterListData(final ProductionDetailLoadListener productionDetailLoadListener, final int page) {
        new Thread() {
            @Override
            public void run() {
                final List<ChapterBean> chapterBeanList = new ArrayList<>();
                for (int i = 20 * (page - 1); i < 40 * page; i++) {
                    ChapterBean chapterBean = new ChapterBean();
                    chapterBean.setName("笑傲江湖" + i);
                    chapterBean.setId(i);
                    chapterBean.setSize("6");
                    chapterBean.setDuration("66");
                    chapterBean.setPlayNumber("666");
                    chapterBean.setDownload(i % 3);
                    chapterBeanList.add(chapterBean);
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        productionDetailLoadListener.onChapterListSuccess(chapterBeanList);
                    }
                });
            }
        }.start();
    }

    @Override
    public void loadProductionDetailData(final ProductionDetailLoadListener productionDetailLoadListener) {
        new Thread() {
            @Override
            public void run() {
                final ProductionBean productionBean = new ProductionBean();
                productionBean.setImage("http://img2.imgtn.bdimg.com/it/u=1552459397,13228665&fm=21&gp=0.jpg");
                productionBean.setAnchor("金庸");
                productionBean.setIntroduction("有情有义,率性而为,笑傲江湖");
                productionBean.setName("笑傲江湖");
                productionBean.setChapterNumber("666");
                productionBean.setPlayNumber("666");
                productionBean.setSubscribeNumber("666");
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        productionDetailLoadListener.onProductionSuccess(productionBean);
                    }
                });
            }
        }.start();
    }
}
