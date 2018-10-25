package top.crossoverjie.nows.nows.impl;

import top.crossoverjie.nows.nows.filter.FilterProcess;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/25 18:52
 * @since JDK 1.8
 */
public class HttpFilterProcess implements FilterProcess{


    @Override
    public String process(String msg) {

        msg = msg.replaceAll("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+","");

        return msg ;
    }
}
