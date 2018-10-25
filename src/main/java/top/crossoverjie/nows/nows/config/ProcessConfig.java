package top.crossoverjie.nows.nows.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.crossoverjie.nows.nows.filter.FilterProcess;
import top.crossoverjie.nows.nows.impl.HttpFilterProcess;
import top.crossoverjie.nows.nows.impl.NumberFilterProcess;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/25 22:14
 * @since JDK 1.8
 */
@Configuration
public class ProcessConfig {

    @Bean("httpFilterProcess")
    public FilterProcess httpFilterProcess(){
        return  new HttpFilterProcess();
    }
    @Bean("numberFilterProcess")
    public FilterProcess numberFilterProcess(){
        return  new NumberFilterProcess();
    }


}
