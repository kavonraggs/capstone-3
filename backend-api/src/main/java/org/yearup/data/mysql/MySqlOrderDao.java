package org.yearup.data.mysql;

import org.yearup.data.OrderDao;
import org.yearup.data.UserDao;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource){ super(dataSource);}

    @Override
    public int createOrder(int userId) {
        String sql = "INSERT INTO orders(user_id, date) " +
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

            }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }
}
