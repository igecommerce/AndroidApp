package kite.amibee.com.netstore.model.pojo.productAttr;

import com.google.gson.annotations.SerializedName;

public class ProductDetailModel {
    @SerializedName("categoryId")
    String categoryId;
    @SerializedName("brandId")
    String brandId;
    @SerializedName("productId")
    String productId;
    @SerializedName("sku")
    String sku;
    @SerializedName("name")
    String name;
    @SerializedName("price")
    String price;
    @SerializedName("specialPrice")
    String specialPrice;
    @SerializedName("splPriceStartDate")
    String splPriceStartDate;
    @SerializedName("splPriceEndDate")
    String splPriceEndDate;
    @SerializedName("imageUrl")
    String imageUrl;
    @SerializedName("imageLabel")
    String imageLabel;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getSplPriceStartDate() {
        return splPriceStartDate;
    }

    public void setSplPriceStartDate(String splPriceStartDate) {
        this.splPriceStartDate = splPriceStartDate;
    }

    public String getSplPriceEndDate() {
        return splPriceEndDate;
    }

    public void setSplPriceEndDate(String splPriceEndDate) {
        this.splPriceEndDate = splPriceEndDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(String imageLabel) {
        this.imageLabel = imageLabel;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @SerializedName("stock")
    String stock;
    @SerializedName("measurement")
    String measurement;
    @SerializedName("stockStatus")
    String stockStatus;
    @SerializedName("imagePath")
    String imagePath;
    @SerializedName("key")
    String key;
    @SerializedName("caption")
    String caption;

}
