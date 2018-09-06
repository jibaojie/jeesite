# jeesite

参考其他项目搭建的一个系统，项目名字随便取的，仅供自己学习使用的，只做了后端，通过swagger-ui测试，没开发前端。。

参考项目
-   [jeesite](https://github.com/thinkgem/jeesite)
-   [guns](https://github.com/abel533/guns)
-   [abel533](https://github.com/abel533)的开源项目
-   [mcg-helper](https://github.com/mcg-helper/mcg-helper) 
-   [weixin-java-tools](https://github.com/Wechat-Group/weixin-java-tools) 等其他项目

# 模块说明
    mcg-helper: 代码生产模块，启动项目，配置数据源，表等可以直接生产dao,service,controller等代码，可自定义，比较灵活
    jeesite-entity：存放实体类，供其他项目调用
    jeesite-util: 工具类
    jeesite-common: 公用的一些模块，如日志等web项目都会使用的模块
    jeesite-login: 登录模块，web模块需要登录可加该项目依赖，统一权限处理
    jeesite-web: 提供api的模块，项目可以有多个web模块，需要权限相关可添加login模块依赖
    jeesite-weixin: 支持多公众号 企业号 支付 小程序
    
 #项目技术
 
-   springboot 2.0.3   
-   mybatis，通用mapper，分页插件pagehelper
-   shiro 1.4.0
-   swagger 2.6.1
-   quartz 2.3.0

#主要功能


 