# 博客工具箱

## 批量替换图床

### 使用

![](https://i.loli.net/2019/05/08/5cd1cc7612c25.gif)

![](https://i.loli.net/2019/05/08/5cd1cd6062d2a.png)

![](https://ws3.sinaimg.cn/large/006tNc79ly1g2ylkf37wyj30r10ion3f.jpg)

```shell
git clone https://github.com/crossoverJie/blog.toolbox
mvn -Dmaven.test.skip=true clean package
java -jar blog.toolbox-0.0.2.jar /xx/xx/path 100 --app.downLoad.path=/xx/img
```



程序会自动扫描 `/xx/xx/path` 目录下以 `.md` 为后缀的 `Markdown` 文件，将其中的图片下载到本地目录 `/xx/img` 保存；同时默认会上传到 `SM.MS` 图床并替换原有的图片链接。

- `app.downLoad.path` 是用于将下载的图片保存到本地磁盘的目录。
- `/xx/xx/path` 则是扫描 `.md` 文件的目录，会递归扫描所有出所有文件。
- 100 则是需要替换文件的数量，默认是按照文件修改时间排序。

### 自定义上传策略

内置上传到免费图床 `SM.MS` 大家也可自定义上传地址，只需要实现 `top.crossoverjie.nows.nows.service.UploadPicService`，如下所示：

```java
public class TestUploadServiceImpl implements UploadPicService{

    @Override
    public String upload(String path) throws Exception {
        return "https://crossoverjie.top/uploads/crossoverjie.jpg";
    }
}
```

返回一个上传地址即可；同时在启动应用时指定其上传策略：

```java
java -jar blog.toolbox-0.0.2.jar /xxx/path 10 --app.model=2 --app.pic.upload.way=top.crossoverjie.nows.nows.service.impl.fixpic.upload.way.TestUploadServiceImpl
```

### 备份模式

该模式用于备份博客中的图片到本地，以防丢失。

```java
java -jar blog.toolbox-0.0.2.jar /xxx/path 200 --app.model=3 --app.downLoad.path=/backup/
```

## 字数统计
> 统计你所写的博客字数，仅支持 Markdown 文件。


# 使用

```shell
git clone https://github.com/crossoverJie/blog.toolbox
mvn -Dmaven.test.skip=true clean package
java -jar blog.toolbox-0.0.2.jar /xx/xx/path
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