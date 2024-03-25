## 컬럼 조회

- **Method**: GET
- **Endpoint**: /boards/{boardId}/columns
- **Response Body**:
  ```json
  {
    "message": "컬럼 조회에 성공했습니다.",
    "data": {
      "boardId": 1,
      "responseDtoList": [
        {
          "id": 2,
          "title": "columns생성",
          "sequence": 1
        },
        {
          "id": 1,
          "title": "columnsTitle수정",
          "sequence": 2
        },
        {
          "id": 3,
          "title": "columns생성",
          "sequence": 3
        }
      ]
    }
  }
  ```

## 컬럼 생성

- **Method**: POST
- **Endpoint**: /boards/{boardId}/columns
- **Headers**:
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "title": "columns생성"
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "컬럼을 생성했습니다.",
    "data": {
      "id": 9,
      "title": "columns생성",
      "sequence": 9
    }
  }
  ```

## 컬럼 수정

- **Method**: PUT
- **Endpoint**: /columns/{columnId}
- **Headers**:
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "title": "columnsTitle수정"
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "컬럼을 수정했습니다.",
    "data": [
      {
        "id": 1,
        "title": "columnsTitle수정",
        "sequence": 2
      }
    ]
  }
  ```

## 컬럼 삭제

- **Method**: DELETE
- **Endpoint**: /columns/{columnId}
- **Headers**:
  ```
  Authorization: 토큰
  ```

## 컬럼 순서 이동

- **Method**: PUT
- **Endpoint**: /columns/{columnId}/sequences
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "sequence": 1
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "컬럼 순서를 수정했습니다.",
    "data": {
      "id": 2,
      "title": "columns생성",
      "sequence": 1
    }
  }
  ```
