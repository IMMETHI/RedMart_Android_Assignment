package com.redmartassignment.android.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.redmartassignment.android.presenters.TimelinePresenter;
import com.redmartassignment.android.services.TimelineService;
import com.redmartassignment.android.views.TimelineView;
import com.redmartassignment.android.interfaces.callbackInterfaces.GetProductsListener;
import com.redmartassignment.android.model.productList.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimelineFragmentTest {


    @Mock
    private List<Product> productsList;
    @Mock
    Product product;
    @Mock
    TimelineView timelineView;
    @Mock
    TimelineService timelineService;
    @Mock
    Context context;
    @Mock
    Activity activity;
    @Mock
    View imageView;
    @Mock
    View amountView;
    @Mock
    RecyclerView recyclerView;
    @Mock
    GetProductsListener getProductsListener;

    TimelinePresenter timelinePresenter;


    @Before
    public void setUp() throws Exception {
        timelinePresenter = new TimelinePresenter(context, timelineService, timelineView, recyclerView);
    }

    @Test
    public void testForMainProductListingSuccessCase() throws Exception {
        timelinePresenter.loadProducts();
        verify(timelineView, atLeastOnce()).showMainProgressBar();
        verify(timelineService, atLeastOnce()).getProductList(0, timelinePresenter.getProductsListener);
    }

    @Test
    public void testForMainProductListingErrorCase() throws Exception {
        timelinePresenter.getProductsListener.onProductListError("something wrong");
        verify(timelineView, atLeastOnce()).showThisErrorMsg("something wrong");
    }

    @Test
    public void testForProductClickedFlow() throws Exception {
        timelinePresenter.onProductClicked(activity, product, imageView, amountView);
        timelineView.callDetailViewForThisProduct(any(Intent.class), any(ActivityOptionsCompat.class));
    }


    @Test
    public void testForLoadMoreScroller() throws Exception {
        when(timelineView.getLayoutManager()).thenReturn(new GridLayoutManager(activity, 2));
        when(timelineView.isLoading()).thenReturn(false);
        timelinePresenter.recyclerViewOnScrollListener.onScrolled(recyclerView, 1, 1);
        verify(timelineService, atLeastOnce()).getProductList(0, timelinePresenter.getProductsListener);
    }


    @Test
    public void testForCurrentLoadingOnCase() throws Exception {
        when(timelineView.getLayoutManager()).thenReturn(new GridLayoutManager(activity, 2));
        when(timelineView.isLoading()).thenReturn(true);
        timelinePresenter.recyclerViewOnScrollListener.onScrolled(recyclerView, 1, 1);
        verify(timelineService, never()).getProductList(1, timelinePresenter.getProductsListener);
    }



    @Test
    public void testForPageCountCompleteForLoadMoreCase() throws Exception {
        when(timelineView.getLayoutManager()).thenReturn(new GridLayoutManager(activity, 2));
        when(timelineView.isLoading()).thenReturn(true);
        timelinePresenter.isLastPage = true;
        timelinePresenter.recyclerViewOnScrollListener.onScrolled(recyclerView, 1, 1);
        verify(timelineService, never()).getProductList(1, timelinePresenter.getProductsListener);
    }


}