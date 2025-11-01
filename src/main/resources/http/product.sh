# Create Product
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "title": "MacBook Air M3",
    "listPrice": 1890000,
    "discountPrice": 1690000
  }'

# Get Product
curl -X GET http://localhost:8080/api/v1/products/1

# Update Product
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "MacBook Air M3",
    "listPrice": 1890000,
    "discountPrice": 1590000
  }'

# Upload Thumbnail Image
curl -X POST http://localhost:8080/api/v1/products/1/thumbnail \
  -F "file=@image/macbook_air_m3.jpg"

# Delete Thumbnail Image
curl -X DELETE http://localhost:8080/api/v1/products/1/thumbnail

# Delete Product
curl -X DELETE http://localhost:8080/api/v1/products/1
