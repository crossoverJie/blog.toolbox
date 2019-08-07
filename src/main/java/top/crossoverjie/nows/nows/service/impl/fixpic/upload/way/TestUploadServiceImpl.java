package top.crossoverjie.nows.nows.service.impl.fixpic.upload.way;

import top.crossoverjie.nows.nows.service.UploadPicService;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-05-12 15:35
 * @since JDK 1.8
 */
public class TestUploadServiceImpl implements UploadPicService {

    @Override
    public String upload(String path) throws Exception {
        return "https://crossoverjie.top/uploads/crossoverjie.jpg";
    }
}
