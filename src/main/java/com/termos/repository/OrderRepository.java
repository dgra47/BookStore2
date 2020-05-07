package com.termos.repository;

import com.termos.dto.AbstractDTO;
import com.termos.dto.OrderDTO;
import com.termos.utility.TimeUtils;
import com.termos.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OrderRepository implements AbstractRepository<Order>{
    private DataSource dataSource;

    @Autowired
    public OrderRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Order getById(String id) {
        Order order = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from orders where order_id=?";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = map(resultSet);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Order persist(Order order) {
        boolean result = true;
        Connection connection;
        try {
            connection = dataSource.getConnection();

            String sql = "INSERT INTO orders(order_id, book_id, user_id, order_date, quantity, price, status, invoice) VALUES(?,?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, order.getBookId());
            preparedStatement.setString(3, order.getUserId());
            preparedStatement.setTimestamp(4, TimeUtils.NowTimeStamp());
            preparedStatement.setInt(5, order.getQuantity());
            preparedStatement.setDouble(6, order.getPrice());
            preparedStatement.setString(7, order.getStatus());
            preparedStatement.setString(8, order.getInvoice());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return order;
    }

    @Override
    public String remove(String id) {
        boolean result = true;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "DELETE from orders where order_id=?";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return id;
    }

    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            ResultSet rs = connection.prepareStatement("select * from orders").executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Order map(ResultSet rst) throws SQLException {
        return new Order(rst.getString("order_id"),
                rst.getString("book_id"),
                rst.getString("user_id"),
                rst.getTimestamp("order_date"),
                rst.getInt("quantity"),
                rst.getFloat("price"),
                rst.getString("status"),
                rst.getString("invoice"));
    }

    @Override
    public Order dtoToModel(AbstractDTO dto) {
        OrderDTO orderDTO = (OrderDTO)dto;
        return new Order(orderDTO.getOrderId(),
                orderDTO.getBookId(),
                orderDTO.getUserId(),
                orderDTO.getOrderDate(),
                orderDTO.getQuantity(),
                orderDTO.getPrice(),
                orderDTO.getStatus(),
                orderDTO.getInvoice()
        );
    }

    @Override
    public OrderDTO modelToDto(Order order){
        var orderDto = new OrderDTO();
        if(order == null){
            return null;
        }
        orderDto.setBookId(order.getBookId());
        orderDto.setInvoice(order.getInvoice());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setPrice(order.getPrice());
        orderDto.setQuantity(order.getQuantity());

        return orderDto;
    }

    @Override
    public int count() {
        try {
            return dataSource.getConnection().prepareStatement("SELECT COUNT(order_id) FROM orders").executeQuery().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}