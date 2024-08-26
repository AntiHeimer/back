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


# Member

## signup
#### /signup: POST

**Header**
-String auth

**Body**
- String request(AES)
    - String id; 8자 이상, 알파벳/숫자만, 공백/NULL x
    - String pw; 알파벳/숫자만, 공백/NULL x
    - String name; 공백/NULL x
    - String gender; NULL x, female/male만
    - LocalDate Birth; NULL x

### possible error
auth(401): 권한 없음

null(405)
- NullIdException(): 입력되지 않은 아이디
- NullNameException(): 입력되지 않은 이름
- NullPwException():  입력되지 않은 비밀번호
- NullGenderException(): 입력되지 않은 성별
- NullBirthException(): 입력되지 않은 생일

invalid(406)
- InvalidIdException(): 유효하지 않은 아이디
- InvalidPwException(): 유효하지 않은 비밀번호
- InvalidNameException(): 유효하지 않은 이름
- InvalidGenderException(): 유효하지 않은 성별

duplicate(407)
- DuplicateIdException(): 중복된 아이디, 회원


## login
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


## logout
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


# HealthData

## saveActive
#### /save/active: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- LocalDate date(dd-MM-yyyy)
- List activeData
    - int activeEnergyBurned
    - int activeEnergyBurnedGoal
    - int appleExerciseTime
    - int appleExerciseTimeGoal
    - int appleStandHours
    - int appleStandHoursGoal

### possible error
auth(401): 권한 없음(토큰)

duplicate(407)
- DuplicateHealthDataException: 중복된 활동 데이터

exist(408)
- NotExistException: 존재하지 않는 회원


## saveMove
#### /save/move: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- LocalDate date(dd-MM-yyyy)
- List moveData
    - LocalDateTime startDateTime
    - LocalDateTime endDateTime
    - int value

### possible error
auth(401): 권한 없음(토큰)

duplicate(407)
- DuplicateHealthDataException: 중복된 움직인 거리 데이터

exist(408)
- NotExistException: 존재하지 않는 회원


## saveWalk
#### /save/walk: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- LocalDate date(dd-MM-yyyy)
- List walkData
    - LocalDateTime startDateTime
    - LocalDateTime endDateTime
    - int value

### possible error
auth(401): 권한 없음(토큰)

duplicate(407)
- DuplicateHealthDataException: 중복된 걸음수 데이터

exist(408)
- NotExistException: 존재하지 않는 회원


## saveWeight
#### /save/weight: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- double weight

### possible error
auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


## recentData
#### /recent?uuid={uuid}&data={data}: GET - url 인코딩

**Header**
- String Authorization

**Param**
- String uuid: uuid AES 암호화
- String data

### possible error
auth(401): 권한 없음(토큰)

invalid(406)
- InvalidDataTypeException: 유효하지 않은 데이터 타입

exist(408)
- NotExistException: 존재하지 않는 회원

decoding(410)
- UnsupportedEncodingException: 디코딩 오류