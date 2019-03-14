package kite.amibee.com.netstore.model.pojo.home;

import com.google.gson.annotations.SerializedName;

import kite.amibee.com.netstore.model.pojo.category.Embeded;
import kite.amibee.com.netstore.model.pojo.filter.BrandFilter;
import kite.amibee.com.netstore.model.pojo.productAttr.ProductDetailModel;
import kite.amibee.com.netstore.model.pojo.signin.SignInDetailsModel;

public class SearchModel {
    @SerializedName("category")
    Embeded category;
    @SerializedName("brand")
    BrandFilter brand;
    @SerializedName("product")
    ProductDetailModel product;

    public Embeded getCategory() {
        return category;
    }

    public void setCategory(Embeded category) {
        this.category = category;
    }

    public BrandFilter getBrand() {
        return brand;
    }

    public void setBrand(BrandFilter brand) {
        this.brand = brand;
    }

    public ProductDetailModel getProduct() {
        return product;
    }

    public void setProduct(ProductDetailModel product) {
        this.product = product;
    }


    @Override
    public String toString() {
        return "SearchModel{" +
                "category=" + category +
                ", brand=" + brand +
                ", product=" + product +
                '}';
    }
}
