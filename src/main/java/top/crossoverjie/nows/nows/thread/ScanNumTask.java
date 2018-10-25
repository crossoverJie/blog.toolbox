package top.crossoverjie.nows.nows.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.crossoverjie.nows.nows.filter.FilterProcessManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/25 23:55
 * @since JDK 1.8
 */
public class ScanNumTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ScanNumTask.class);

    private String path;

    private FilterProcessManager filterProcessManager;

    public ScanNumTask(String path, FilterProcessManager filterProcessManager) {
        this.path = path;
        this.filterProcessManager = filterProcessManager;
    }

    @Override
    public void run() {
        Stream<String> stringStream = null;
        try {
            stringStream = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("IOException", e);
        }

        List<String> collect = stringStream.collect(Collectors.toList());
        for (String msg : collect) {
            filterProcessManager.process(msg);
        }
    }
}
