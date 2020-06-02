package cn.com.wind.wdp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TranslateCacheConfig {
    public static String userAgent;
    public static String baiduCache;
    public static String tencentCache;
    public static String youdaoCache;

    @Value("${translationCache.user-agent}")
    public void setUserAgent(String userAgent) {
        TranslateCacheConfig.userAgent = userAgent;
    }

    @Value("${translationCache.baidu-cache}")
    public void setBaiduCache(String baiduCache) {
        TranslateCacheConfig.baiduCache = baiduCache;
    }

    @Value("${translationCache.tencent-cache}")
    public void setTencentCache(String tencentCache) {
        TranslateCacheConfig.tencentCache = tencentCache;
    }

    @Value("${translationCache.youdao-cache}")
    public void setYoudaoCache(String youdaoCache) {
        TranslateCacheConfig.youdaoCache = youdaoCache;
    }
}
