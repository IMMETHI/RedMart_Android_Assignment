package com.redmartassignment.android.interfaces.callbackInterfaces;

import android.view.View;

import com.redmartassignment.android.model.productList.Product;

public interface OnProductClickListener {

    void onProductClicked(Product product, View imageView, View amountView);

}
