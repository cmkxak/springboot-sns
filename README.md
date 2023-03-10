# MutsaSNS

## ๐ฌ ์๋น์ค ์๊ฐ
<p align="center">
<img src="https://42place.innovationacademy.kr/wp-content/uploads/2020/09/likelion.png">
<br>
MutsaSNS๋ ๋ฉ์์ด ์ฌ์์ฒ๋ผ ๋ฐฑ์๋ ์ค์ฟจ 2๊ธฐ ํ์ด๋ ํ๋ก์ ํธ ์ํ์ผ๋ก, SNS(Social-Network-Service)๋ฅผ ์ ๊ณตํ๋ ์น ์ฌ์ดํธ ์๋๋ค.
</p>

## ๐จ TECH STACK
![Spring Boot](https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/spring_security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MYSQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=AmazonEC2&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white)

## ๐ ERD
![erd](https://user-images.githubusercontent.com/71599552/211246134-b8359d17-9a03-4774-a39c-f268a8620484.PNG)

## ๐ Endpoints
|  ๊ตฌ๋ถ  |  HTTP  |              URI              |          ์ค๋ช           |
|:----:|:------:|:-----------------------------:|:------------------------:|
| USER |  POST  |       api/v1/users/join       |         ํ์๊ฐ์          |
| USER |  POST  |      api/v1/users/login       |      ๋ก๊ทธ์ธ ๋ฐ ํ ํฐ ๋ฐ๊ธ      |
| USER |  POST  | api/v1/users/{id}/role/change | ์ ์  ๊ถํ ๋ณ๊ฒฝ (ONLY ADMIN) |
| POST |  GET   |         api/v1/posts          |      ๊ฒ์๊ธ ๋ฆฌ์คํธ ์กฐํ       |
| POST |  GET   |       api/v1/posts/{id}       |       ๊ฒ์๊ธ ์์ธ ์กฐํ       |
| POST |  POST  |         api/v1/posts          |        ๊ฒ์๊ธ ๋ฑ๋ก         |
| POST |  PUT   |       api/v1/posts/{id}       |        ๊ฒ์๊ธ ์์          |
| POST | DELETE |       api/v1/posts/{id}       |        ๊ฒ์๊ธ ์ญ์          |

## ๐ Endpoint Example
### ํ์ ๊ฐ์ [POST] /api/v1/users/join

**request body**
```json
{
    "userName":"testuser",
    "password":"password"
}
```

**response body**
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "userId": 12,
        "userName": "testuser"
    }
}
```

### ๋ก๊ทธ์ธ [POST] /api/v1/users/login

**request body**
```json
{
    "userName":"testuser",
    "password":"password"
}
```

**response body**
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "jwt": "eyJhbGciOiJIUzI1NiJ9...
    }
}
```

### ๊ฒ์๊ธ ๋ฆฌ์คํธ ์กฐํ [GET] /api/v1/posts

**response body**
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "content": [
            {
                "id": 26,
                "title": "๊ด๋ฆฌ์ ์๋๋ค.",
                "body": "๊ด๋ฆฌ์ ์์.",
                "userName": "root",
                "createdAt": "2022-12-27 14:51:43",
                "lastModifiedAt": "2022-12-27 14:51:43"
            },
            {
                "id": 11,
                "title": "์๋ก์ด ๊ธ",
                "body": "์ ์ถํฉ์๋ค.",
                "userName": "root",
                "createdAt": "2022-12-27 13:33:00",
                "lastModifiedAt": "2022-12-27 13:33:00"
            }
        ],
        "pageable": {
            "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
            },
            "offset": 0,
            "pageSize": 20,
            "pageNumber": 0,
            "unpaged": false,
            "paged": true
        },
        "last": false,
        "totalElements": 47,
        "totalPages": 1,
        "size": 20,
        "number": 0,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "first": true,
        "numberOfElements": 20,
        "empty": false
    }
}
```
### ๊ฒ์๊ธ ์์ธ ์กฐํ [GET] /api/v1/posts/{id}

**response body**

```json
{
    "resultCode": "SUCCESS",
    "result": {
        "id": 2,
        "title": "hello-new-title",
        "body": "hello-new-body",
        "userName": "kyeongrok34",
        "createdAt": "2022-12-27 00:52:16",
        "lastModifiedAt": "2022-12-27 00:52:16"
    }
}
```
### ๊ฒ์๊ธ ๋ฑ๋ก [POST] /api/v1/posts

**request body**

```json
{
    "title":"์ ๋ชฉ",
    "body":"๋ด์ฉ"
}
```
**response body**
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "message": "ํฌ์คํธ ๋ฑ๋ก ์๋ฃ",
        "postId": 54
    }
}
```
### ๊ฒ์๊ธ ์์  [PUT] /api/v1/posts/{id}

