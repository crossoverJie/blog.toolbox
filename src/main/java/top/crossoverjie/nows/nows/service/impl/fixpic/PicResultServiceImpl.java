package top.crossoverjie.nows.nows.service.impl.fixpic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.crossoverjie.nows.nows.service.ResultService;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-05-06 21:18
 * @since JDK 1.8
 */
@Service
public class PicResultServiceImpl implements ResultService {
    private static Logger logger = LoggerFactory.getLogger(PicResultServiceImpl.class);

    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = new ThreadLocal<>();

    public void setCurrentTime() {
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }
    @Override
    public void end() {
        long end = System.currentTimeMillis();
        logger.info("任务完成，耗时[{}] ms", end - TIME_THREAD_LOCAL.get());
    }
}
