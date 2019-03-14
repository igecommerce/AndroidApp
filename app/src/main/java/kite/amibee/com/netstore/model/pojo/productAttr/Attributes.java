package kite.amibee.com.netstore.model.pojo.productAttr;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import kite.amibee.com.netstore.model.pojo.home.HomeProducts;

public class Attributes implements Comparable<Attributes> {
    private static final String TAG = "Attributes";
    @SerializedName("categoryId")
    public String categoryId;
    @SerializedName("productId")
    public int productId;
    @SerializedName("price")
    public float price;
    @SerializedName("specialPrice")
    public float specialPrice;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("imageLabel")
    public String imageLabel;
    String tag;
    public Attributes(String tag,String categoryId,int productId,float price,float specialPrice,String imageUrl,String imageLabel){
        this.tag=tag;
        this.categoryId=categoryId;
        this.productId=productId;
        this.price=price;
        this.specialPrice=specialPrice;
        this.imageUrl=imageUrl;
        this.imageLabel=imageLabel;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(float specialPrice) {
        this.specialPrice = specialPrice;
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



    public int compareTo(Attributes price) {
        try{
            Log.w(TAG, "compareTo: tag "+tag );
            if (tag!=null){
                if (tag.equals("lowtohigh")){
                    Float s_price = specialPrice;
                    Float c_price = price.specialPrice;
                    if (s_price == c_price)
                        return 0;
                    else if (s_price > c_price)
                        return 1;
                    else
                        return -1;
                } else {
                    Float s_price = specialPrice;
                    Float c_price = price.specialPrice;
                    if (s_price == c_price)
                        return 0;
                    else if (s_price < c_price)
                        return 1;
                    else
                        return -1;
                }
            }else {
                Float s_price = specialPrice;
                Float c_price = price.specialPrice;
                if (s_price == c_price)
                    return 0;
                else if (s_price < c_price)
                    return 1;
                else
                    return -1;
            }


        }catch (NumberFormatException e){
            Log.e(TAG, "compareTo: NumberFormatException "+e );
        }catch (NullPointerException e){
            Log.e(TAG, "compareTo: NullPointerException "+e );
        }catch (Exception e){
            Log.e(TAG, "compareTo: Exception "+e );
        }
        return 0;
    }
}
