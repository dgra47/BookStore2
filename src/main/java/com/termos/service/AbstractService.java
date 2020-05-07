package com.termos.service;

import com.termos.dto.AbstractDTO;
import java.util.stream.Stream;

public interface AbstractService<T extends AbstractDTO>{
    T persistNew(T t);
    String remove(String id);
    T getById(String id);
    Stream<T> findAll();
    int getRecordCount();
}