package kite.amibee.com.netstore.model.pojo.filter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kite.amibee.com.netstore.model.pojo.AddressModel;

public class BrandFilter {
    @SerializedName("brandId")
    int brandId;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    String image;
    @SerializedName("status")
    int status;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @SerializedName("imageUrl")
    String imageUrl;
}
