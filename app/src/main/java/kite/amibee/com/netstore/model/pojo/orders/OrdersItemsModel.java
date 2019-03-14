package kite.amibee.com.netstore.model.pojo.orders;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kite.amibee.com.netstore.model.pojo.AddressModel;

public class OrdersItemsModel {
    @SerializedName("id")
    String id;
    @SerializedName("orderId")
    String orderId;
    @SerializedName("productName")
    String productName;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @SerializedName("price")
    float price;
    @SerializedName("cost")
    float cost;
    @SerializedName("quantity")
    String quantity;
    @SerializedName("sku")
    String sku;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }



    @SerializedName("productType")
    String productType;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @SerializedName("createdDate")
    String createdDate;
    @SerializedName("updatedDate")
    String updatedDate;



}
