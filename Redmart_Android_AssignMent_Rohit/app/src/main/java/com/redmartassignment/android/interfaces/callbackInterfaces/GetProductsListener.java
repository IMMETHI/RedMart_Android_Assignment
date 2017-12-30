package com.redmartassignment.android.interfaces.callbackInterfaces;

import com.redmartassignment.android.model.productList.ProductListResponse;

public interface GetProductsListener {

    void onProductListSuccess(ProductListResponse productsListResponse);

    void onProductListError(String errorMsg);


}
