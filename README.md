## Nasi Mie [![Build Status](https://travis-ci.org/nasi-mix/nasi-mie.svg?branch=master)](https://travis-ci.org/nasi-mix/nasi-mie)

Control interface, need to use nasi-eureka  and nasi-campur


## Pre-require

- Java 1.8 +
- Mysql
- Redis


## Mysql + phpmyadmin
```bash
docker run -d -h mysql -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root mysql:5.6
docker run --name phpadmin --link mysql:db -p 9998:80 -d phpmyadmin/phpmyadmin
#docker exec -it phpadmin vi /www/libraries/config.default.php
#$cfg['AllowThirdPartyFraming'] = true; 嵌入其他页面
```

## Redis
```bash
docker run --name redis -p 6379:6379 -d redis
```

## 快速开始

```java
mvn clean install
./bootstrap.sh start
```
