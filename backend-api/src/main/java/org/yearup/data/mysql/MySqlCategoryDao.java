package org.yearup.data.mysql;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {

        // get all categories
        List<Category> allCategories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try(Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()){
                Category category = mapRow(results);
                allCategories.add(category);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return allCategories;
    }

    @Override
    public Category getById(int categoryId)
    {
        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,categoryId);
            ResultSet results = preparedStatement.executeQuery();

            while(results.next()) {
                return mapRow(results);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Category create(Category category)
    {
        // create a new category

        String sql = "INSERT INTO category(category_id, name, description)" + " VALUES (?, ?, ?)";

        try (Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, category.getCategoryId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(3, category.getDescription());

            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()){
                    int orderId = generatedKeys.getInt(1);
                    return getById(orderId);
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
        String sql = "UPDATE categories" +
                "SET  name = ? " +
                " , description = ? " +
                "WHERE category_id = ?";

        try (Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category

        String sql = "DELETE FROM categories " +
                "WHERE category_id = ?";

        try(Connection conn = getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
