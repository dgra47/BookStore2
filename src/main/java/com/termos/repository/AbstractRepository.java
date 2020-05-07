package com.termos.repository;

import com.termos.dto.AbstractDTO;
import com.termos.model.AbstractModel;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public interface AbstractRepository<T extends AbstractModel> {
    T getById(String id);
    T persist(T t);
    String remove(String id);
    List<T> findAll();
    T map(ResultSet rslt) throws SQLException;
    T dtoToModel(AbstractDTO dto);
    AbstractDTO modelToDto(T t);
    int count();
}