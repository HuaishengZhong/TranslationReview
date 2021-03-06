package cn.com.wind.wdp.trans.impl;

import cn.com.wind.wdp.config.TranslateCacheConfig;
import cn.com.wind.wdp.lang.LANG;
import cn.com.wind.wdp.trans.AbstractTranslator;
import cn.com.wind.wdp.util.TranslateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public final class TencentTranslator extends AbstractTranslator {
    private static final String url = "https://fanyi.qq.com/api/translate";

    public TencentTranslator(){
        super(url);
    }

    @Override
    public void setLangSupport() {
        langMap.put(LANG.ZH, "zh");
        langMap.put(LANG.EN, "en");
        langMap.put(LANG.JP, "jp");
        langMap.put(LANG.KOR, "kr");
        langMap.put(LANG.FRA, "fr");
        langMap.put(LANG.RU, "ru");
        langMap.put(LANG.DE, "de");
    }

    @Override
    public void setFormData(LANG from, LANG to, String text) {
        formData.put("source", langMap.get(from));
        formData.put("target", langMap.get(to));
        formData.put("sourceText", text);
        formData.put("sessionUuid", "translate_uuid" + String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public String query() throws Exception {
        HttpPost request = new HttpPost(url);
        request.setEntity(new UrlEncodedFormEntity(TranslateUtil.map2list(formData), "UTF-8"));

        request.setHeader("Cookie", TranslateCacheConfig.tencentCache);
        request.setHeader("Origin", "http://fanyi.qq.com");

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "utf-8");

        EntityUtils.consume(entity);
        response.getEntity().getContent().close();
        response.close();

        return result;
    }

    @Override
    public String parses(String text) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.readTree(text).path("translate").findPath("targetText").toString();
        s = s.substring(1, s.length() - 1);
        return s;
    }
}
