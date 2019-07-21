# Hot Crawler


Content

- Requirement Analysis
- System Design
- Code Implementation
- Test & Deployment
- Optimization Function

### Main


### 1. Requirement Analysis

#### 1.1 Requirements

- Crawler hot content from media.
- Frontend and Backend separation.
- Timer Crawler. 

Stage

- Stage 1
  - Write Crawler one site API interface.
  - Write Frontend page.
  - Easy Deploy.
- Stage 2
  - Write  crawler more sites API interface.
  - Update Frontend page.
  - Auto Deploy.
- Stage 3
  - Crawler WeChat API
  - Storing information URL to DB.
  

Media

- Zhihu
- V2EX
- Weibo
- Douban
- Tianya
- CloudMusic

#### 1.2 Using Techniques

- Backend
  - Spring Boot 2
  - Swagger 2
  - JUnit 5
  - Apache Nutch
- Frontend
  - Vue.js
- Test & Deployment
  - Docker
  - Jenkins
- Database & Cache

### 2. System Design

#### 2.1 Function Module

System Function

Frontend --> Backend Controller --> Cache <--> Crawler 

#### 2.2 Database Design

t_info_type

| Name | Type    | Length | NULL     | Key  | Description |
| ---- | ------- | ------ | -------- | ---- | ----------- |
| id   | varchar | 64     | not null | P    |             |
| name | varchar | 64     | not null |      |             |

t_info

| Name  | Type    | Length | NULL     | Key  | Description |
| ----- | ------- | ------ | -------- | ---- | ----------- |
| id    | varchar | 64     | not null | P    |             |
| title | varchar | 128    | not null |      |             |
| url   | varchar | 255    | not null |      |             |



#### 2.3 Interfaces Design

1\. v1/types

Method: GET

Data Type: JSON

Parameters: null

Result:

```json
{
    ret_code: 0,
    ret_msg: "success",
    data: [
        {
            id: 1
            name: "v2ex"
        },
        ...
    ]
}

```

2\. v1/types/{id}/infos

Method: GET

Data Type: JSON

Parameters: null

Result:

```json
{
    ret_code: 0,
    ret_msg: "success",
    data: [
        {
            id: 1,
            title: "",
            url: ""
        },
        ...
    ]
}
```



### 3. Code Implementation

#### 3.1 Implementation Backend


##### 3.1.1 Build Project

Build Maven Project, run hello-world of spring-boot

Project Directory Structure Definition

```$xslt
/controller
/service
/dao
/crawler
/entity
/util
/conf
```

##### 3.1.2 Integrate Swagger

visiting http://localhost:8080/swagger-ui.html

- Integer with Spring Fox
- Fix config bean doesn't scan the problem.
- Using Swagger-UI.
- Using Swagger Core annotations in controller.

##### 3.1.3 Test Drive Development

- Integrate JUnit5

##### 3.1.4 Developing Crawler

Jsoup, Jsonpath

##### 3.1.5 Timer Scheduler

##### 3.1.6 Using Redis Cache.

##### 3.1.7 Implementation API.

##### 3.1.8 Add Log File



#### 3.2 Implementation Frontend

### 4. Test & Deployment

#### CD/CI

### 5. Optimization Function

Notify













### References

[Documenting Spring Boot REST API with Swagger and SpringFox](https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/)


