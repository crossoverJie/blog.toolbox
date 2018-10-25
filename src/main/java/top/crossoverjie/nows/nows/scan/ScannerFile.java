package top.crossoverjie.nows.nows.scan;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/25 23:01
 * @since JDK 1.8
 */
@Component
public class ScannerFile {

    private Map<String,String> allFile = new HashMap<>(16) ;

    public Map<String,String> getAllFile(String path){

        File f = new File(path) ;
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()){
                String directoryPath = file.getPath();
                getAllFile(directoryPath);
            }else {
                String filePath = file.getPath();
                if (!filePath.endsWith(".md")){
                    continue;
                }
                allFile.put(file.getName(),filePath) ;
            }
        }

        return allFile ;
    }
}
