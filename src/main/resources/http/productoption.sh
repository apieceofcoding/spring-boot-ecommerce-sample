# Create Product Option
curl -X POST http://localhost:8080/api/v1/products/1/options \
  -H "Content-Type: application/json" \
  -d '{
    "name": "256GB",
    "priceDiff": 0,
    "stock": 100
  }'

# Create Product Option (variant)
curl -X POST http://localhost:8080/api/v1/products/1/options \
  -H "Content-Type: application/json" \
  -d '{
    "name": "512GB",
    "priceDiff": 200000,
    "stock": 50
  }'

# Get Product Options by Product ID
curl -X GET http://localhost:8080/api/v1/products/1/options

# Get Product Option
curl -X GET http://localhost:8080/api/v1/product-options/1

# Update Product Option
curl -X PUT http://localhost:8080/api/v1/product-options/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "256GB SSD",
    "priceDiff": 0,
    "stock": 80
  }'

# Delete Product Option
curl -X DELETE http://localhost:8080/api/v1/product-options/1
