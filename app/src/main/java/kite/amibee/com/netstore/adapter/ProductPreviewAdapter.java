package kite.amibee.com.netstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kite.amibee.com.netstore.R;
import kite.amibee.com.netstore.activity.home.HomeActivity;
import kite.amibee.com.netstore.activity.product.ProductFullViewActivity;
import kite.amibee.com.netstore.model.pojo.home.HomeBannerImages;

public class ProductPreviewAdapter extends RecyclerView.Adapter<ProductPreviewAdapter.ViewHolder> {
    private static final String TAG = "ProductPreviewAdapter";
    private LayoutInflater mInflater;
    View view;
    Context context;
    List<String> imgList;
    private int lastSelectedPosition = 0;
    public ProductPreviewAdapter(Context context, List<?> imgList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.imgList = (List<String>) imgList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = mInflater.inflate(R.layout.product_adap_preview, parent, false);

        return new ViewHolder(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).
                load(imgList.get(position)).
                transition(DrawableTransitionOptions.withCrossFade()).
                listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.iv_product_small_1.setImageResource(R.drawable.logo);
                        Log.e(TAG, "onLoadFailed: url "+imgList.get(position) );
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }

                }).
                into(holder.iv_product_small_1);
                if (lastSelectedPosition==position){
                    holder.lay_img_preview.setBackgroundResource(R.drawable.layout_border_img_preview);
                    ((ProductFullViewActivity)context).imgPreviewUpdate(imgList.get(lastSelectedPosition));
                }else {
                    holder.lay_img_preview.setBackgroundResource(0);
                }



    }

    @Override
    public int getItemCount() {
        return this.imgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout lay_img_preview;
        ImageView iv_product_small_1;

        @SuppressLint("NewApi")
        ViewHolder(View itemView) {
            super(itemView);
            lay_img_preview=itemView.findViewById(R.id.lay_img_preview);
            iv_product_small_1= itemView.findViewById(R.id.iv_product_small_1);
            lay_img_preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void updateAdapter(List<String> imgList) {
        this.imgList = imgList;
        notifyDataSetChanged();
    }
}

