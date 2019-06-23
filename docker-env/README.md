# docker 搭建本地开发环境

## 安装docker
+ https://hub.docker.com/editions/community/docker-ce-desktop-mac

## docker
+ 创建并启动容器
```
docker run
```

+ 查看容器状态
```
docker container ls -a
```

+ 停止容器
```
docker stop
```


## mysql
+ 获取mysql
```
docker pull mysql:5.6.44
```

+ 启动一个mysql服务实例
```
# container名称为saturn-mysql
# root用户
# 主机3306端口映射到容器3306端口
# utf8mb4
docker run --name saturn-mysql -e MYSQL_ROOT_PASSWORD=root_pwd -p 3306:3306 -d mysql:5.6.44 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

+ 连接mysql实例
```
docker exec -it saturn-mysql bash
mysql -uroot -proot_pwd
```

+ 创建database
```
create database saturn_${project_name} default character set utf8mb4 collate utf8mb4_unicode_ci;
```

