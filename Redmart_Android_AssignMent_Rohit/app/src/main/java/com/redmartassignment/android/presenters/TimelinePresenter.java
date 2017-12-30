package com.redmartassignment.android.presenters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.redmartassignment.android.activities.ProductDetailsActivity;
import com.redmartassignment.android.services.TimelineService;
import com.redmartassignment.android.views.TimelineView;
import com.redmartassignment.android.interfaces.callbackInterfaces.GetProductsListener;
import com.redmartassignment.android.model.productList.Product;
import com.redmartassignment.android.model.productList.ProductListResponse;

import timber.log.Timber;


public class TimelinePresenter {

    Context context;
    TimelineService timelineService;
    TimelineView timelineView;
    int pageCounter;
    public boolean isLastPage;

    public TimelinePresenter(Context context, TimelineService timelineService, TimelineView timelineView, RecyclerView recyclerView) {
        this.context = context;
        this.timelineService = timelineService;
        this.timelineView = timelineView;
        pageCounter = 0;
        isLastPage = false;
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        makeGetProductsRequest();
    }

    private void makeGetProductsRequest() {
        Timber.d("-- makeGetProductsRequest --");
        if (pageCounter == 0) {
            timelineView.showMainProgressBar();
        } else {
            timelineView.showFooterLoader();
        }
        timelineService.getProductList(pageCounter, getProductsListener);
    }

    public void loadProducts() {
        Timber.d("-- loadProducts --");
        pageCounter++;
        makeGetProductsRequest();
    }

    public void onProductClicked(Activity activity, Product product, View imageView, View amountView) {
        Timber.d("-- onProductClicked --");
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, new Pair<View, String>(imageView, "productImage"),
                new Pair<View, String>(amountView, "productName"));
        timelineView.callDetailViewForThisProduct(intent, activityOptions);
    }

    public GetProductsListener getProductsListener = new GetProductsListener() {
        @Override
        public void onProductListSuccess(ProductListResponse productsListResponse) {
            if (productsListResponse.getProducts().size() > 0) {
                isLastPage = false;
            } else {
                isLastPage = true;
            }
            timelineView.showOrUpdateRecyclerView(productsListResponse.getProducts());
        }

        @Override
        public void onProductListError(String errorMsg) {
            isLastPage = true;
            timelineView.showThisErrorMsg(errorMsg);
        }
    };


    public RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = timelineView.getLayoutManager().getChildCount();
            int totalItemCount = timelineView.getLayoutManager().getItemCount();
            int firstVisibleItemPosition = timelineView.getLayoutManager().findFirstVisibleItemPosition();

            if (!timelineView.isLoading() && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= timelineView.getPageCount()) {
                    loadProducts();
                }
            }
        }
    };
}
