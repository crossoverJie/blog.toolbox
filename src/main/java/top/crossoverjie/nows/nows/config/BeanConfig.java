package top.crossoverjie.nows.nows.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.crossoverjie.nows.nows.filter.FilterProcess;
import top.crossoverjie.nows.nows.service.UploadPicService;
import top.crossoverjie.nows.nows.service.impl.fixpic.IgnorePrefixFilterProcess;
import top.crossoverjie.nows.nows.service.impl.fixpic.PicFilterProcess;
import top.crossoverjie.nows.nows.service.impl.totalsum.HttpFilterProcess;
import top.crossoverjie.nows.nows.service.impl.totalsum.WrapFilterProcess;

import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/10/25 22:14
 * @since JDK 1.8
 */
@Configuration
public class BeanConfig {

    @Value("${app.thread}")
    private int corePoolSize = 2;

    @Autowired
    private AppConfig appConfig;

    @Bean
    public ExecutorService sendMessageExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("task-%d").build();

        ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        return executor;
    }

    @Bean("httpFilterProcess")
    public FilterProcess httpFilterProcess() {
        return new HttpFilterProcess();
    }


    @Bean("numberFilterProcess")
    public FilterProcess numberFilterProcess() {
        return new WrapFilterProcess();
    }

    @Bean("picFilterProcess")
    public FilterProcess picFilterProcess() {
        return new PicFilterProcess();
    }

    @Bean("ignorePrefixFilterProcess")
    public FilterProcess ignorePrefixFilterProcess() {
        return new IgnorePrefixFilterProcess();
    }


    /**
     * http client
     *
     * @return okHttp
     */
    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }


    @Bean("uploadPicService")
    public UploadPicService buildUploadBean() throws Exception {
        ServiceLoader<UploadPicService> uploadPicServices = ServiceLoader.load(UploadPicService.class);
        for (UploadPicService picService : uploadPicServices) {
            return picService ;
        }

        return null ;
    }
}
