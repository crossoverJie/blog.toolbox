package top.crossoverjie.nows.nows.service.impl.fixpic;

import top.crossoverjie.nows.nows.config.AppConfig;
import top.crossoverjie.nows.nows.filter.FilterProcess;
import top.crossoverjie.nows.nows.util.SpringBeanFactory;

/**
 * Function: 过滤掉指定图床图片
 *
 * @author crossoverJie
 * Date: 2019-05-08 00:43
 * @since JDK 1.8
 */
public class IgnorePrefixFilterProcess implements FilterProcess {

    @Override
    public String process(String msg) {
        // 如果文章中图片已经是图床地址（filterUrl），避免重复上传，所以过滤掉（return null）
        if (msg != null && !msg.startsWith(SpringBeanFactory.getBean(AppConfig.class).getFilterUrl())) {
            return msg;
        }
        return null;
    }
}
