package kite.amibee.com.netstore.intercom;

import java.util.List;

import kite.amibee.com.netstore.model.pojo.filter.BrandFilter;

public interface Intercomm {
    void brandList(List<BrandFilter> brandList);
}
