# 博客工具箱

## 批量替换图床

### 使用

```shell
git clone https://github.com/crossoverJie/blog.toolbox
mvn clean package
java -jar nows-0.0.1-SNAPSHOT.jar --app.downLoad.path=/xx/img /xx/xx/path 100
```


程序员会自动扫描 `/xx/xx/path` 目录下以 `.md` 为后缀的 `Markdown` 文件，将其中的图片下载到本地目录 `/xx/img` 保存；同时默认会上传到 `SM.MS` 图床并替换原有的图片链接。


## 字数统计
> **Number Of Words**。统计你所写的博客字数，仅支持 Markdown 文件。


# 使用

```shell
git clone https://github.com/crossoverJie/blog.toolbox
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