## 항해99 과제

---
### 게시판 만들기 ( Java Spring CRUD구현 )
- - - 

#### 유스케이스
![유스케이스_과제2](https://user-images.githubusercontent.com/111578825/218233044-b9c8752a-19b2-4cd9-818a-ab58b9b51d88.png)


- - -

#### ERD
![ERD](https://user-images.githubusercontent.com/111578825/218122342-b542a839-754d-460e-bf27-a1fe055e116d.png)




- - -



#### api명세서


| Index | Method |       URL       |                                                                        RequestHeader                                                                        | Request                                                                | Response                                                                                                                                                                                                  | Response Header |
|:-----:|:------:|:---------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------------:|
|   1   |  GET   |    api/posts    |                                                                                                                                                             |                                                                        | {<br> 　"title": "제목",<br>　"userName": "홍길동",<br>　"content": "안녕하세요 홍길동입니다",<br>　"createdAt": "2023-02-06T10:05:44.402421"<br>}                                                                            |                 |
|   2   |  GET   |  api/post/{id}  |                                                                                                                                                             |                                                                        | {<br> 　"title": "제목",<br>　"userName": "홍길동",<br>　"content": "안녕하세요 홍길동입니다",<br>　"createdAt": "2023-02-06T10:05:44.402421"<br>}                                                                            |                 |
|   3   |  POST  |    api/post     | Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.<br/>eyJzdWIiOiJiaW4xMjM0IiwiZXhwIjoxNjY5ODcwNDU<br/>yLCJpYXQiOjE2Njk4NjY4NTJ9.mm8wgaV8M70hidh<br/>PX4Ut6UONZGaxjA1KnOJT1mO59Xc | {<br> 　"title": "제목",<br/>　"content": "안녕하세요 홍길동입니다"<br>}              | {<br> 　"id": 1,<br/>　"title": "제목2",<br/>　"userName": "a12345789",<br/>　"content": "안녕하세요 홍길동입니다",<br/>　"createdAt": "2023-02-12T21:04:16.5391188",<br/>　"modifiedAt": "2023-02-12T21:04:16.5391188"<br>} |                 |
|   4   |  PUT   |  api/post/{id}  | Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.<br/>eyJzdWIiOiJiaW4xMjM0IiwiZXhwIjoxNjY5ODcwNDU<br/>yLCJpYXQiOjE2Njk4NjY4NTJ9.mm8wgaV8M70hidh<br/>PX4Ut6UONZGaxjA1KnOJT1mO59Xc | {<br>　"title":"제목2",<br>　"content":"안녕하세요 홍길동입니다2"<br>}                | {<br> 　"id": 1,<br/>　"title": "제목2",<br/>　"userName": "a12345789",<br/>　"content": "안녕하세요 홍길동입니다",<br/>　"createdAt": "2023-02-12T21:04:16.5391188",<br/>　"modifiedAt": "2023-02-12T21:04:16.5391188"<br>} |                 |
|   5   | DELETE |  api/post/{id}  | Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.<br/>eyJzdWIiOiJiaW4xMjM0IiwiZXhwIjoxNjY5ODcwNDU<br/>yLCJpYXQiOjE2Njk4NjY4NTJ9.mm8wgaV8M70hidh<br/>PX4Ut6UONZGaxjA1KnOJT1mO59Xc |                                                                        | {<br/>　"msg": "게시글 삭제 성공",<br/>　"statusCode": 200<br/>}                                                                                                                                                   |                 |
|   7   |  POST  | api/auth/signup |                                                                                                                                                             | { <br>　"username" : "홍길동123",<br/>　"password" : "1234568901234A"<br/>} | {<br/>　"msg": "회원가입 성공",<br/>　"statusCode": 200<br/>}                                                                                                                                                     |                 |
|   8   |  POST  | api/auth/login  |                                                                                                                                                             | { <br>　"username" : "홍길동123",<br/>　"password" : "1234568901234A"<br/>} | {<br/>　"msg": "로그인 성공",<br/>　"statusCode": 200<br/>}                                                                                                                                                      | Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.<br/>eyJzdWIiOiJiaW4xMjM0IiwiZXhwIjoxNjY5ODcwNDU<br/>yLCJpYXQiOjE2Njk4NjY4NTJ9.mm8wgaV8M70hidh<br/>PX4Ut6UONZGaxjA1KnOJT1mO59Xc |

------