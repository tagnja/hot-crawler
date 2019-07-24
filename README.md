# Hot Crawler


[Const520 热点聚合](http://hot.const520.com)。一站式资讯阅览，轻松掌握热点话题。

![网站首页图](home-page.png)

### 已展示站点

- V2EX
- 知乎
- GitHub
- 新浪微博
- 豆瓣
- 天涯
- 网易云音乐
- Hacker News

### 待处理站点 TODO

- Twitter. GET trends/place — Twitter Developers 
- Medium. `https://medium.com/topic/popular`
- Reddit. `https://www.reddit.com/hot/`
- YouTube. `https://www.youtube.com/feed/trending`
- 开发者头条. `https://toutiao.io/`

### 使用技术
后端

- Spring Boot
- Spring Boot Web
- Spring Boot Redis
- Spring Boot Devtools
- Spring Scheduling Tasks
- RESTful API
- Swagger UI/SpringFox
- Lombok

页面爬取解析
- Jsoup
- Jsonpath

前端

- Thymeleaf
- jQuery

缓存
- Redis

测试
- JUnit4

部署

- Nginx 代理
- Spring Boot embedded server - Jetty

### 如何运行
运行前必须保证已安装 Git, JDK, Maven, Redis 等软件。

```shell
$ git clone https://github.com/tagnja/hot-crawler.git
$ cd hot-crawler
$ mvn package spring-boot:repackage
$ java -jar target/hotcrawler-1.0-SNAPSHOT.jar
```

### 如何贡献

可对本项目做以下贡献

1. 添加有价值的网站到“待处理站点” 的 todo list 中。
2. 实现“待处理站点”爬虫。
3. 修复 issues。

添加一个页面爬虫快速指南

1. 添加站点信息在 hot-crawler/src/main/resources/sites.properties。注意，序号 sites[0] 不能和已存在的重复。

   ```
   #example
   sites[0].id = 1
   sites[0].name = example
   sites[0].processorName: ExampleHotProcessor
   sites[0].domain: https://example.com
   sites[0].hotPageUrl: https://example.com/xxx
   sites[0].hotApiUrl: xxx
   sites[0].itemKey: xxx
   ```

2. 添加热点爬取处理器，如 hot-crawler/src/main/java/com/taogen/hotcrawler/commons/crawler/impl/ExampleHotProcessor.java

   ```java
   @Component("ExampleHotProcessor")
   public class ExampleHotProcessor implements HotProcessor
   {
   	@Override
       public List<Info> crawlHotList() 
       {
       	...
       }
   }
   ```

3. 本地测试和运行

   - 运行 Redis 缓存。

   - 进入项目根目录，执行单元测试

     ```
     $ mvn test
     ```

   - 进入项目根目录，使用 maven 插件运行项目

     ```
     $ mvn spring-boot:run
     ```

   - 访问 http://localhost:8080 ，即可看到你添加的爬虫 example 的页面。

### 其它

- [更新日志](update_log.md) 
