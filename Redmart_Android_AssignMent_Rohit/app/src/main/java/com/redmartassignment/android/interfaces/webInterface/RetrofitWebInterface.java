package com.redmartassignment.android.interfaces.webInterface;

import com.redmartassignment.android.model.productDetails.ProductDetailsResponse;
import com.redmartassignment.android.model.productList.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitWebInterface {

    @GET("/v1.6.0/catalog/search")
    Call<ProductListResponse> getAllProducts(@Query("page") String pageCount, @Query("pageSize") String pageSize);


    @GET("/v1.6.0/catalog/products/{product_id}")
    Call<ProductDetailsResponse> getProductDetails(@Path("product_id") String productId);


}
