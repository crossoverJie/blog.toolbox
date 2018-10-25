package top.crossoverjie.nows.nows.impl;

import top.crossoverjie.nows.nows.filter.FilterProcess;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/25 18:52
 * @since JDK 1.8
 */
public class NumberFilterProcess implements FilterProcess{


    @Override
    public String process(String msg) {

        msg = msg.replaceAll("\\s*", "");

        return msg ;
    }
}
