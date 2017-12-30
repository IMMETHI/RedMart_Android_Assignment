package com.redmartassignment.android.services;


import com.redmartassignment.android.model.productList.Product;

public class DetailViewService {

    private Product selectedProduct;

    public DetailViewService(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

}
