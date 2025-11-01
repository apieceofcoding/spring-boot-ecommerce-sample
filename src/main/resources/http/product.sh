curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "title": "MacBook Air M3",
    "listPrice": 1890000,
    "discountPrice": 1690000,
    "thumbnailUrl": "https://example.com/macbook-air.jpg"
  }'
