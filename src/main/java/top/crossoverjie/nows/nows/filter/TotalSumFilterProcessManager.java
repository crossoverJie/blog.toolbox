package top.crossoverjie.nows.nows.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.crossoverjie.nows.nows.service.impl.totalsum.TotalWords;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/25 18:53
 * @since JDK 1.8
 */
@Service
public class TotalSumFilterProcessManager extends AbstractFilterProcess{
    private static Logger logger = LoggerFactory.getLogger(TotalSumFilterProcessManager.class);
    @Autowired
    private TotalWords totalWords;

    @Resource(name = "numberFilterProcess")
    private FilterProcess numberFilterProcess;

    @Resource(name = "httpFilterProcess")
    private FilterProcess httpFilterProcess;

    private List<FilterProcess> filterProcesses = new ArrayList<>(10);


    @PostConstruct
    @Override
    public void start() {
        this.addProcess(numberFilterProcess)
                .addProcess(httpFilterProcess);
    }

    @Override
    public AbstractFilterProcess addProcess(FilterProcess process) {
        filterProcesses.add(process);
        return this;
    }


    /**
     * 处理
     * @param msg
     */
    @Override
    public String process(String msg) {
        for (FilterProcess filterProcess : filterProcesses) {
            msg = filterProcess.process(msg);
        }
        totalWords.sum(msg.toCharArray().length);
        //logger.info("统计字数任务");
        return msg;
    }




}
