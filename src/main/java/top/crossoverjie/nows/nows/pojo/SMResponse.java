package top.crossoverjie.nows.nows.pojo;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-05-07 23:05
 * @since JDK 1.8
 */
public class SMResponse {

    /**
     * code : success
     * data : {"width":1920,"height":1080,"filename":"image.jpg","storename":"5cd19e6187091.jpg","size":295117,"path":"/2019/05/07/5cd19e6187091.jpg","hash":"dqiLgM7lQ4CRpZ5","timestamp":1557241441,"ip":"125.80.135.62","url":"https://i.loli.net/2019/05/07/5cd19e6187091.jpg","delete":"https://sm.ms/delete/dqiLgM7lQ4CRpZ5"}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = delete;
        }
    }
}
