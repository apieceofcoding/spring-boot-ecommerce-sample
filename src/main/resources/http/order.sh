# Create Order (from multiple cart items)
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "cartItemIds": [1, 2]
  }'

# Create Order (from single cart item)
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "cartItemIds": [1]
  }'

# Get Order
curl -X GET http://localhost:8080/api/v1/orders/1

# Mark Order as Paid
curl -X PATCH http://localhost:8080/api/v1/orders/1/paid

# Mark Order as Complete
curl -X PATCH http://localhost:8080/api/v1/orders/1/complete
