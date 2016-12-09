package com.zhangyi.reboundrecyclelayout.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangyi.reboundrecyclelayout.R;
import com.zhangyi.reboundrecyclelayout.bean.ProductionBean;


/**
 * Created by zhangyi on 2016/12/8.
 * 作品（电台或有声书）信息的详情页的头部
 */

public class ProductionDetailHeader implements View.OnClickListener {
    private Context mContext;
    private View headView;
    private TextView productionHeaderName;
    private TextView productionHeaderAnchor;
    private TextView productionHeaderPlayNumber;
    private TextView productionHeaderSubscribeNumber;
    private TextView productionHeaderIntroduction;
    private TextView productionHeaderChapterNumber;

    public ProductionDetailHeader(Context mContext) {
        this.mContext = mContext;
    }

    public View inflate() {
        headView = View.inflate(mContext, R.layout.production_header, null);
        productionHeaderName = (TextView) headView.findViewById(R.id.production_header_name);
        productionHeaderAnchor = (TextView) headView.findViewById(R.id.production_header_anchor);
        productionHeaderPlayNumber = (TextView) headView.findViewById(R.id.production_header_play_number);
        productionHeaderSubscribeNumber = (TextView) headView.findViewById(R.id.production_header_subscribe_number);
        productionHeaderIntroduction = (TextView) headView.findViewById(R.id.production_header_introduction);
        productionHeaderChapterNumber = (TextView) headView.findViewById(R.id.production_header_chapter_number);
        headView.setVisibility(View.GONE);
        return headView;
    }

    /**
     * 更新头部信息
     *
     * @param productionBean
     */
    public void updateUI(ProductionBean productionBean) {
        if (productionBean == null || headView == null) {
            return;
        }
        headView.setVisibility(View.VISIBLE);
        productionHeaderName.setText(productionBean.getName());
        productionHeaderAnchor.setText(productionBean.getAnchor());
        productionHeaderPlayNumber.setText(productionBean.getPlayNumber());
        productionHeaderSubscribeNumber.setText(productionBean.getSubscribeNumber());
        productionHeaderIntroduction.setText(productionBean.getIntroduction());
        productionHeaderChapterNumber.setText("集数:" + productionBean.getChapterNumber());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.production_header_subscribe_btn:
                break;
            case R.id.production_header_introduction:
                break;
            case R.id.production_header_chapter_choose:
                break;
            case R.id.production_header_chapter_order:
                break;
        }
    }
}
