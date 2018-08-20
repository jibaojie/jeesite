# jeesite
参考其他项目搭建的一个系统，项目名字随便取的。

    原jeesite4项目：https://github.com/thinkgem/jeesite4
    mcg-helper项目：https://github.com/mcg-helper/mcg-helper

# 模块说明
    mcg-helper: 代码生产模块，启动项目，配置数据源，表等可以直接生产dao,service,controller等代码，可自定义，比较灵活
    jeesite-entity：存放实体类，供其他项目调用
    jeesite-util: 工具类
    jeesite-common: 公用的一些模块，如日志等web项目都会使用的模块
    jeesite-job: 定时任务模块，考虑到有些项目可能不会有定时任务，就拿出来了，放在其他模块也一样
    jeesite-login: 登录模块，web模块需要登录可加该项目依赖，统一权限处理
    jeesite-web: 提供api的模块，项目可以有多个web模块，需要权限相关可添加login模块依赖