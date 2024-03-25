## 카드 조회

- **Method**: GET
- **Endpoint**: /cards/{cardId}
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
        "cardId": long,
        "columnId": long,
        "title": "string",
        "description": "string",
        "background_color": "string",
        "deadline": "date",
        "start_at": "date",
        "workerResponse": []
      }
    ]
  }
  ```

## 카드 전체 조회

- **Method**: GET
- **Endpoint**: /column/{columnId}/cards
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
        "cardId": long,
        "columnId": long,
        "title": "string",
        "description": "string",
        "background_color": "string",
        "deadline": "date",
        "start_at": "date",
        "workerResponse": []
      }
    ]
  }
  ```

## 카드 생성

- **Method**: POST
- **Endpoint**: /boards/{boardId}/columns/{columnId}/cards
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "title": "string",
    "description": "string",
    "background_color": "string",
    "deadline": "date",
    "start_at": "date"
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "string",
    "data": [
      {
        "cardId": long,
        "columnId": long,
        "title": "string",
        "description": "string",
        "background_color": "string",
        "deadline": "date",
        "start_at": "date",
        "workerResponse": []
      }
    ]
  }
  ```

## 카드 수정

- **Method**: PUT
- **Endpoint**: /cards/{cardId}
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "title": "string",
    "description": "string",
    "background_color": "string",
    "deadline": "date",
    "start_at": "date"
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "string",
    "data": [
      {
        "cardId": long,
        "columnId": long,
        "title": "string",
        "description": "string",
        "background_color": "string",
        "deadline": "date",
        "start_at": "date",
        "workerResponse": []
      }
    ]
  }
  ```

## 카드 삭제

- **Method**: DELETE
- **Endpoint**: /cards/{cardId}
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Response Body**:
  ```json
  {
    "message": "string"
  }
  ```

## 작업자 추가/삭제

- **Method**: PUT
- **Endpoint**: /cards/{cardId}/workers
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "addRequest": {
      "workerList": []
    },
    "removeRequest": {
      "workerList": []
    }
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "string",
    "data": [
      {
        "cardId": long,
        "columnId": long,
        "title": "string",
        "description": "string",
        "background_color": "string",
        "deadline": "date",
        "start_at": "date",
        "workerResponse": []
      }
    ]
  }
  ```

## 카드 이동

- **Method**: PUT
- **Endpoint**: /cards/{cardId}/move
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "moveTo": long
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "string",
    "data": [
      {
        "cardId": long,
        "columnId": long,
        "title": "string",
        "description": "string",
        "background_color": "string",
        "deadline": "date",
        "start_at": "date",
        "workerResponse": []
      }
    ]
  }
  ```
