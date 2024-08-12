# Antiheimer

## Signup
#### /signup: POST



## Login(AES)
#### /login: POST
request AES 암호화

**Header**
- String auth

**Body**
- String request
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


## Logout(AES)
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