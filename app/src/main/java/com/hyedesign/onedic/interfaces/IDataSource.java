package com.hyedesign.onedic.interfaces;

import java.util.List;

/**
 * Created by Albaloo on 2/21/2017.
 */

public interface IDataSource<T> {

    String LOGTAG = "DATASOURCE";

    int getCount();
    T create(T model);
    T update(T model);
    void delete(long id);
    void delete();
    T getById(long id);
    List<T> getAll();
    List<T> getFiltered(String selection, String orderby);

    // Commons
    void synce(long id);
    void softDelete(long id);
    void favorite(long id, String status);
    void seed();
}
