# Nasi 

一款基于docker于spring cloud 构成的集合

- Nasi
    - nasi-mie
    - nasi-campur
    - nasi-eureka


## Nasi Mie

Nasi Mie 是一款 `nasi-campur` 界面管理软件


## Nasi Campur

Nasi Campur 是一款 提供镜像微服务的模块

## Nasi Eureka

Nasi Eureka 是一款发现nasi 微服务的框架

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
