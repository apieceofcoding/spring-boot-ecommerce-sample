# Create Category (전자기기)
curl -X POST http://localhost:8080/api/v1/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "전자기기"
  }'

# Get Category
curl -X GET http://localhost:8080/api/v1/categories/1

# Get All Categories
curl -X GET http://localhost:8080/api/v1/categories

# Update Category
curl -X PUT http://localhost:8080/api/v1/categories/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "전자제품"
  }'

# Add Product to Category (카테고리 1번에 상품 1번 매핑)
curl -X POST http://localhost:8080/api/v1/categories/1/products/1

# Get Products in Category
curl -X GET http://localhost:8080/api/v1/categories/1/products

# Get Categories by Product
curl -X GET http://localhost:8080/api/v1/products/1/categories

# Remove Product from Category
curl -X DELETE http://localhost:8080/api/v1/categories/1/products/1

# Delete Category
curl -X DELETE http://localhost:8080/api/v1/categories/1
