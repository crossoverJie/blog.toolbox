package top.crossoverjie.nows.nows.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.crossoverjie.nows.nows.impl.TotalWords;

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
public class FilterProcessManager {

    @Autowired
    private TotalWords totalWords;

    @Resource(name = "numberFilterProcess")
    private FilterProcess numberFilterProcess;

    @Resource(name = "httpFilterProcess")
    private FilterProcess httpFilterProcess;

    private List<FilterProcess> filterProcesses = new ArrayList<>(10);

    @PostConstruct
    public void start() {
        this.addProcess(numberFilterProcess)
                .addProcess(httpFilterProcess);
    }

    public FilterProcessManager addProcess(FilterProcess process) {
        filterProcesses.add(process);
        return this;
    }


    /**
     * 处理
     * @param msg
     */
    public void process(String msg) {
        for (FilterProcess filterProcess : filterProcesses) {
            msg = filterProcess.process(msg);
        }
        totalWords.sum(msg.toCharArray().length);
    }
}
