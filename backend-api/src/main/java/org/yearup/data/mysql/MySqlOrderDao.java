package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource){ super(dataSource);}

    @Override
    public int createOrder(int userId) {
        String sql = "INSERT INTO orders(user_id, 'date') " +
                " VALUES (?,?)";

        try (Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()){
                return keys.getInt(1);
            }

            throw new RuntimeException("Could not create order");

            }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
