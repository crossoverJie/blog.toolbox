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
        if (config.getAppModel().equals(BaseConstants.FIX_PIC)) {
            filterProcessManager = SpringBeanFactory.getBean(FixPicFilterProcessManager.class);
            resultService = SpringBeanFactory.getBean(PicResultServiceImpl.class);
            fileCount = Integer.parseInt(strings[1]);
            ((PicResultServiceImpl) resultService).setCurrentTime();
        } else {
            filterProcessManager = SpringBeanFactory.getBean(TotalSumFilterProcessManager.class);
            resultService = SpringBeanFactory.getBean(TotalSumResultServiceImpl.class);
            ((TotalSumResultServiceImpl) resultService).setCurrentTime();
        }

        Set<ScannerFile.FileInfo> allFile = scannerFile.getAllFile(strings[0]);
        logger.info("allFile size=[{}]", allFile.size());
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
