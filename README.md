# my-ssm  
自己手写的简易ssm框架  
常用注解(Controller, Autowired,Request,事务)  
目录结构 
├── DispatchServlet.java ##启动servlet主要方法 
├── annotations ##注解 
│   ├── Autowired.java  
│   ├── Controller.java  
│   ├── RequestMapping.java  
│   ├── RequestParam.java  
│   ├── Service.java  
│   └── Transaction.java  
├── aspect ##aop主要方法  
│   ├── TransactionProxy.java  
│   ├── annotation  
│   │   └── Aspect.java  
│   └── proxy  
│       ├── AbstractAspectProxy.java  
│       ├── Proxy.java  
│       ├── ProxyChain.java  
│       └── ProxyManager.java  
├── config 
│   └── ConfigConstant.java  
├── controller  
│   ├── ControllerAspect.java  
│   └── UserController.java  
├── entity  
│   ├── RequestMethod.java  
│   └── User.java  
├── helper 帮助类  
│   ├── AopHelper.java  
│   ├── AopHelper.java  
│   ├── AopHelper.java  
│   ├── BeanHelper.java  
│   ├── ClassHelper.java  
│   ├── ConfigHelper.java  
│   ├── ControllerHelper.java  
│   ├── DatabaseHelper.java  
│   ├── HelperLoader.java  
│   └── IocHelper.java  
├── http controller动态代理类  
│   ├── ControllerInvoke.java  
│   ├── ControllerInvokeHandler.java  
│   └── InvokeEntity.java  
├── service  
│   └── UserService.java  
└── util 工具类  
    ├── CastUtil.java  
    ├── ClassUtil.java  
    ├── MapUtil.java  
    ├── PropsUtil.java  
    ├── ProxyUtil.java  
    └── ReflectionUtil.java  

