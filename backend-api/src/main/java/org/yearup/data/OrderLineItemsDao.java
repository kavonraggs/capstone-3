package org.yearup.data;

import org.yearup.models.ShoppingCartItem;

public interface OrderLineItemsDao {
    void add(int orderId, ShoppingCartItem item);
}
