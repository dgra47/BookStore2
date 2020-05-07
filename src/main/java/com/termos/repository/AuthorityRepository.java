package com.termos.repository;

import com.termos.dto.AbstractDTO;
import com.termos.model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthorityRepository implements AbstractRepository<Authority> {
    private DataSource dataSource;

    @Autowired
    public AuthorityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Authority getById(String id) {
        throw new UnsupportedOperationException("Not implmented, yet");
    }

    @Override
    public Authority persist(Authority t) {
        throw new UnsupportedOperationException("Not implmented, yet");
    }

    @Override
    public String remove(String id) {
        throw new UnsupportedOperationException("Not implmented, yet");
    }

    @Override
    public List<Authority> findAll() {
        List<Authority> list = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            ResultSet rs = connection.prepareStatement("select * from authorities").executeQuery();
            while (rs.next()) {
                Authority auth = map(rs);
                list.add(auth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, List<Authority>> findAllByUserName() {
        Map<String, List<Authority>> map = new HashMap<>();
        try {
            Connection connection = dataSource.getConnection();
            ResultSet rs = connection.prepareStatement("select * from authorities").executeQuery();
            while (rs.next()) {
                Authority auth = map(rs);
                List<Authority> list = map.get(auth.getUsername());
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(auth.getUsername(), list);
                }
                list.add(auth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<Authority> getAuthoritiesByUsername(String username) {
        List<Authority> list = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement prst = connection.prepareStatement("select * from authorities WHERE username = ?");
            prst.setString(1, username);
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                Authority auth = map(rs);
                list.add(auth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Authority map(ResultSet rsAuth) throws SQLException {
        Authority a = new Authority();
        a.setUsername(rsAuth.getString(1));
        a.setAuthority(rsAuth.getString(2));
        return a;
    }

    @Override
    public Authority dtoToModel(AbstractDTO dto) {
        throw new UnsupportedOperationException("Not implmented, yet");
    }

    @Override
    public AbstractDTO modelToDto(Authority t) {
        throw new UnsupportedOperationException("Not implmented, yet");
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not implmented, yet");
    }
}