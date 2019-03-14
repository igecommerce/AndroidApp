package kite.amibee.com.netstore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kite.amibee.com.netstore.activity.product.ProductListActivity;
import kite.amibee.com.netstore.adapter.HomeBrandFilterAdapter;
import kite.amibee.com.netstore.intercom.Intercomm;
import kite.amibee.com.netstore.model.pojo.filter.BrandFilter;
import kite.amibee.com.netstore.util.api.Api;

public class TabFragmentBrand extends Fragment implements Intercomm {
    private static final String TAG = "TabFragmentBrand";
    RecyclerView lotion_rv_brand;
    HomeBrandFilterAdapter lotionBrandFilterAdapter;
    List<BrandFilter> brandList;
    SearchView search_brand;
    Api apical;
    private View.OnClickListener onClickListener;
    RelativeLayout lay_FilterCancel,lay_FilterApply;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        apical = new Api(getActivity(),this);
        brandList=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_brand, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: " );
        onClick();
        apical.productBrandFilter("");
        search_brand=view.findViewById(R.id.search_brand);
        lay_FilterCancel=view.findViewById(R.id.lay_bot_1);
        lay_FilterApply=view.findViewById(R.id.lay_bot_2);

        lay_FilterCancel.setOnClickListener(onClickListener);
        lay_FilterApply.setOnClickListener(onClickListener);

        lotion_rv_brand=(RecyclerView)view.findViewById(R.id.lotion_rv_brand);
        lotion_rv_brand.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        lotion_rv_brand.setItemAnimator(new DefaultItemAnimator());
        lotion_rv_brand.addItemDecoration(new SpacesItemDecoration(0,0, 0, 0));
        lotionBrandFilterAdapter = new HomeBrandFilterAdapter(getActivity(), brandList);
        lotion_rv_brand.setAdapter(lotionBrandFilterAdapter);

        search_brand.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                apical.productBrandFilter(newText);
                if (newText.length()>= 0){
                    apical.productBrandFilter(newText);
                }else {
                    lotionBrandFilterAdapter.updateAdapter(brandList);
                }

                return false;
            }
        });
    }



    public void onClick(){
        onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: cal " );
                if (lay_FilterApply.hashCode() == view.hashCode()){
                    Log.d(TAG, "onClick: cal filter " );
                    ((ProductListActivity)getActivity()).brandFilterList();
                }else if (lay_FilterCancel.hashCode()== view.hashCode()){
                    ((ProductListActivity)getActivity()).onBackPressed();
                }
            }
        };
    }

    @Override
    public void brandList(List<BrandFilter> brandList) {
        if (brandList!=null && brandList.size() >0){
            Log.d(TAG, "apiResponseBrandFilter: brandList "+brandList.size());
            lotionBrandFilterAdapter.updateAdapter(brandList);
        }else {
            Log.e(TAG, "apiResponseBrandFilter: brandList "+brandList);
            Toast.makeText(getActivity(),"No List",Toast.LENGTH_SHORT).show();
        }
    }
}
