package cn.com.wind.wdp.http;

import cn.com.wind.wdp.lang.LANG;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHttpAttribute {
    public String url;
    public Map<String, String> formData;
    public Map<LANG, String> langMap;
    public CloseableHttpClient httpClient;

    public AbstractHttpAttribute(String url) {
        this.url = url;
        this.formData = new HashMap<>();
        this.langMap = new HashMap<>();
        this.httpClient = HttpClients.createDefault();
    }

    public abstract String query() throws Exception;

    public abstract String run(LANG source, String text);

    public abstract String run(LANG from, LANG to, String text);

    public void close(HttpEntity httpEntity, CloseableHttpResponse httpResponse) {
        try {
            EntityUtils.consume(httpEntity);
            if (null != httpResponse) {
                httpResponse.close();
            }
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (null != httpClient) {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
