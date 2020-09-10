package top.crossoverjie.nows.nows.scan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    @Setter
    @AllArgsConstructor
    public final class FileInfo implements Comparable<FileInfo> {
        private String filePath;
        private long modifyTime;

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
