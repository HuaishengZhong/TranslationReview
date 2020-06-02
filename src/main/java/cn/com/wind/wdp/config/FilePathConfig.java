package cn.com.wind.wdp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilePathConfig {
    public static String downloadPath;

    @Value("${filePath.download-path}")
    public void setDownloadPath(String downloadPath) {
        FilePathConfig.downloadPath = downloadPath;
    }
}
