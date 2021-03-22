# Ignite 多数据库处理（服务端）

## 项目简述
* 使用ignite,将两个mysql数据库中的数据获取并放入缓存中。
* 数据库一 含有表T1， 数据库二 含有表T3

## 文件概述
* /sql T1、T3建表SQL
* /entities 包含T1、T3实体
* /store Ignite需要的CacheStore类，控制数据读入缓存或数据持久化
* PersonStoreServer Ignite启动类
* database.properties 数据库参数设置
* ignite-service-config.xml ignite配置文件

## 使用
* 数据库生成表T1、T3
* 修改database.properties文件，设置数据库
* 启动PersonStoreServer类

## 注意
* 本人使用Postgres数据库，如需使用MYSQL，请修改CacheStore对应的SQL语句。

## 配合使用
* 本人其他Ignite相关项目。
