## 항해99 과제

---
### 게시판 만들기 ( Java Spring CRUD구현 )
- - - 

#### 유스케이스
![유스케이스_과제2](https://users-images.githubusercontent.com/111578825/218037858-2be7054b-bb3a-4e86-b2bf-d844f860cfc8.png)


- - -

#### ERD
![ERD](https://user-images.githubusercontent.com/111578825/218122342-b542a839-754d-460e-bf27-a1fe055e116d.png)




- - -



#### api명세서


| Index | Method |             URL              | Request                                                                                                 | Response                                                                                                                                                                                                             |
|:-----:|:------:|:----------------------------:|:--------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|   1   |  GET   |           api/get            |                                                                                                         | {<br> 　"title": "제목",<br>　"userName": "홍길동",<br>　"content": "안녕하세요 홍길동입니다",<br>　"createdAt": "2023-02-06T10:05:44.402421"<br>}                                                                                       |
|   2   |  GET   |         api/get/{id}         |                                                                                                         | {<br> 　"title": "제목",<br>　"userName": "홍길동",<br>　"content": "안녕하세요 홍길동입니다",<br>　"createdAt": "2023-02-06T10:05:44.402421"<br>}                                                                                       |
|   3   |  POST  |           api/add            | {<br> 　"title": "제목",<br>　"userName": "홍길동",<br>　"content": "안녕하세요 홍길동입니다",<br>　"password": "1234"<br>} | {{<br>　"createdAt": "2023-02-06T10:34:11.7218094",<br>　"modifiedAt": 2023-02-06T10:34:11.7218094",<br>　"id": 7,<br>　"title": "제목",<br>　"userName": "홍길동",<br>　"content": "안녕하세요 홍길동입니다",<br>　"password": "1234"<br>} |
|   4   |  PUT   |       api/update/{id}        | {<br>　"title":"제목2",<br>　"userName":"홍길동2",<br>　"content":"안녕하세요 홍길동입니다2",<br>　"password":"1234"<br>}   | 저장 완료                                                                                                                                                                                                                |
|   5   |  POST  |       api/delete/{id}        | {<br>　"password":"1234"<br>}                                                                            | 삭제 완료                                                                                                                                                                                                                |
|   6   | DELETE | api/delete/{id}?pasword=1234 |                                                                                                         | 삭제 완료                                                                                                                                                                                                                |
|   7   |  POST  |          api/users          | { <br>　"username" : "홍길동",<br/>　"password" : "1234"<br/>}                                               | {<br/>　"response" : success<br/>}                                                                                                                                                                                    |
|   8   |  POST  |          api/login           | { <br>　"username" : "홍길동",<br/>　"password" : "1234"<br/>}                                               | Header <br/>Authorization : Bearer \<JWT> <br/>success                                                                                                                                                               |

------