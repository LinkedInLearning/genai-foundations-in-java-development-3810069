## BookController Endpoints

### 1. Get All Books

#### Request
```bash
curl -X GET "http://localhost:8080/books" -H "accept: application/json"
```

#### Response
```json
[
    {
        "id": 1,
        "title": "Clean Code",
        "author": "Robert C. Martin",
        "isbn": "978-0132350884",
        "reviews": []
    },
    {
        "id": 2,
        "title": "Effective Java",
        "author": "Joshua Bloch",
        "isbn": "978-0134685991",
        "reviews": []
    }
]
```

### 2. Create a Book

#### Request
```bash
curl -X POST "http://localhost:8080/books" -H "accept: application/json" -H "Content-Type: application/json" -d '{
  "title": "Refactoring",
  "author": "Martin Fowler",
  "isbn": "978-0201485677"
}'
```

#### Response
```json
{
    "id": 3,
    "title": "Refactoring",
    "author": "Martin Fowler",
    "isbn": "978-0201485677",
    "reviews": []
}
```

### 3. Get Book by ID

#### Request
```bash
curl -X GET "http://localhost:8080/books/1" -H "accept: application/json"
```

#### Response
```json
{
    "id": 1,
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "isbn": "978-0132350884",
    "reviews": []
}
```

### 4. Get Top Popular Books

#### Request
```bash
curl -X GET "http://localhost:8080/books/popular?count=2" -H "accept: application/json"
```

#### Response
```json
[
    {
        "id": 1,
        "title": "Clean Code",
        "author": "Robert C. Martin",
        "isbn": "978-0132350884",
        "reviews": []
    },
    {
        "id": 2,
        "title": "Effective Java",
        "author": "Joshua Bloch",
        "isbn": "978-0134685991",
        "reviews": []
    }
]
```

## ReviewController Endpoints

### 1. Get All Reviews for a Book

#### Request
```bash
curl -X GET "http://localhost:8080/books/1/reviews" -H "accept: application/json"
```

#### Response
```json
[
    {
        "id": 1,
        "reviewerName": "Alice",
        "content": "Great book on clean coding practices.",
        "rating": 5,
        "book": {
            "id": 1,
            "title": "Clean Code",
            "author": "Robert C. Martin",
            "isbn": "978-0132350884"
        }
    },
    {
        "id": 2,
        "reviewerName": "Bob",
        "content": "A must-read for Java developers.",
        "rating": 4,
        "book": {
            "id": 1,
            "title": "Clean Code",
            "author": "Robert C. Martin",
            "isbn": "978-0132350884"
        }
    }
]
```

### 2. Add a Review for a Book

#### Request
```bash
curl -X POST "http://localhost:8080/books/1/reviews" -H "accept: application/json" -H "Content-Type: application/json" -d '{
  "reviewerName": "Charlie",
  "content": "Excellent book.",
  "rating": 5
}'
```

#### Response
```json
{
    "id": 3,
    "reviewerName": "Charlie",
    "content": "Excellent book.",
    "rating": 5,
    "book": {
        "id": 1,
        "title": "Clean Code",
        "author": "Robert C. Martin",
        "isbn": "978-0132350884"
    }
}
```

### 3. Get Average Rating for a Book

#### Request
```bash
curl -X GET "http://localhost:8080/books/1/reviews/average-rating" -H "accept: application/json"
```

#### Response
```json
4.5
```

## Global Exception Handling Responses

### Resource Not Found

#### Request
```bash
curl -X GET "http://localhost:8080/books/999" -H "accept: application/json"
```

#### Response
```json
{
    "timestamp": "2023-06-29T12:00:00.000+00:00",
    "status": 404,
    "error": "Not Found",
    "message": "Book not found with ID: 999",
    "path": "/books/999"
}
```

### Internal Server Error

#### Request
```bash
curl -X GET "http://localhost:8080/books/error" -H "accept: application/json"
```

#### Response
```json
{
    "timestamp": "2023-06-29T12:00:00.000+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "message": "An unexpected error occurred",
    "path": "/books/error"
}
```

This documentation provides detailed examples of how to use the endpoints with CURL commands, along with sample responses for each request. This will help consumers of your API understand how to interact with it and what to expect in return.