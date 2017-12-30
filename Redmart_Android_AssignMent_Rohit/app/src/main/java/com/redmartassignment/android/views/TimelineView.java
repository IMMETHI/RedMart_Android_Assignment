package com.redmartassignment.android.views;


import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.redmartassignment.android.model.productList.Product;

import java.util.List;

public interface TimelineView {

    void showMainProgressBar();

    void showThisErrorMsg(String errorMsg);

    void showOrUpdateRecyclerView(List<Product> products);

    void showFooterLoader();

    void callDetailViewForThisProduct(Intent intent, ActivityOptionsCompat activityOptions);

    RecyclerView getProductRecyclerView();

    boolean isLoading();

    GridLayoutManager getLayoutManager();

    int getPageCount();

}
