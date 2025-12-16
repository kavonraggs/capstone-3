package org.yearup.data;

import org.yearup.models.ShoppingCartItem;

public interface OrderLineItemDao {
    void add(int orderId, ShoppingCartItem item);
}
