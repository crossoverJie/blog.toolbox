package top.crossoverjie.nows.nows.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-05-07 23:05
 * @since JDK 1.8
 */
@Getter
@Setter
public class SMResponse {

    /**
     * code : success
     * data : {"width":1920,"height":1080,"filename":"image.jpg","storename":"5cd19e6187091.jpg","size":295117,"path":"/2019/05/07/5cd19e6187091.jpg","hash":"dqiLgM7lQ4CRpZ5","timestamp":1557241441,"ip":"125.80.135.62","url":"https://i.loli.net/2019/05/07/5cd19e6187091.jpg","delete":"https://sm.ms/delete/dqiLgM7lQ4CRpZ5"}
     */

    private String code;
    private DataBean data;

    @Getter
    @Setter
    public static class DataBean {
        /**
         * width : 1920
         * height : 1080
         * filename : image.jpg
         * storename : 5cd19e6187091.jpg
         * size : 295117
         * path : /2019/05/07/5cd19e6187091.jpg
         * hash : dqiLgM7lQ4CRpZ5
         * timestamp : 1557241441
         * ip : 125.80.135.62
         * url : https://i.loli.net/2019/05/07/5cd19e6187091.jpg
         * delete : https://sm.ms/delete/dqiLgM7lQ4CRpZ5
         */

        private int width;
        private int height;
        private String filename;
        private String storename;
        private int size;
        private String path;
        private String hash;
        private int timestamp;
        private String ip;
        private String url;
        private String delete;
    }
}
