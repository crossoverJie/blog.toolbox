package top.crossoverjie.nows.nows.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-05-07 19:12
 * @since JDK 1.8
 */
@Component
public class AppConfig {
    @Value("${app.model}")
    private String appModel ;

    @Value("${app.downLoad.path}")
    private String downLoadPath ;

    @Value("${app.pic.upload.way}")
    private String uploadWay ;

    public String getAppModel() {
        return appModel;
    }

    public String getDownLoadPath() {
        return downLoadPath;
    }

    public String getUploadWay() {
        return uploadWay;
    }
}
