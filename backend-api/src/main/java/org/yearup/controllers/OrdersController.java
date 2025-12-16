package org.yearup.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.OrderDao;
import org.yearup.data.OrderLineItemDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class OrdersController {

    private UserDao userDao;
    private ShoppingCartDao shoppingCartDao;
    private OrderDao orderDao;
    private OrderLineItemDao orderLineItemDao;

    public OrdersController(UserDao userDao, ShoppingCartDao shoppingCartDao, OrderDao orderDao, OrderLineItemDao orderLineItemDao) {
        this.userDao = userDao;
        this.shoppingCartDao = shoppingCartDao;
        this.orderDao = orderDao;
        this.orderLineItemDao = orderLineItemDao;
    }

    @PostMapping
    public void checkout(Principal principal) {
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        int userId = user.getId();

        List<ShoppingCartItem> cartItems = shoppingCartDao.getByUserId(userId);

        if (!cartItems.isEmpty()) {
            int orderId = orderDao.createOrder(userId);

            for (ShoppingCartItem item : cartItems) {
                orderLineItemDao.add(orderId, item);
            }

            shoppingCartDao.deleteCart(userId);
        }
    }

}
