# Capstone 3 

## This is a full stack ecommerce web application build using Java Spring Boot and JavaScript


## Author
Kavon Raggs

## Features
- User Auth with JWT
- Product browsing and filtering
- Secure endpoints
- Profile management
- MySql Database integration
- RESTful API

# Tech Used

### Backend
- Java
- Spring Boot / Security
- JWT Authentication
- JDBC
- MySql

### Frontend
- HTML
- CSS
- JS
- Axios

## API Endpoints
### Cart
- GET /cart
- POST /cart/products/{productId}
- DELETE /cart

### Orders
- POST /orders

### Profile 
- GET /profile
- PUT /profile


## Database
- Users
- Products
- Shopping Cart
- Orders
- Order Line Items
- Profiles

# Interesting piece of code
```public class MySqlOrderLineItemDao extends MySqlDaoBase implements OrderLineItemDao```
- This code is interesting to me because in each SQL DAO we have a base interface that set what each DAO needs to do and another one to define how it does it
- Shows difference between implements and extends 
- extends: is-a / is a type of
- implements: follows a contract / structure

``` @Override
    public void addOrIncrementItem(int userId, int productId) {
        ShoppingCartItem item = getItem(userId, productId);

        if (item == null){
            addItem(userId,productId);
        }
        else {
            incrementQuantity(userId, item);
        }
    }```

- if item is not in cart, add it. If it is, increase quantity




