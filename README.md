# Antiheimer

## JWT
signup/login은 auth 키 사용, 그 외 모든 api는 JWT 사용

**Header**

- auth 키: auth
- JWT token: Authorization

### possible error
auth(401)
- 유효하지 않는 JWT 서명
- 만료된 JWT 토큰
- 지원하지 않는 JWT 토큰
- 잘못된 JWT 토큰

## 기타
path에 variable이 있는 경우 AES 암호화 필요

ex) /login/{uuid}

## Signup
#### /signup: POST

**Header**
-String auth

**Body**
- String id; 8자 이상, 알파벳/숫자만, 공백/NULL x
- String pw; 알파벳/숫자만, 공백/NULL x
- String name; 공백/NULL x

### possible error
auth(400): 권한 없음

invalid(401)
- InvalidIdException(): 유효하지 않은 아이디
- InvalidPwException(): 유효하지 않은 비밀번호
- InvalidNameException(): 유효하지 않은 이름

duplicate(402)
- DuplicateIdException(): 중복된 아이디, 회원

null(403)
- NullNameException(): 입력되지 않은 이름
- NullPwException():  입력되지 않은 비밀번호

## Login
#### /login: POST

**Header**
- String auth

**Body**
- String request(AES)
  - String id
  - String pw

### possible error
auth(401): 권한 없음

null(405)
- NullIdException: 입력되지 않은 아아디
- NullPwException: 입력되지 않은 비밀번호

exist(408)
- NotExistException: 존재하지 않는 아이디

incorrect(409)
- IncorrectPwException: 일치하지 않는 비밀번호


## Logout
#### /logout/{uuid}: GET
uuid AES 암호화

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

null(405)
- NullUuidException: 입력되지 않은 uuid

invalid(406)
- InvalidUuidException: 유효하지 않은 uuid

exist(408)
- NotExistException: 존재하지 않는 회원