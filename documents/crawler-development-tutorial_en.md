# Crawler Development Tutorial

1. Add information of website in configuration file `hot-crawler/src/main/resources/sites.properties`。Notice the number x, y of cates[x].sites[y] can't be repeated with others.

   ```
   #example
   cates[0].sites[0].id = 1
   cates[0].sites[0].name = example
   cates[0].sites[0].processorName = ExampleHotProcessor
   cates[0].sites[0].url = https://example.com/list
   cates[0].sites[0].prefix = https://example.com
   ```
   
2. Add processor of crawler. e.g. `hot-crawler/src/main/java/com/taogen/hotcrawler/commons/crawler/impl/ExampleHotProcessor.java`

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

   Tips: You can also extend one abstract class if need. Abstract classes have implemented some operations for you.

3. Test and running on local.

   - Startup Redis server in your computer.

   - Execute unit test.

     ```
     $ mvn test
     ```

   - Running project by Spring Boot maven plugin.

     ```
     $ mvn spring-boot:run
     ```

   - Visiting http://localhost:8080 to access your created website crawler page.