# microservice-hystirx
#### 简介
该项目主要包括Hystrix熔断的简单使用、熔断监控以及配合监控的聚合服务，使用Nacos注册中心，服务间调用是用Feign的形式。
SpringBoot的版本使用的是2.1.15.RELEASE，需要注意的是Hystrix针对与不同的SpringBoot版本使用方式稍微有些不同。

#### 代码介绍：
**hystrix-consumer服务**：hystrix的使用代码主要是写在这个服务中

引入依赖：

```xml
<!--hystrix熔断-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>

<!--被熔断监控服务所监控-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
启动类添加注解,使用过程中发现，在使用SpringBoot1.5.8版本的时候，只需要加入`@EnableHystrix`注解即可，不需要其他操作。但是在2.1.15版本需要加入`@EnableHystrix`、`@EnableCircuitBreaker`两个注解，并且需要在启动类加上上面一段代码，不然的话不会被监控服务所监控：
```java
@Bean
public ServletRegistrationBean getServlet(){
  HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
  ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
  registrationBean.setLoadOnStartup(1);
  registrationBean.addUrlMappings("/actuator/hystrix.stream");
  registrationBean.setName("HystrixMetricsStreamServlet");
  return registrationBean;
}
```
在使用Hystrix的时候，只需要在需要使用熔断服务的方法上加上`@HystrixCommand`注解，然后可以利用`commandProperties`自定义熔断的一些规则与配置。也可以使用`fallbackMethod`来指定在使用Hystrix的时候，只需要在需要使用熔断服务的方法上加上`@HystrixCommand`注解，然后可以利用`commandProperties`自定义熔断的一些规则与配置。也可以使用`fallbackMethod`来指定备选方案方法名，也可以配置线程池相关参数，具体参数配置可参考Hystrix官方文档。

**hystrix-provider服务**：模拟被请求的服务，提供一个耗时操作。

**hystrix-dashboard服务**：提供熔断监控服务
引入依赖：

```xml
<!--熔断监控服务-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```
启动类添加`@EnableHystrixDashboard`注解

然后可以通过 http://localhost:port/hystrix 看到hystrix页面，然后将 http://hystrix-app:port/actuator/hystrix.stream 填写到搜索框就可以看到该服务使用熔断接口的调用信息。

**hystrix-turbine服务**：聚合服务，由于实际生产环境，我们的服务不会只部署一台，肯定是以集群的形式部署，该服务的作用就是将多个服务器上部署的同一个服务聚合在一起，将聚合之后的结果展示在监控服务的控制面板上。

引入依赖，因为使用的是nacos注册中心，所以要排除掉eureka的依赖：

```xml
<!--熔断监控聚合多个服务-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-turbine</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </exclusion>
    </exclusions>
    <version>1.4.6.RELEASE</version>
</dependency>
```
启动类加入`@EnableTurbine`注解

在application.properties中配置需要聚合的服务

```properties
turbine.app-config=hystrix-consumer
```
然后就可以在hystrix的监控面板的搜索框中输入 http://turbine-hostname:port/turbine.stream 看到聚合之后服务接口的调用情况。
