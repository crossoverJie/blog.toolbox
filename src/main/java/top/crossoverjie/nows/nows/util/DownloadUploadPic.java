package top.crossoverjie.nows.nows.util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.crossoverjie.nows.nows.pojo.SMResponse;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

/**
 * Function:图片下载
 *
 * @author crossoverJie
 * Date: 2019-05-07 00:14
 * @since JDK 1.8
 */
public class DownloadUploadPic {

    private static Logger logger = LoggerFactory.getLogger(DownloadUploadPic.class);

    private static OkHttpClient httpClient ;

    static {
        httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static void download(String urlString, String fileName) throws IOException {
        File file = new File(fileName) ;
        if (file.exists()){
            logger.info("[{}]已下载完毕",fileName);
            return;
        }
        URL url = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            url = new URL(urlString);
            URLConnection con = url.openConnection();
            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            os = new FileOutputStream(fileName);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (os != null){
                os.close();
            }
            if (is != null){
                is.close();
            }
        }

    }



    public static String upload(String fileName,int errorTime) throws IOException, InterruptedException {

        if (errorTime == 5){
            logger.error("[{}]上传失败次数达到上限{}次",fileName,errorTime);
            return null;
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File(fileName));

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("smfile", fileName, fileBody)
                .build();

        Request request = new Request.Builder()
                .url("https://sm.ms/api/v2/upload")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
                .header("Authorization", "")
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute() ;
        if (response.isSuccessful()){
            ResponseBody body = response.body();
            try {
                SMResponse smResponse = JSON.parseObject(body.string(), SMResponse.class);
                return smResponse.getData().getUrl();
            }catch (Exception e){
                logger.error("上传图片[{}]失败 res=[{}]",fileName,body.string());
                errorTime ++;
                TimeUnit.SECONDS.sleep(1);
                return upload(fileName, errorTime);
            }finally {
                body.close();
            }
        }
        return null ;
    }
}

