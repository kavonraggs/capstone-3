package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCartItem;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    ProductDao productDao;

    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;
    }

    @Override
    public List<ShoppingCartItem> getByUserId(int userId) {
        List<ShoppingCartItem> itemList = new ArrayList<>();
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ?;";

        try(Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            ResultSet results = preparedStatement.executeQuery();

           while (results.next()){
                ShoppingCartItem item = mapItemRow(results);
                itemList.add(item);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return itemList;
    }

    @Override
    public ShoppingCartItem getItem(int userId, int productId) {
        String sql = "SELECT * FROM shopping_cart" +
                " WHERE user_id = ? AND product_id = ?";

        try (Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()){
                int quantity = results.getInt("quantity");
                ShoppingCartItem item = new ShoppingCartItem();
                item.setQuantity(quantity);
                Product product = productDao.getById(productId);
                item.setProduct(product);
                return item;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void addItem(int userId, int productId) {
        String sql = "INSERT INTO shopping_cart(user_id, product_id, quantity) " + "VALUES (?, ?, ?);";

        try(Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateQuantity(int userId, ShoppingCartItem shoppingCartItem) {
        String sql = "UPDATE shopping_cart" +
                " SET quantity = ?" +
                " WHERE user_id = ? AND product_id = ? ";

        try(Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, shoppingCartItem.getQuantity());
            preparedStatement.setInt(3, shoppingCartItem.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItem(int userId, int productId) {
        String sql = "DELETE FROM shopping_cart " +
                "WHERE user_id = ? AND product_id = ?";

        try (Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCart(int userId) {
        String sql = "DELETE FROM shopping_cart " +
                " WHERE user_id = ?";

        try(Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    protected ShoppingCartItem mapItemRow(ResultSet row) throws SQLException
    {
        int quantity = row.getInt("quantity");

        ShoppingCartItem item = new ShoppingCartItem();
        Product product = productDao.getById(row.getInt("product_id"));
        item.setQuantity(quantity);
        item.setProduct(product);

        return item;
    }
}
