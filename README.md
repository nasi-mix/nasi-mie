## Nasi Mie

管理界面，需要配合 nasi-eureka 和nasi-campur 使用


使用前需要导入sql文件

## 必需软件

- Java 1.8 +
- Redis
- Mysql

## Redis

```bash
docker run --name redis -p 6379:6379 -d redis
```

## 快速开始

```java
mvn clean install
./bootstrap.sh start
```
