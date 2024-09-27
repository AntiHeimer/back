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

ex) /login/{memberUuid}




# Member
## /member

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

null(41x)
- NullIdException(411): 입력되지 않은 아이디
- NullPwException(412):  입력되지 않은 비밀번호
- NullNameException(413): 입력되지 않은 이름
- NullGenderException(414): 입력되지 않은 성별
- NullBirthException(415): 입력되지 않은 생일

invalid(42x)
- InvalidIdException(421): 유효하지 않은 아이디
- InvalidPwException(422): 유효하지 않은 비밀번호
- InvalidNameException(423): 유효하지 않은 이름
- InvalidGenderException(424): 유효하지 않은 성별

duplicate(45x)
- DuplicateIdException(450): 중복된 아이디, 회원


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

null(41x)
- NullIdException(411): 입력되지 않은 아아디
- NullPwException(412): 입력되지 않은 비밀번호

exist(43x)
- NotExistIDException(430): 존재하지 않는 아이디

incorrect(44x)
- IncorrectPwException(440): 일치하지 않는 비밀번호


## logout
#### /logout/{memberUuid}: GET
memberUuid AES 암호화

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

null(41x)
- NullIdException(410): 입력되지 않은 uuid

invalid(42x)
- InvalidUuidException(420): 유효하지 않은 uuid

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## memberInfo
#### /info?memberUuid={memberUuid}: GET- URL 인코딩

**Header**
- String Authorization

**Param**
- String memberUuid: AES 암호화

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

[//]: # (- NotExistMemberException(431): 존재하지 않는 회원)




# HealthData
## /health-data

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

duplicate(45x)
- DuplicateHealthDataException(452): 중복된 활동 데이터

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


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

duplicate(45x)
- DuplicateHealthDataException(452): 중복된 움직인 거리 데이터

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


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

duplicate(45x)
- DuplicateHealthDataException(452): 중복된 걸음수 데이터

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## saveWeight
#### /save/weight: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- double weight

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


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

duplicate(45x)
- DuplicateHealthDataException(452): 중복된 수면 데이터

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## recentData
#### /recent?memberUuid={memberUuid}&data={data}: GET - URL 인코딩

**Header**
- String Authorization

**Param**
- String memberUuid: AES 암호화
- String data

### possible error
auth(401): 권한 없음(토큰)

invalid(42x)
- InvalidDataTypeException(425): 유효하지 않은 데이터 타입

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원

decoding(46x)
- UnsupportedEncodingException(460): 디코딩 오류


## findHealthData
#### /find/all

**Header**
- String auth

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## findActive
#### /find/active: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## findMove
#### /find/move: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## findWalk
#### /find/walk: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## findSleep
#### /find/sleep: POST

**Header**
- String Authorization

**Body**
- String memberUuid
- LocalDate date

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


# Location
## /location

## saveLocation
#### /save: POST

**Header**
- String Authorization

**Body**
- String reqDto: AES 암호화
  - String memberUuid;
  - LocalDateTime date;
  - LocationDto location;
    - double latitude;
    - double longitude;

### possible error
auth(401): 권한 없음(토큰)

duplicate(45x)
- DuplicateLocationException(451): 중복된 위치 정보

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## recentLocation
#### /recent?memberUuid={memberUuid}: GET - URL 인코딩

**Header**
- String Authorization

**Param**
- String memberUuid: AES 암호화

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원
- NotExistLocationException: 존재하지 않는 위치 정보




# Diagnosis
## /diagnosis

## returnDiagnosisSheet
#### /sheet?num={num}: GET

**Header**
-String Authorization

**Param**
-int num

### possible error
auth(401): 권한 없음(토큰)

incorrect(44x)
- IncorrectNumException(441): 올바르지 않은 번호


## randomWords
### /random-words: GET

**Header**
-String Authorization

### possible error
auth(401): 권한 없음(토큰)


## startDiagnosis
### /start: POST

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## diagnosisScore
### /score: POST

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

invalid(42x)
- InvalidScoreException(426): 유효하지 않은 점수

incorrect(44x)
- IncorrectNumException(441): 올바르지 않은 번호


## diagnosisAnswer
### /answer: POST

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

invalid(42x)
- InvalidScoreException(426): 유효하지 않은 점수


## diagnosisResult
### /result: GET

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원



# Relation
## /relation

## requestRelation
#### /request: POST

**Header**
- String Authorization

**Body**
- String fromMemberUuid;
- String toMemberId;
- String requestType; guardian/ward

## possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원

duplicate(45x)
- DuplicateRelationException(453): 이미 존재하는 관계


## saveGuardian
#### /save/guardian: POST

**Header**
- String Authorization

**Body**
- String notificationUuid
- String guardianUuid
- String wardUuid

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## saveWard
#### /save/ward: POST

**Header**
- String Authorization

**Body**
- String notificationUuid
- String guardianUuid
- String wardId

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## infoGuardian
#### /info/guardian?memberUuid={memberUuid}: GET - URL 인코딩

**Header**
- String Authorization

**Param**
- String memberUuid: AES 암호화

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## infoWard
#### /info/ward?memberUuid={memberUuid}: GET - URL 인코딩

**Header**
- String Authorization

**Param**
- String memberUuid: AES 암호화

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원




# Notification
## notification

## findNotification
#### /find?memberUuid={memberUuid}: GET - URL 인코딩

**Header**
- String Authorization

**Param**
- String memberUuid: AES 암호화

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원


## deleteNotification
#### /delete?notificationUuid={notificationUuid}: DELETE - URL 인코딩

**Header**
- String Authorization

**Param**
- String notificationUuid: AES 암호화

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistNotificationException(433): 존재하지 않는 알림




# DementiaCenter
## /dementia-center

## getDementiaCenter
#### ?page={page}: GET

**Header**
- String Authorization

### possible error
auth(401): 권한 없음(토큰)




# DementiaResult
## /dementia-result

## findResult
#### /find?memberUuid={memberUuid}: GET - URL 인코딩

**Header**
- String Authorization

**Param**
- String memberUuid: AES 암호화

### possible error
auth(401): 권한 없음(토큰)

exist(43x)
- NotExistMemberException(431): 존재하지 않는 회원