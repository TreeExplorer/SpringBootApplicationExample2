## SpringBootApplicationExample

> - 这是一个 SpringBoot 框架的后端程序【注意：没有前端】
> - 代码没有添加很多东西，我觉得还是比较简洁的，比较容易看懂
> - 写了两个类的增删改查，还有一个文件上传
> - 其它的功能在 config 包下有对应的类，task 包中写了一个定时任务 demo
> - 运行环境：Java 8、Maven 3.6

> - 读代码先看一下项目整体结构和项目及程序的配置文件
> - 再看一下项目中的 example_spring_boot.sql 数据库脚本文件
> - 最后就是从 controller 层，找几个功能往下层看就好









<!-- 
所有代码规范应按照阿里巴巴开发手册执行
- DO（Data Object）：此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
- DTO（Data Transfer Object）：数据传输对象，Service 或 Manager 向外传输的对象。
- BO（Business Object）：业务对象，由 Service 层输出的封装业务逻辑的对象。
- AO（Application Object）：应用对象，在 Web 层与 Service 层之间抽象的复用对象模型，
极为贴近展示层，复用度不高。
- VO（View Object）：显示层对象，通常是 Web 向模板渲染引擎层传输的对象。 
- Query：数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用 Map 类来传输。
-->


