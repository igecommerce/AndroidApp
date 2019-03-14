package kite.amibee.com.netstore.model.pojo.fav;

import com.google.gson.annotations.SerializedName;

public class Fav {
    @SerializedName("id")
    int id;
    @SerializedName("customerId")
    int customerId;
    @SerializedName("productId")
    int productId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("status")
    int status;
}
