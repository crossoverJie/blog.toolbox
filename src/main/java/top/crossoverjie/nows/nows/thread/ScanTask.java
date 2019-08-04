package top.crossoverjie.nows.nows.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.crossoverjie.nows.nows.config.AppConfig;
import top.crossoverjie.nows.nows.constants.BaseConstants;
import top.crossoverjie.nows.nows.filter.AbstractFilterProcess;
import top.crossoverjie.nows.nows.service.UploadPicService;
import top.crossoverjie.nows.nows.util.DownloadUploadPic;
import top.crossoverjie.nows.nows.util.SpringBeanFactory;

import java.io.*;
import java.net.HttpRetryException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/10/25 23:55
 * @since JDK 1.8
 */
public class ScanTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ScanTask.class);

    private String path;

    private AbstractFilterProcess filterProcessManager;

    private AppConfig appConfig;

    private UploadPicService uploadPicService;

    public ScanTask(String path, AbstractFilterProcess filterProcessManager) {
        this.path = path;
        this.filterProcessManager = filterProcessManager;
        this.appConfig = SpringBeanFactory.getBean(AppConfig.class);
        uploadPicService = (UploadPicService) SpringBeanFactory.getBean("uploadPicService");
    }

    @Override
    public void run() {
        Stream<String> stringStream = null;
        try {
            stringStream = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("IOException", e);
        }

        // 如果是统计字数
        if (appConfig.getAppModel().equals(BaseConstants.TOTAL_WORDS)) {
            List<String> collect = stringStream.collect(Collectors.toList());
            for (String msg : collect) {
                filterProcessManager.process(msg);
            }

        } else {
            // 下载旧图及上传新图
            Map<String, String> picMapping = downUpPic(stringStream, path);

            // 替换本地文本
            if (picMapping.size() > 0) {
                replacePic(path, picMapping);
            }

        }
    }

    /**
     * 下载和上传图片
     *
     * @param stringStream
     * @return
     */
    private Map<String, String> downUpPic(Stream<String> stringStream, String filePath) {
        // 文件前缀
        int index = filePath.lastIndexOf(System.getProperty("file.separator"));
        filePath = filePath.substring(index + 1);

        Map<String, String> picMapping = new HashMap<>(16);

        List<String> pics = new ArrayList<>(10);

        List<String> collect = stringStream.collect(Collectors.toList());
        int i = 0;
        for (String msg : collect) {
            String picAddress = filterProcessManager.process(msg);
            if (picAddress != null) {
                pics.add(picAddress);
            }

            // 如果文章中有 english_title 默认使用 english_title 作为部分图片名，避免图片名含有中文
            if (i == 0 && msg.contains("english_title:")) {
                filePath = filePath.substring(0, 8) + "-" + msg.substring(15).toLowerCase();
                i++;
            }
        }

        for (String pic : pics) {
            String path = appConfig.getDownLoadPath() + "/" + filePath + "---" + pic.substring(pic.lastIndexOf("/") + 1);
            try {

                File file = new File(path);
                if (file.exists()) {
                    logger.info("[{}] 已下载完毕，地址 = [{}]", pic, path);
                } else {
                    DownloadUploadPic.download(pic, path);
                    logger.info("下载图片 [{}] 成功，地址 = [{}]", pic, path);
                }
            } catch (HttpRetryException e) {
                logger.error("下载图片失败，url = [{}]，HTTP状态码为 [{}]，请检查 URL！", pic, e.responseCode());
                path = "error";
            } catch (IOException e) {
                logger.error("下载图片失败，url = [{}]，请检查URL！", pic, e);
                path = "error";
            }

            // 只做备份，不做上传
            if (appConfig.getAppModel().equals(BaseConstants.FixPic.BACK_UP_MODEL)) {
                continue;
            }

            try {
                if (path.equals("error")) {
                    continue;
                }

                String uploadAddress = uploadPicService.upload(path);
                if (uploadAddress == null) {
                    logger.error("上传图片失败，跳过 fileName = [{}]", path);
                    continue;
                }

                logger.info("【上传 [{}] 图片成功，地址=[{}]】", pic, uploadAddress);
                picMapping.put(pic, uploadAddress);
            } catch (Exception e) {
                logger.error("上传图片失败，fileName=  [{}]", path);
            }

        }

        return picMapping;

    }

    /**
     * 替换本地文本
     *
     * @param path
     * @param picMapping
     */
    private void replacePic(String path, Map<String, String> picMapping) {
        if (appConfig.getAppModel().equals(BaseConstants.FixPic.BACK_UP_MODEL)) {
            return;
        }

        for (Map.Entry<String, String> pic : picMapping.entrySet()) {
            String oldPic = pic.getKey();
            String newPic = pic.getValue();

            FileWriter out = null;
            FileReader in = null;
            BufferedReader bufIn = null;
            try {
                // 读
                File file = new File(path);

                in = new FileReader(file);
                bufIn = new BufferedReader(in);

                // 内存流, 作为临时流
                CharArrayWriter tempStream = new CharArrayWriter();

                // 替换
                String line = null;

                while ((line = bufIn.readLine()) != null) {
                    // 替换每行中, 符合条件的字符串
                    line = line.replaceAll(oldPic, newPic);
                    // 将该行写入内存
                    tempStream.write(line);
                    // 添加换行符
                    tempStream.append(System.getProperty("line.separator"));
                }

                // 将内存中的流 写入 文件
                out = new FileWriter(file);
                tempStream.writeTo(out);
            } catch (Exception e) {
                logger.error("替换内容失败，源 = [{}]，目标 = [{}]", oldPic, newPic, e);
            } finally {
                try {
                    out.close();
                    bufIn.close();
                    in.close();
                } catch (IOException e) {
                    logger.error("替换文件关闭流失败，IOException", e);
                }

            }
        }

        logger.info("替换 [{}] 成功", path);
    }

}
