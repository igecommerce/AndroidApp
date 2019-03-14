package kite.amibee.com.netstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import kite.amibee.com.netstore.R;
import kite.amibee.com.netstore.activity.product.ProductListActivity;
import kite.amibee.com.netstore.model.pojo.filter.BrandFilter;

public class HomeBrandFilterAdapter extends RecyclerView.Adapter<HomeBrandFilterAdapter.ViewHolder> {
    private static final String TAG = "HomeBrandFilterAdapter";

    private LayoutInflater mInflater;
    View view;
    // data is passed into the constructor
    Context context;

    List<BrandFilter> brandList;
    int selection=0;
    public HomeBrandFilterAdapter(Context context, List<BrandFilter> brandList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.brandList = brandList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = mInflater.inflate(R.layout.filter_brand_adap_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_filter_brand.setText(brandList.get(position).getName());
        if (selection==position){
            holder.rb_filter_brand.setChecked(true);
        }else {
            holder.rb_filter_brand.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_filter_brand;
        RadioButton rb_filter_brand;

        @SuppressLint("NewApi")
        ViewHolder(View itemView) {
            super(itemView);
            tv_filter_brand = (TextView) itemView.findViewById(R.id.tv_filter_brand);
            rb_filter_brand = itemView.findViewById(R.id.rb_filter_brand);
            rb_filter_brand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selection=getAdapterPosition();
                    ((ProductListActivity)context).brandFilterId(brandList.get(selection).getBrandId());
                    notifyDataSetChanged();
                }
            });

        }
    }

    public void updateAdapter(List<BrandFilter> brandlist) {
        brandList = brandlist;
        notifyDataSetChanged();
    }

}
