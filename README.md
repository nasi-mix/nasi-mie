## Nasi Mie

管理界面，需要配合 nasi-eureka 和nasi-campur 使用


使用前需要导入sql文件

## 必需软件

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
