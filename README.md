# Info Crawler

Content

- Prepared
- Implementation



### Prepared

project name: HotCrawler

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

- zhihu
- V2EX
- Weibo
- Douban
- Tianya
- CloudMusic

Techiques

- Backend
  - Spring Boot
  - Swagger
  - JUnit
- Frontend
  - Vue.js
- Docker+Jenkins
- Database

### Implementation

### Build Environment

Build Maven Project run hello-world of spring-boot

Project Function Module

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

### Interfaces Design with Swagger

Using Swagger

visiting http://localhost:8080/swagger-ui.html

- Integer with Spring Fox
- Fix config bean doesn't scan the problem.
- Using Swagger-UI.
- Using Swagger Core annotations in controller.

Design API



### Test Drive Development

- Integrate JUnit5

### Developing Crawler with Apache Nutch

### References

[Documenting Spring Boot REST API with Swagger and SpringFox](https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/)


