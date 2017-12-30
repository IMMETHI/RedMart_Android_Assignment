package com.redmartassignment.android.views;

import com.redmartassignment.android.model.productList.Image;
import com.redmartassignment.android.model.productList.Primary;

import java.util.List;

public interface ProductDetailsView {


    void updateTitleAndSizeDetails(String productName, String productDetails);

    void updateAmountDetails(String currentAmount, String originalAmount, String savingMsg, int savingBgColor);

    void updateImageGallery(List<Image> images);

    void updateProductMetaDescriptions(List<Primary> metaDetailsList);

    void showThisErrorMsg(int stringResourceId);

}
