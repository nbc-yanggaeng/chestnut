## 댓글 조회

- **Method**: GET
- **Endpoint**: /cards/{cardId}/comments
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Response Body**:
  ```json
  {
    "message": "string",
    "data": [
      {
        "memberId": long,
        "content": "string"
      }
    ]
  }
  ```

## 댓글 달기

- **Method**: POST
- **Endpoint**: /cards/{cardId}/comments
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "content": "string"
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "string",
    "data": [
      {
        "memberId": long,
        "content": "string"
      }
    ]
  }
  ```

## 댓글 수정

- **Method**: PUT
- **Endpoint**: /comments/{commentId}
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "content": "string"
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "string",
    "data": [
      {
        "memberId": long,
        "content": "string"
      }
    ]
  }
  ```

## 댓글 삭제

- **Method**: DELETE
- **Endpoint**: /comments/{commentId}
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Response Body**:
  ```json
  {
    "message": "string",
    "data": [
      {
        "memberId": long,
        "content": "string"
      }
    ]
  }
  ```
