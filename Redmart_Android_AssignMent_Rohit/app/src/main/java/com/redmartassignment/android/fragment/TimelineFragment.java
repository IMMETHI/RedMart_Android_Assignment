package com.redmartassignment.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.redmartassignment.android.BaseFragment;
import com.redmartassignment.android.R;
import com.redmartassignment.android.adapters.ProductListAdapter;
import com.redmartassignment.android.presenters.TimelinePresenter;
import com.redmartassignment.android.services.TimelineService;
import com.redmartassignment.android.views.TimelineView;
import com.redmartassignment.android.interfaces.callbackInterfaces.OnProductClickListener;
import com.redmartassignment.android.model.productList.Product;
import com.redmartassignment.android.utils.GridSpacingItemDecoration;
import com.redmartassignment.android.utils.Utils;
import com.redmartassignment.android.widgets.RobotoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class TimelineFragment extends BaseFragment implements TimelineView, OnProductClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.errorTextView)
    RobotoTextView errorTextView;

    private TimelinePresenter timelinePresenter;
    private ProductListAdapter productListAdapter;
    private boolean isLoading = false;
    private int pageCount = 0;
    private GridLayoutManager gridLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, view);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()), true));
        timelinePresenter = new TimelinePresenter(getActivity(), new TimelineService(), this, recyclerView);
        return view;
    }


    @Override
    public boolean isBackKeyConsumed() {
        Timber.d("-- isBackKeyConsumed --");
        return false;
    }


    @Override
    public String getFragmentTitle() {
        return "RedMart";
    }


    @Override
    public void showMainProgressBar() {
        Timber.d("-- showMainProgressBar --");
        isLoading = true;
        recyclerView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void showThisErrorMsg(String errorMsg) {
        Timber.d("-- showThisErrorMsg --");
        if (progressBar.getVisibility() == View.VISIBLE) {
            errorTextView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            errorTextView.setText(errorMsg);
        } else {
            Utils.showLongToast(activity, errorMsg);
        }
    }


    @Override
    public void showOrUpdateRecyclerView(List<Product> products) {
        Timber.d("-- showOrUpdateRecyclerView --");
        if (productListAdapter == null) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            productListAdapter = new ProductListAdapter(activity, products, this);
            recyclerView.setAdapter(productListAdapter);
        } else {
            productListAdapter.removeLoadingFooter();
            productListAdapter.getDiscoverDataTables().addAll(products);
            productListAdapter.notifyItemRangeInserted(pageCount, productListAdapter.getDiscoverDataTables().size());
        }
        pageCount = productListAdapter.getDiscoverDataTables().size();
        isLoading = false;
    }


    @Override
    public void showFooterLoader() {
        Timber.d("-- showFooterLoader --");
        isLoading = true;
        productListAdapter.addLoadingFooter();
    }


    @Override
    public void callDetailViewForThisProduct(Intent intent, ActivityOptionsCompat activityOptions) {
        Timber.d("-- callDetailViewForThisProduct --");
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
    }

    @Override
    public void onProductClicked(Product product, View imageView, View amountView) {
        Timber.d("-- onProductClicked --");
        timelinePresenter.onProductClicked(activity, product, imageView, amountView);
    }

    @Override
    public RecyclerView getProductRecyclerView() {
        Timber.d("-- getProductRecyclerView --");
        return recyclerView;
    }

    @Override
    public boolean isLoading() {
        Timber.d("-- isLoading --");
        return isLoading;
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        Timber.d("-- getLayoutManager --");
        return gridLayoutManager;
    }

    @Override
    public int getPageCount() {
        Timber.d("-- getPageCount --");
        return pageCount;
    }

}
