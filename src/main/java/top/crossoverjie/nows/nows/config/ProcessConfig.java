package top.crossoverjie.nows.nows.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.crossoverjie.nows.nows.filter.FilterProcess;
import top.crossoverjie.nows.nows.impl.HttpFilterProcess;
import top.crossoverjie.nows.nows.impl.WrapFilterProcess;

import java.util.concurrent.*;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/25 22:14
 * @since JDK 1.8
 */
@Configuration
public class ProcessConfig {

    private int corePoolSize = 4;

    private int maxPoolSize = 4;

    @Bean
    public ExecutorService sendMessageExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("scan-number-%d").build();

        ExecutorService executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        return executor;
    }

    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private int queueCapacity = 256;

    @Bean("httpFilterProcess")
    public FilterProcess httpFilterProcess() {
        return new HttpFilterProcess();
    }


    @Bean("numberFilterProcess")
    public FilterProcess numberFilterProcess() {
        return new WrapFilterProcess();
    }


}
