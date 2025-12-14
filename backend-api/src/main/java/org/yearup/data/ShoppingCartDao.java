package org.yearup.data;


import org.yearup.models.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartDao
{
    List<ShoppingCartItem> getByUserId(int userId);
    // add additional method signatures here
    ShoppingCartItem getItem(int userId, int productId);
    void addItem(int userId, int productId);
    void updateQuantity(int userId, ShoppingCartItem shoppingCartItem);
    void deleteItem(int userId, int productId);
    void deleteCart(int userId);
}
