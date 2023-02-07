package top.crossoverjie.nows.nows.service.impl.fixpic;

import top.crossoverjie.nows.nows.filter.FilterProcess;

/**
 * Function:过滤掉指定图床图片
 *
 * @author crossoverJie
 * Date: 2019-05-08 00:43
 * @since JDK 1.8
 */
public class IgnorePrefixFilterProcess implements FilterProcess {
    @Override
    public String process(String msg) {
        if (msg != null && (msg.startsWith("https://ws") || msg.startsWith("http://ww") || msg.startsWith("http://wx") || msg.startsWith("https://tva1")  || msg.startsWith("https://p.ipic")) ){
            return msg ;
        }
        return null;
    }
}
