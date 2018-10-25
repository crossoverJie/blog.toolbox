package top.crossoverjie.nows.nows.filter;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/25 18:15
 * @since JDK 1.8
 */
public interface FilterProcess {

    /**
     * 处理文本
     * @param msg
     * @return
     */
    String process(String msg) ;
}
