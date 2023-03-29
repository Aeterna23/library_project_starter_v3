package com.spark.library.dao;

import java.util.List;

public interface Dao<K,V>{

    List<V> findAll();
    V findById(K id);
    void save(V model);
    void update(K id, V model);
     void delete (K id);
}
