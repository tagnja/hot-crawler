# 实现一个网站爬虫快速指南

1. 添加站点信息在 hot-crawler/src/main/resources/sites.properties。注意，cates[x].sites[y] 中的序号 x, y  不能和已存在的重复。

   ```
   #example
   cates[0].sites[0].id = 1
   cates[0].sites[0].name = example
   cates[0].sites[0].processorName = ExampleHotProcessor
   cates[0].sites[0].url = https://example.com/list
   cates[0].sites[0].prefix = https://example.com
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

   Tips：你也可以继承某个实现 HotProcessor 的抽象类，这些抽象类帮你实现了一些操作。

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