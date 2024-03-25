## 회원가입

- **Method**: POST
- **Endpoint**: /members/signup
- **Request Body**:
  ```json
  {
    "email": "sonny12@gmail.com",
    "password": "sonny12"
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "회원 가입에 성공했습니다."
  }
  ```

## 로그인

- **Method**: POST
- **Endpoint**: /members/login
- **Request Body**:
  ```json
  {
    "email": "sonny12@gmail.com",
    "password": "sonny12"
  }
  ```
- **Headers**:
  ```
  Authorization: 토큰
  ```
- **Response Body**:
  ```json
  {
    "message": "로그인을 성공했습니다."
  }
  ```

## 로그아웃

- **Method**: POST
- **Endpoint**: /members/logout
- **Headers**:
  ```
  Authorization: 토큰
  ```
- **Response Body**:
  ```json
  {
    "message": "로그아웃을 성공했습니다."
  }
  ```

## 사용자 정보 수정 (비밀번호 수정)

- **Method**: PUT
- **Endpoint**: /members
- **Headers**:
  ```
  Authorization: 토큰
  ```
- **Request Body**:
  ```json
  {
    "password": "현재 비밀번호",
    "changePassword": "바꿀 비밀번호",
    "rechangePassword": "바꿀 비밀번호"
  }
  ```
- **Response Body**:
  ```json
  {
    "message": "회원을 성공적으로 수정 하였습니다."
  }
  ```

## 사용자 정보 삭제

- **Method**: DELETE
- **Endpoint**: /members
- **Headers**:
  ```
  Authorization: 토큰
  ```
- **Response Body**:
  ```json
  {
    "message": "회원을 성공적으로 삭제 하였습니다."
  }
  ```
```
