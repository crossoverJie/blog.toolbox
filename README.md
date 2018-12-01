# NOWS

> **Number Of Words**。统计你所写的博客字数，仅支持 Markdown 文件。


# 使用

```shell
git clone https://github.com/crossoverJie/NOWS.git
mvn clean package
java -jar nows-0.0.1-SNAPSHOT.jar /xx/xx/path
```

![](https://ws2.sinaimg.cn/large/006tNbRwly1fwlc5yrsmnj31kw09vqv5.jpg)

# 扩展

采用责任链模式可自定义统计策略。

```java
    @Bean("httpFilterProcess")
    public FilterProcess httpFilterProcess() {
        return new HttpFilterProcess();
    }


    @Bean("numberFilterProcess")
    public FilterProcess numberFilterProcess() {
        return new NumberFilterProcess();
    }
    
    @PostConstruct
    public void start() {
        this.addProcess(numberFilterProcess)
                .addProcess(httpFilterProcess);
    }
```

# 性能

利用多线程读取效率明显提升。