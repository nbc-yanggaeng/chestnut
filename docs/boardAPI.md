## 보드 리스트 조회

- **Method**: GET
- **Endpoint**: /boards
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Response Body**:
  ```json
  {
    "message": "보드 리스트를 조회했습니다.",
    "data": [
      {
        "id": 1,
        "title": "보드 제목1",
        "description": "보드 설명1",
        "backgroundColor": "blue"
      },
      {
        "id": 3,
        "title": "게시글 제목2",
        "description": "보드 설명2",
        "backgroundColor": "red"
      },
      {
        "id": 4,
        "title": "게시글 제목3",
        "description": "보드 설명3",
        "backgroundColor": ""
      }
    ]
  }
  ```

## 보드 조회

- **Method**: GET
- **Endpoint**: /boards/{boardId}
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Response Body**:
  ```json
  {
    "boardId": 1,
    "boardName": "My Project Board",
    "columns": [
      {
        "columnId": 1,
        "columnName": "To Do",
        "sequence": 1,
        "cards": [
          {
            "cardId": 1,
            "cardTitle": "Task 1",
            "cardDescription": "Task 1 description here"
          }
        ]
      }
    ]
  }
  ```

## 보드 생성

- **Method**: POST
- **Endpoint**: /boards
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Response Body**: 
  ```json
  { "message": "보드를 생성했습니다." }
  ```

## 보드 수정

- **Method**: PUT
- **Endpoint**: /boards/{boardId}
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Response Body**: 
  ```json
  { "message": "보드를 수정했습니다." }
  ```

## 보드 삭제

- **Method**: DELETE
- **Endpoint**: /boards/{boardId}
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Response Body**: 
  ```json
  { "message": "보드를 삭제했습니다." }
  ```

## 보드 초대

- **Method**: POST
- **Endpoint**: /boards/{boardId}/invite/{userId}
- **Headers**: 
  ```
  Authorization: 토큰
  ```
- **Response Body**:
  ```json
  { "message": "유저를 초대했습니다." }
  ```
