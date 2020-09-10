package top.crossoverjie.nows.nows.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.crossoverjie.nows.nows.config.AppConfig;
import top.crossoverjie.nows.nows.constants.BaseConstants;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Function: 替换图片
 *
 * @author crossoverJie
 * Date: 2019/05/05 23:53
 * @since JDK 1.8
 */
@Service
public class FixPicFilterProcessManager extends AbstractFilterProcess {

    @Resource(name = "picFilterProcess")
    private FilterProcess picFilterProcess;

    @Resource(name = "ignorePrefixFilterProcess")
    private FilterProcess ignorePrefixFilterProcess;

    private List<FilterProcess> filterProcesses = new ArrayList<>(4);

    @Autowired
    private AppConfig config;

    @PostConstruct
    @Override
    public void start() {
        this.addProcess(picFilterProcess);

        // 非备份模式下才要过滤图片
        if (!config.getAppModel().equals(BaseConstants.FixPic.BACK_UP_MODEL)) {
            this.addProcess(ignorePrefixFilterProcess);
        }
    }

    @Override
    public AbstractFilterProcess addProcess(FilterProcess process) {
        filterProcesses.add(process);
        return this;
    }


    /**
     * 处理
     *
     * @param msg
     */
    @Override
    public String process(String msg) {

        for (FilterProcess filterProcess : filterProcesses) {
            msg = filterProcess.process(msg);
        }

        return msg;
    }
}
