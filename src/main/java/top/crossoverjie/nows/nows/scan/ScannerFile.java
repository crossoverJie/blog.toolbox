package top.crossoverjie.nows.nows.scan;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/10/25 23:01
 * @since JDK 1.8
 */
@Component
public class ScannerFile {

    private Set<FileInfo> fileInfos = new TreeSet();

    public Set<FileInfo> getAllFile(String path) {

        File f = new File(path);
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                String directoryPath = file.getPath();
                getAllFile(directoryPath);
            } else {
                String filePath = file.getPath();
                if (!filePath.endsWith(".md")) {
                    continue;
                }
                FileInfo info = new FileInfo(filePath, file.lastModified());
                fileInfos.add(info);
            }
        }

        return fileInfos;
    }


    public final class FileInfo implements Comparable<FileInfo> {
        private String filePath;
        private long modifyTime;

        public FileInfo(String filePath, long modifyTime) {
            this.filePath = filePath;
            this.modifyTime = modifyTime;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public int compareTo(FileInfo info) {
            if (info.modifyTime < this.modifyTime) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