**request body**
```json
{
    "title":"์์ ๋ ์ ๋ชฉ",
    "body":"์์ ๋ ๋ด์ฉ"
}
```
**response body**
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "message": "ํฌ์คํธ ์์  ์๋ฃ",
        "postId": 54
    }
}
```
### ๊ฒ์๊ธ ์ญ์  [DELETE] /api/v1/posts/{id}

**response body**
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "message": "ํฌ์คํธ ์ญ์  ์๋ฃ",
        "postId": 54
    }
}
```

### ์ ์  ๊ถํ ๋ณ๊ฒฝ [POST] /api/v1/{id}/role/change

**request body**
```json
{
  "role": "ADMIN" | "USER"
}
```
**response body**
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "message": "๋ณ๊ฒฝ ๋์์ต๋๋ค.",
        "role": "ADMIN"
    }
}
```

## ๐๋ฏธ์ ์๊ตฌ์ฌํญ ๋ถ์ & ์ฒดํฌ๋ฆฌ์คํธ
### ์ฒดํฌ๋ฆฌ์คํธ
- [x] ์ธ์ฆ/์ธ๊ฐ ํํฐ ๊ตฌํ
- [x] ๋ก๊ทธ์ธ/ํ์๊ฐ์ API ๊ตฌํ
- [x] ๊ฒ์๊ธ ์กฐํ/๋ฑ๋ก/์์ /์ญ์  API ๊ตฌํ
- [x] Swagger
- [x] ADMIN ํ์์ ๋ฑ๊ธ ๋ณ๊ฒฝ ๊ธฐ๋ฅ
- [x] ADMIN ํ์์ ๊ฒ์๊ธ ์์ /์ญ์  ๊ตฌํ

- [x] Gitlab์ ํตํ CI/CD ๊ตฌ์ถ
- [ ] ํ๋ฉด UI ๊ฐ๋ฐ 

### 1์ฃผ์ฐจ ๋ฏธ์ ์์ฝ
**[์ ๊ทผ ๋ฐฉ๋ฒ]**
---
- [x] **[์ธ์ฆ/์ธ๊ฐ ํํฐ ๊ตฌํ]**
- RESTful ์ ํ๋ฆฌ์ผ์ด์์ ์ธ์ฆ/์ธ๊ฐ ์ฒ๋ฆฌ๋ฅผ ์ํด UsernamePasswordAuthenticationFilter ์์ ์ปค์คํ ํํฐ ๋ฐฐ์น
- JWT(Json Web Token)์ ํตํ, ์ธ์ฆ ๊ตฌํ
- UserDetails / UserDetailsService๋ฅผ ํ์ฉํ์ฌ ์ฌ์ฉ์์ ์ ๋ณด๋ฅผ ๊ฐ์ ธ์ค๋๋ก ๊ตฌํ
- "HMAC using SHA-256" ์๊ณ ๋ฆฌ์ฆ์ ์ฌ์ฉํ์ฌ token ํด์ฑ ์ฒ๋ฆฌ
- Token์ Claim์ UserRole(USER / ADMIN)์ด ๋ด๊ธฐ๋๋ก ๊ตฌํ
- ์ธ์ฆ์ด ์คํจํ  ๊ฒฝ์ฐ์ ์ฒ๋ฆฌ๋ฅผ ๋ด๋นํ๋ JwtAuthenticationEntryPoint ํด๋์ค ๊ตฌํ
- ๊ถํ์ด ์๋ ๊ฒฝ์ฐ์ ์ฒ๋ฆฌ๋ฅผ ๋ด๋นํ๋ JwtAccessDeniedHandler ํด๋์ค ๊ตฌํ
- ๊ฐ์ธ ๊ณต๋ถ ๋งํฌ : https://github.com/cmkxak/springboot-security-jwt


- [x] **[ํ์๊ฐ์/๋ก๊ทธ์ธ API ๊ตฌํ]**
- Spring security๋ฅผ ํ์ฉํ์ฌ ๊ตฌํ
- ํ์,๋นํ์ ๋ชจ๋ ์ ๊ทผ๋๋๋ก ๊ตฌํ
- [ํ์๊ฐ์] ์ค๋ณต๋ ์ ์ ์ ๊ฒฝ์ฐ, ํ์๊ฐ์์ด ๋ถ๊ฐํ๋๋ก ๊ตฌํ
- [ํ์๊ฐ์] BCryptPasswordEncoder๋ฅผ ํตํ ์ํธํ ๋ ๋น๋ฐ๋ฒํธ๊ฐ ์ ์ฅ๋๋๋ก ๊ตฌํ
- [๋ก๊ทธ์ธ] userName or password ์๋ฌ์ ๊ฒฝ์ฐ, ๋ก๊ทธ์ธ์ด ๋ถ๊ฐํ๋๋ก ๊ตฌํ
- [๋ก๊ทธ์ธ] ๋ก๊ทธ์ธ ์ฑ๊ณต ์, JWT๊ฐ ๋ฐ๊ธ๋๋๋ก ๊ตฌํ


- [x] **[Gitlab์ ํตํ CI/CD ๊ตฌ์ถ]**
- docker build์ push๊ฐ ๊ฐ๋ฅํ๋๋ก ์์ฑํ DockerFile์ ํตํ docker build ๋ฐ push ์๋ํ
- ๋ฐฐํฌ ์๋ฒ(AWS EC2)์ Linux ๊ธฐ๋ฅ์ธ, Crontab์ ํตํ Container Registry(Gitlab)์ ๋ณ๊ฒฝ ์ฌํญ ๋ฐ์ ์๋ํ


- [x] **[Swagger]**
- API ๋ช์ธ๋ฅผ ํธํ๊ฒ ๊ด๋ฆฌํ  ์ ์๋ Swagger ๋ผ์ด๋ธ๋ฌ๋ฆฌ ์ถ๊ฐ


- [x] **[๊ฒ์๊ธ ๋ฑ๋ก/์กฐํ/์์ /์ญ์  API ๊ตฌํ]**
>๊ฒ์๊ธ ๋ฑ๋ก
- ๋ก๊ทธ์ธ ํ ํ์๋ง ๋ฑ๋ก์ด ๊ฐ๋ฅํ๋๋ก ๊ตฌํ
- ๋ค๋์ผ ์ฐ๊ด๊ด๊ณ ์ค์  (Post -> User)
- Post Entity ํด๋์ค์ ๊ฒ์๊ธ ์์ฑ์ ๋ด๋นํ๋ ์์ฑ ๋ฉ์๋ ๊ตฌํ
- ๊ฒ์๊ธ ๋ฑ๋ก ์, ์์ฑ์๊ฐ ์ค์ ๋๋๋ก ๊ตฌํ
  <br>

>๊ฒ์๊ธ ์กฐํ (๊ฒ์๊ธ ๋ฆฌ์คํธ ์กฐํ)
- @PageDefault ์ด๋ธํ์ด์์ ํตํ ํ์ด์ง ์ฒ๋ฆฌ
    - defaultSize = 20, ์์ฑ์ผ์ ๊ธฐ์ค์ผ๋ก ๋ด๋ฆผ์ฐจ์ ์ ๋ ฌ
      <br>

> ๊ฒ์๊ธ ์กฐํ (์์ธ ์กฐํ)
- post๊ฐ ์กด์ฌํ์ง ์์ ๊ฒฝ์ฐ, 404 Error๊ฐ ๋ฐํ๋๋๋ก ๊ตฌํ
  <br>

> ๊ฒ์๊ธ ์์ 
- ์ธ์๋ก ๋์ด์จ postId์ userName์ ํ์ฉํ์ฌ ์์ฑ์์ ์์  ์์ฒญ์์ ๋๋ฑ์ฑ ๊ฒ์ฆ ๋ก์ง ๊ตฌํ
- ADMIN์ ๊ฒฝ์ฐ, ๊ฒ์๊ธ์ ์์ ์ด ๊ฐ๋ฅํ๋๋ก ๊ตฌํ
- ์์ ๋๊ฐ์ง ๊ฒฝ์ฐ๊ฐ ์๋๋ฉด, ๊ถํ์ด ์๋ค๋ ์๋ฌ๊ฐ ๋ฐํ๋๋๋ก ๊ตฌํ
  <br>

> ๊ฒ์๊ธ ์ญ์ 
- ์ธ์๋ก ๋์ด์จ, postId์ userName์ ํ์ฉํ์ฌ ์์ฑ์์ ์ญ์  ์์ฒญ์์ ๋๋ฑ์ฑ ๊ฒ์ฆ ๋ก์ง ๊ตฌํ
- ADMIN์ ๊ฒฝ์ฐ, ๊ฒ์๊ธ์ ์ญ์ ๊ฐ ๊ฐ๋ฅํ๋๋ก ๊ตฌํ
- ์์ ๋๊ฐ์ง ๊ฒฝ์ฐ๊ฐ ์๋๋ฉด, ๊ถํ์ด ์๋ค๋ ์๋ฌ๊ฐ ๋ฐํ๋๋๋ก ๊ตฌํ
  <br>


- [x] **[๋ฑ๊ธ์ ๊ธฐ๋ฅ(ONLY ADMIN)]**
- data.sql ํ์ผ์ ํตํ, ์ ํ๋ฆฌ์ผ์ด์ ์คํ ์, ํ๋์ ADMIN ์ ์ ๊ฐ ์กด์ฌํ๋๋ก ๊ตฌํ
- Spring Security์ hasRole() ๋ฉ์๋๋ฅผ ํ์ฉํ์ฌ admin์ ๊ฒฝ์ฐ๋ง ๊ฐ๋ฅํ๋๋ก ๊ตฌํ


**[ํน์ด์ฌํญ]**
- ํ์คํธ ์ฝ๋๋ฅผ ์ค๊ณํจ์ ์์ด, ์ปจํธ๋กค๋ฌ ํ์คํธ ์ฝ๋์ ์๋น์ค ํ์คํธ ์ฝ๋์ ํ์คํ ์ฐจ์ด๋ฅผ ์์ง ์ ๋ชจ๋ฅด๊ฒ ๋ค.
  <br>

- TDD๋ผ ํจ์, ํ์คํธ ์ฝ๋๋ฅผ ๋จผ์  ๊ฐ๋ฐํ๊ณ , ์์ฑ๋ ํ์คํธ ์ฝ๋๋ฅผ ๋ฐํ์ผ๋ก ์ค์  ๋ก์ง์ ์์ฑํ๋ ์์๋ก ์๊ณ  ์๋ค. ๊ฐ ๊ณ์ธต์ ๋ชจ๋  ํ์คํธ ์ฝ๋๋ฅผ ์์ฑํ๋ฉฐ ๊ฐ๋ฐํ๋๋ฐ์ ๋ง์ ๋ธ๋ ฅ์ด ๋ค์ด ๊ฐ ๊ฒ ๊ฐ์๋ฐ, ์ฃผ๋ก ์ด๋ค ๊ณ์ธต์ด ๋จผ์  ๊ฐ๋ฐ๋๋์ง ๊ถ๊ธํ๋ค. (ํน์ ๊ฐ๋ฐ ์์์ ๊ดํ ๊ด๋ก๊ฐ ์๋์ง?)
  <br>

- ํ๋ฉด ๊ฐ๋ฐ UI๋ฅผ ํ์ง ๋ชปํด ์์ฝ๋ค. ๋ฆฌ์กํธ๋ฅผ ํ์ฉํ์ฌ FRONT-END๋ ๊ตฌ์ถํด๋ด์ผ ๊ฒ ๋ค.
  <br>
