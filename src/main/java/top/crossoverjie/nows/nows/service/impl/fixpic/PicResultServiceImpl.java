package top.crossoverjie.nows.nows.service.impl.fixpic;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PicResultServiceImpl implements ResultService {

    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = new ThreadLocal<>();

    public void setCurrentTime() {
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }

    @Override
    public void end() {
        long end = System.currentTimeMillis();
        log.info("任务完成，耗时 [{}] ms", end - TIME_THREAD_LOCAL.get());
    }
}
