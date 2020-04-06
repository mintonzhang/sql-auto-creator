# sql-auto-creator

它是一款使用openJFX(javaFX) 编写的一款可以将DB设计转换为sql语句的小工具。打包完成之后为可运行jar，可以在安装了JDK的机器上运行。



## 运行要求

1. JDK版本大于1.8
2. JAVA_HOME需要在环境变量中配置

## 版本更新日志[全部版本](https://github.com/mintonzhang/sql-auto-creator/tree/sql-auto-creator/jars/)

### #1.0版本

1. 提供模板下载功能，然后将需要创建的字段按照要求进行填写到excel中(Excel可以保存为项目所需要的DB设计)
2. 然后打开双击jar包(前提是已经配置了环境变量)否则需要自行使用命令启动 启动命令为 java -jar xxxx
3. 目前只支持mysql数据库。