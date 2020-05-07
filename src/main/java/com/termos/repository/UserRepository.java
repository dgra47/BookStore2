package com.termos.repository;

import com.termos.dto.AbstractDTO;
import com.termos.dto.UserDTO;
import com.termos.model.Authority;
import com.termos.utility.TimeUtils;
import com.termos.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class UserRepository implements AbstractRepository<User>{
    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;
    private AuthorityRepository authorityRepository;

    @Autowired
    public UserRepository(DataSource dataSource,PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public User getById(String id) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql="select * from users where user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                user = map(resultSet);}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getByEmail(String email) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql="select * from users where email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                user = map(resultSet);}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getByUserName(String userName) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql="select * from users where username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                user = map(resultSet);}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }



    @Override
    public User persist(User user) {
        if (exists(user.getUsername(),user.getEmail())) {
            return update(user);
        }
        return create(user);
    }

    @Override
    public String remove(String id) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "DELETE from users where user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            ResultSet rs = connection.prepareStatement("select * from users").executeQuery();
            Map<String, List<Authority>> allAuth = authorityRepository.findAllByUserName();
            while (rs.next()) {
                User user = map(rs);
                list.add(user);
                List<Authority> authList = allAuth.get(user.getUsername());
                user.setAuthority(authList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public User map(ResultSet rs) throws SQLException {
        return new User(rs.getString("user_id"),
                rs.getString("city"),
                rs.getString("first_name"),
                rs.getString("sur_name"),
                rs.getInt("user_tel"),
                rs.getTimestamp("reg_date"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getBoolean("enabled")
        );
    }

    @Override
    public User dtoToModel(AbstractDTO dto) {
        UserDTO userDTO = (UserDTO)dto;

        List<Authority> authList = new ArrayList<>();
        for (String authority: userDTO.authorities) {
            Authority a = new Authority();
            a.setUsername(userDTO.username);
            a.setAuthority(authority);
            authList.add(a);
        }
        User user = new User(userDTO.userId,
                userDTO.city,
                userDTO.firstName,
                userDTO.surName,
                userDTO.userTel,
                userDTO.regDate,
                userDTO.username,
                userDTO.password,
                userDTO.email,
                userDTO.enabled
        );
        user.setAuthority(authList);

        return user;
    }
    @Override
    public UserDTO modelToDto(User user) {
        var userDto = new UserDTO();
        if(user == null){
            return null;
        }

        userDto.enabled=user.isEnabled();
        List<Authority> authList = user.getAuthority();
        if (authList != null) {
            userDto.authorities = new String[authList.size()];
            for (int i = 0; i < authList.size(); i++) {
                userDto.authorities[i] = authList.get(i).getAuthority();
            }
        }
        userDto.city = user.getCity();
        userDto.email = user.getEmail();
        userDto.firstName = user.getFirstName();
        userDto.username = user.getUsername();
        userDto.password = user.getPassword();
        userDto.regDate = user.getRegDate();
        userDto.userTel = user.getUserTel();
        userDto.userId = user.getId();
        return userDto;
    }


    private boolean exists(String username,String email) {
        try {
            Connection connection = dataSource.getConnection();
            String sql =  "SELECT users from users where username=? AND email=?";
            PreparedStatement preparedStatement1 =
                    connection.prepareStatement(sql);
            preparedStatement1.setString(1, username);
            preparedStatement1.setString(2, email);
            ResultSet rs= preparedStatement1.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User create(User user) {
        Connection connection;
        try {
            String uuid = UUID.randomUUID().toString();
            connection = dataSource.getConnection();
            for (Authority auth: user.getAuthority()) {
                String authSql = "INSERT INTO authorities(username, authority) VALUES (?, ?)";
                PreparedStatement authPreparedStatement = connection.prepareStatement(authSql);
                authPreparedStatement.setString(1, auth.getUsername());
                authPreparedStatement.setString(2, auth.getAuthority());
                authPreparedStatement.executeUpdate();
            }
            String sql = "INSERT INTO users(user_id, city, first_name, sur_name, user_tel, reg_date, username, password, email,enabled) VALUES(?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, user.getCity());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getSurName());
            preparedStatement.setInt(5, user.getUserTel());
            preparedStatement.setTimestamp(6, TimeUtils.NowTimeStamp());
            preparedStatement.setString(7, user.getUsername());
            preparedStatement.setString(8, passwordEncoder.encode(user.getPassword()));
            preparedStatement.setString(9, user.getEmail());
            preparedStatement.setBoolean(10,user.isEnabled());
            preparedStatement.executeUpdate();
            user.setUserId(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
            user = null;
        }

        return user;
    }


    private User update(User user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement authPreparedStatement = connection.prepareStatement("DELETE FROM authorities WHERE username = ?");
            authPreparedStatement.setString(1, user.getUsername());
            authPreparedStatement.executeUpdate();
            for (Authority auth: user.getAuthority()) {
                String authSqlInserts = "INSERT INTO authorities(username, authority) VALUES (?, ?)";
                authPreparedStatement = connection.prepareStatement(authSqlInserts);
                authPreparedStatement.setString(1, user.getUsername());
                authPreparedStatement.setString(2, auth.getAuthority());
                authPreparedStatement.executeUpdate();
            }
            String sql =  "update users set city=?, first_name=?, sur_name=?, user_tel=?, reg_date=?, username=?, password=?, email=?, enabled=? where user_id=?";
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql)) {
                preparedStatement1.setString(1, user.getCity());
                preparedStatement1.setString(2, user.getFirstName());
                preparedStatement1.setString(3, user.getSurName());
                preparedStatement1.setInt(4, user.getUserTel());
                preparedStatement1.setTimestamp(5, TimeUtils.NowTimeStamp());
                preparedStatement1.setString(6, user.getUsername());
                preparedStatement1.setString(7, user.getPassword());
                preparedStatement1.setString(8, user.getEmail());
                preparedStatement1.setBoolean(9, user.isEnabled());
                preparedStatement1.setString(10, user.getId());
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            user = null;
        }
        return user;
    }

    @Override
    public int count() {
        try {
            return dataSource.getConnection().prepareStatement("SELECT COUNT(user_id) FROM users").executeQuery().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
