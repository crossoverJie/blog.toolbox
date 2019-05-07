package top.crossoverjie.nows.nows.service.impl.fixpic;

import top.crossoverjie.nows.nows.filter.FilterProcess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Function:图片过滤器
 *
 * @author crossoverJie
 * Date: 2019-05-05 23:13
 * @since JDK 1.8
 */
public class PicFilterProcess implements FilterProcess {

    private String pattern = "https?://.+\\.(jpg|gif|png)";

    @Override
    public String process(String msg) {

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(msg);
        while (m.find()) {
            return m.group();
        }

        return null;
    }
}
