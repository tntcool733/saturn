# spring-boot
+ 通过一个完整的demo项目代码，学习并分析阐述spring-boot中的主要功能

## 实践

### 运行Example.java
+ 示例com.tnt.springbootpractice.Example
+ pom中继承spring-boot-starter-parent
+ 运行mvn spring-boot:run
+ 访问localhost:8080得到Hello World!

```
解析：
1. @RestController: 相当于@Controller和@ResponseBody的结合体，让方法体的return对象转换为json写入http的响应body
2. @RequestMapping: http接口访问路径
3. @EnableAutoConfiguration: 会聪明地根据相关依赖配置，“猜测”进程所需bean并且初始化。如果有@SpringBootApplication，等于默认加了此注解
```

### 依赖管理
+ 我们可以看到spring-boot-starter-parent中的父pom是spring-boot-dependencies
+ spring-boot-dependencies用于管理所有spring-boot自身已经带的对外依赖
+ 如果需要，我们也可以在业务中进行版本覆盖,见解析

```
解析:
1. 业务中进行依赖版本覆盖，配置相应的properties进行覆盖就可
<properties>
	<spring-data-releasetrain.version>Fowler-SR2</spring-data-releasetrain.version>
</properties>
```

### Bean注入
+ 示例com.tnt.springbootpractice.facade.impl.UserFacadeImpl

```
解析：
1. 直接@Autowired注入依赖bean
2. 构造函数+@Autowired注入依赖bean
3. 单构造函数可以去除@Autowired
```

## 参考官方文档
+ https://docs.spring.io/spring-boot/docs/2.0.2.RELEASE/reference/htmlsingle
