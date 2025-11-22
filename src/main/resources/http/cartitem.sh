# Create Cart Item (without option)
curl -X POST http://localhost:8080/api/v1/cart-items \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'

# Create Cart Item (with option)
curl -X POST http://localhost:8080/api/v1/cart-items \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "productOptionId": 1,
    "quantity": 1
  }'

# Get Cart Item
curl -X GET http://localhost:8080/api/v1/cart-items/1

# Get All Cart Items
curl -X GET http://localhost:8080/api/v1/cart-items

# Update Cart Item
curl -X PUT http://localhost:8080/api/v1/cart-items/1 \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "productOptionId": 1,
    "quantity": 3
  }'

# Delete Cart Item
curl -X DELETE http://localhost:8080/api/v1/cart-items/1
