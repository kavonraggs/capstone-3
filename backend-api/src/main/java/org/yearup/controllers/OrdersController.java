package org.yearup.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping
    public void checkout(Principal principal {
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        int userId = user.getId();

        List<ShoppingCartItem> cartItems = shoppingCartDao.getByUserId(userId);
    }
}
