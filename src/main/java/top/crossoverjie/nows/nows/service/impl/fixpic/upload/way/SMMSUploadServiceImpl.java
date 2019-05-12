package top.crossoverjie.nows.nows.service.impl.fixpic.upload.way;

import top.crossoverjie.nows.nows.service.UploadPicService;
import top.crossoverjie.nows.nows.util.DownloadUploadPic;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-05-12 15:01
 * @since JDK 1.8
 */
public class SMMSUploadServiceImpl implements UploadPicService {
    @Override
    public String upload(String path) throws Exception {
        String uploadAddress = DownloadUploadPic.upload(path,0);
        return uploadAddress ;
    }
}
