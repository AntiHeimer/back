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
- String reqDto(AES)
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
- String reqDto(AES)
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


## memberInfo
#### /info?memberUuid={memberUuid}: GET
memberUuid AES 암호화 + URL 인코딩

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)


[//]: # (## saveDeviceToken)

[//]: # (#### /save/device-token: POST)

[//]: # ()
[//]: # (**Header**)

[//]: # (- String Authorization)

[//]: # ()
[//]: # (**Body**)

[//]: # (- String memberUuid)

[//]: # (- String deviceToken)

[//]: # ()
[//]: # (### possible error)

[//]: # (auth&#40;401&#41;: 권한 없음&#40;토큰&#41;)

[//]: # ()
[//]: # (exist&#40;408&#41;)

[//]: # (- NotExistException: 존재하지 않는 회원)


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


## saveSleep
#### /save/sleep: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- LocalDate date(dd-mm-yyyy)
- List sleepData
    - LocalDateTime startDateTime
    - LocalDateTime endDateTime
    - String id
    - String sourceId
    - String sourceName
    - String value

### possible error
auth(401): 권한 없음(토큰)

duplicate(407)
- DuplicateHealthDataException: 중복된 수면 데이터

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


## findHealthData
#### /find/health-data

**Header**
- String auth

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


## findActive
#### /find/active

**Header**
- String auth

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


## findMove
#### /find/move

**Header**
- String auth

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


## findWalk
#### /find/walk

**Header**
- String auth

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


## findSleep
#### /find/sleep

**Header**
- String auth

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


# Location

## saveLocation
#### /save/location: POST

**Header**
- String auth

**Body**
- String reqDto: AES 암호화
  - String memberUuid;
  - LocalDateTime date;
  - LocationDto location;
    - double latitude;
    - double longitude;

### possible error
auth(401): 권한 없음(토큰)

duplicate(407)
- DuplicateLocationException: 중복된 위치 정보

exist(408)
- NotExistException: 존재하지 않는 회원


## recentLocation
#### /recent/location?memberUuid={memberUuid}: GET - url 인코딩

**Header**
- String Authorization

**Param**
- String memberUuid: AES 암호화

### possible error
auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원
- NotExistLocationException: 존재하지 않는 위치 정보


# Diagnosis
## returnDiagnosisSheet
#### /diagnosisSheet?num={num}: GET

**Header**
-String Authorization

**Param**
-int num

### possible error
auth(401): 권한 없음(토큰)

Incorrect(409)
- IncorrectNumException: 올바르지 않은 번호


## RandomWords
### /diagnosisSheet/word: GET

**Header**
-String Authorization

### possible error
auth(401): 권한 없음(토큰)



## StartDiagnosis
### /diagnosis/start: POST

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원



## DiagnosisScore
### /diagnosis/score: POST

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

Incorrect(409)
- IncorrectNumException: 올바르지 않은 번호

Invalid(406)
- InvalidScoreException: 유효하지 않은 점수


## DiagnosisAnswer
### /diagnosis/answer: POST

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

Invalid(406)
- InvalidScoreException: 유효하지 않은 점수



## diagnosisResult
### /diagnosis/result: GET

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원



# Relation
## requestRelation
#### /request-relation: POST

**Header**
- String Authorization

**Body**
- String fromMemberUuid;
- String toMemberId;
- String requestType; guardian/ward

## possible error
auth(401): 권한 없음(토큰)

duplicate(407)
- Duplicate: 이미 존재하는 관계

exist(408)
- NotExistException: 존재하지 않는 회원


## saveGuardian
#### /save-relation/guardian: POST

**Header**
- String Authorization

**Body**
- String notificationUuid
- String guardianUuid
- String wardUuid

### possible error

auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


## saveWard
#### /save-relation/ward: POST

**Header**
- String Authorization

**Body**
- String notificationUuid
- String guardianUuid
- String wardId

### possible error

auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


## infoGuardian
#### /info-relation/guardian?memberUuid={memberUuid}: GET
memberUuid AES 암호화 + 인코딩

**Header**
- String Authorization

### possible error

auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


## infoWard
#### /info-relation/ward?memberUuid={memberUuid}: GET
memberUuid AES 암호화 + 인코딩

**Header**
- String Authorization

### possible error

auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


# Notification
## findNotification
#### /find-notification?memberUuid={memberUuid}: GET
memberUuid AES 암호화 + 인코딩

**Header**
- String Authorization

### possible error

auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 회원


## deleteNotification
#### /delete-notification?notificationUuid={notificationUuid}: DELETE
notificationUuid AES 암호화 + 인코딩

**Header**
- String Authorization

### possible error

auth(401): 권한 없음(토큰)

exist(408)
- NotExistException: 존재하지 않는 알림
