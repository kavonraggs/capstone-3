package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;
import java.util.List;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/cart")
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @GetMapping
    // each method in this controller requires a Principal object as a parameter
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            List<ShoppingCartItem> items = shoppingCartDao.getByUserId(userId);
            ShoppingCart cart = new ShoppingCart();
            for (ShoppingCartItem item: items){
                cart.add(item);
            }
            return cart;
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("/products/{productId}")
    public void addToCart(@PathVariable int productId,
                          Principal principal){
        int userId = getUserById(principal);
        shoppingCartDao.addItem(userId, productId);
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("/products/{productId}")
    public void updateProductInCart(@PathVariable int productId,
                                    @RequestBody ShoppingCartItem item,
                                    Principal principal){
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        int userId = user.getId();
        shoppingCartDao.updateQuantity(userId, shoppingCartDao.getItem(userId, productId), item.getQuantity() + 1);
    }




    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping("/cart")
    public void deleteProductFromCart(Principal principal){
        int userId = getUserById(principal);
        shoppingCartDao.deleteCart(userId);
    }

    //ADD ON
    // add a DELETE method to clear a certain products from the current users cart
    // https://localhost:8080/cart/products/15
    @DeleteMapping("/products/{productId}")
    public void deleteProductFromCart(@PathVariable int productId, Principal principal){
        int userId = getUserById(principal);
        shoppingCartDao.deleteItem(userId, productId);
    }

    public int getUserById(Principal principal){
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        return user.getId();
    }

}
