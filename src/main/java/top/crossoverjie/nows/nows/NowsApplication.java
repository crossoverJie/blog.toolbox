package top.crossoverjie.nows.nows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.crossoverjie.nows.nows.config.AppConfig;
import top.crossoverjie.nows.nows.constants.BaseConstants;
import top.crossoverjie.nows.nows.filter.AbstractFilterProcess;
import top.crossoverjie.nows.nows.filter.FixPicFilterProcessManager;
import top.crossoverjie.nows.nows.filter.TotalSumFilterProcessManager;
import top.crossoverjie.nows.nows.scan.ScannerFile;
import top.crossoverjie.nows.nows.service.ResultService;
import top.crossoverjie.nows.nows.service.impl.fixpic.PicResultServiceImpl;
import top.crossoverjie.nows.nows.service.impl.totalsum.TotalSumResultServiceImpl;
import top.crossoverjie.nows.nows.thread.ScanTask;
import top.crossoverjie.nows.nows.util.SpringBeanFactory;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 替换模式执行顺序：
 * 1. 遍历目录下所有文章（一个文章一个任务）
 * 2. 遍历查找当前文章的每一行，看是否有图片链接，有就返回，没有返回 null
 * 3. 如果链接为需要过滤链接，过滤掉，否则加入待下载 list
 * 4. 依次下载当前文章需要下载的每个图片链接，如果图片在本地已经存在（原来下载了，没有上传成功，或者没有替换成功，文章中依旧是原图片链接），跳过下载
 * 5. 但还是上传到图床，因为此图片链接还是原图片链接，新图片链接会被过滤
 * 6. 上传成功，将原图片链接与新图床链接映射
 * 7. 替换当前文章的所有原图片链接
 * 8. 循环（执行第二个任务）
 *
 * 备份模式只有1、2、3、4
 */
@SpringBootApplication
public class NowsApplication implements CommandLineRunner {


    private static Logger logger = LoggerFactory.getLogger(NowsApplication.class);

    private AbstractFilterProcess filterProcessManager;

    private ResultService resultService;

    /**
     * 要处理的文件数量
     */
    private int fileCount = 0;

    @Autowired
    private AppConfig config;

    @Autowired
    private ScannerFile scannerFile;

    @Autowired
    private ExecutorService executorService;


    public static void main(String[] args) {
        SpringApplication.run(NowsApplication.class, args);

    }

    @Override
    public void run(String... strings) throws Exception {

        if (strings.length == 1) {
            fileCount = Integer.parseInt(strings[0]);
        }

        if (config.getAppModel().equals(BaseConstants.TOTAL_WORDS)) {
            filterProcessManager = SpringBeanFactory.getBean(TotalSumFilterProcessManager.class);
            resultService = SpringBeanFactory.getBean(TotalSumResultServiceImpl.class);
            ((TotalSumResultServiceImpl) resultService).setCurrentTime();

        } else {
            filterProcessManager = SpringBeanFactory.getBean(FixPicFilterProcessManager.class);
            resultService = SpringBeanFactory.getBean(PicResultServiceImpl.class);
            fileCount = Integer.parseInt(strings[1]);
            ((PicResultServiceImpl) resultService).setCurrentTime();
        }

        Set<ScannerFile.FileInfo> allFile = scannerFile.getAllFile(strings[0]);
        logger.info("allFile size = [{}]", allFile.size());
        if (fileCount > allFile.size()) {
            fileCount = allFile.size();
        }

        int flag = 0;
        for (ScannerFile.FileInfo msg : allFile) {
            executorService.execute(new ScanTask(msg.getFilePath(), filterProcessManager));
            flag++;
            if (flag == fileCount) {
                break;
            }
        }

        executorService.shutdown();
        while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            //logger.info("worker running");
        }

        resultService.end();


    }
}
