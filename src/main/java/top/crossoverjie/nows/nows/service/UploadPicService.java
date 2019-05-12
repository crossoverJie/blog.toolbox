package top.crossoverjie.nows.nows.service;

/**
 * Function:上传图片服务
 *
 * @author crossoverJie
 * Date: 2019-05-12 14:58
 * @since JDK 1.8
 */
public interface UploadPicService {

    /**
     * 上传图片
     * @param path 图片下载后的本地地址
     * @return
     * @throws Exception
     */
    String upload(String path) throws Exception;
}
