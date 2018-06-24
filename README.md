# spring-boot实践
+ 通过一个完整的demo项目代码，学习并分析阐述spring-boot中的主要功能

## 功能

### 运行Example.java
+ pom中继承spring-boot-starter-parent
+ 运行mvn spring-boot:run
+ 访问localhost:8080得到Hello World!

```
解析：
1. @RestController: 相当于@Controller和@ResponseBody的结合体，让方法体的return对象转换为json写入http的响应body
2. @RequestMapping: http接口访问路径
3. @EnableAutoConfiguration: 会聪明地根据相关依赖配置，“猜测”进程所需bean并且初始化。如果有@SpringBootApplication，等于默认加了此注解
```

## 参考官方文档
+ https://docs.spring.io/spring-boot/docs/2.0.2.RELEASE/reference/htmlsingle
