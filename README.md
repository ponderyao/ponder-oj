# ponder-oj
单模块 SpringBoot 项目的快速初始化模板

[![standard-readme compliant](https://img.shields.io/badge/JDK-1.8-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/SpringBoot-2.7.3-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/Maven-3.8.1-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/Redis-6.0-pink.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/MySQL-8.0-orange.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/Minio-8.5.2-yellow.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/knife4j-3.0.2-cyan.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/MybatisPlus-3.5.2-blue.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/Quartz-2.3.2-gold.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)

## 特性
### 研发框架
- SpringBoot 2.7.3
  - Spring MVC
  - Spring AOP
  - Spring Cache
  - Spring 全局事务
  - Spring 全局异常
- MybatisPlus 3.5.2
  - Repository/Mapper 仓储分离
  - Page 分页处理

### 数据存储
- Database: MySQL
- NoSQL/Cache: Redis
- File Storage: Minio

### 基础能力
- Spring Session 分布式登录
- Redis 缓存用户登录态信息
- Knife4j Swagger 接口文档
- MapStruct 对象转换器
- Quartz 动态定时任务（支持持久化）
- Validator 自定义参数校验（已支持正则/枚举校验）
- Repository 仓储层实现
- ThrowUtils 业务异常捕获
- 全局事务管理
- 全局异常处理
- 全局跨域配置
- 接口权限拦截与校验
- 统一成功与异常响应
- MD5+密码盐的加密策略

### 基本业务
- 用户服务
  - 用户注册
  - 用户登录
  - 编辑用户
  - 用户注销
  - 用户授权
  - 用户查询
- 文件服务
  - 文件上传
  - 文件下载
- 定时任务服务
  - 创建定时任务
  - 暂停定时任务
  - 恢复定时任务
  - 修改定时任务
  - 删除定时任务
  - 查询定时任务状态
  - 查询所有定时任务

## 快速上手
### 环境准备
1. MySQL
创建数据库，执行 `sql` 目录下的 `default_models.sql` 、 `quartz_job.sql` 脚本
2. Redis
搭建Redis服务，建议基于云服务器docker部署
3. Minio
搭建Minio服务，建议基于云服务器docker部署，设置存储桶并设置public公网可读权限
### 完善配置
按提示填充 `application-dev.yml` 中需配置的信息
```yaml
spring:
  # 数据库配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    # todo 设置mysql连接地址
    url: jdbc:mysql://localhost:3306/my_db?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8
    # todo 设置mysql账号
    username: root
    # todo 设置mysql密码
    password: 103123Yzp
  # Redis 配置
  redis:
    # todo 设置redis主机地址
    host: localhost
    port: 6379
    database: 2
    # todo 设置redis密码
    password: 123456
    timeout: 5000

minio:
  # todo 设置minio主机地址与端口号
  endpoint: http://localhost:9090/
  # todo 设置minio访问key
  accessKey: minioadmin
  # todo 设置minio密钥key
  secretKey: minioadmin
  # todo 设置minio存储桶
  bucket: dev
```
### 启动服务
启动服务，在浏览器访问 `http://localhost:8080/api/doc.html` 即可打开 swagger 接口文档与在线调试平台
### 使用定时任务
通过定时任务接口动态的管理定时任务，定时任务所在类与方法需提前实现
以创建定时任务为例，入参如下：
```json
{
  "jobName": "userJob",
  "jobGroup": "removeBanJobMonthAgo",
  "jobDesc": "删除一个月前被禁的用户",
  "cron": "0 0 0/1 * * ?"
}
```
其中， `jobGroup` 代表任务所在类，`jobName` 代表任务方法