package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderLineItemDao;
import org.yearup.models.ShoppingCartItem;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class MySqlOrderLineItemDao extends MySqlDaoBase implements OrderLineItemDao {

    public MySqlOrderLineItemDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void add(int orderId, ShoppingCartItem item) {
        String sql = "INSERT INTO order_line_item( order_id, product_id, sales_price, quantity, discount)" +
                " VALUES (?, ?, ?, ?, ?);";

        try(Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setBigDecimal(3, item.getProduct().getPrice());
            preparedStatement.setInt(4, item.getQuantity());
            preparedStatement.setBigDecimal(5,item.getDiscountPercent());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
