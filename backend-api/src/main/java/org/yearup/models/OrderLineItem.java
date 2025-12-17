package org.yearup.models;

import org.yearup.data.ShoppingCartDao;

import java.math.BigDecimal;

public class OrderLineItem {
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discount;


    ShoppingCartDao shoppingCartDao;

    public OrderLineItem(int orderId, int productId, int quantity, BigDecimal price, BigDecimal discount) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
