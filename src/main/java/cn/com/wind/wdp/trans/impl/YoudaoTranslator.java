package cn.com.wind.wdp.trans.impl;

import cn.com.wind.wdp.config.TranslateCacheConfig;
import cn.com.wind.wdp.lang.LANG;
import cn.com.wind.wdp.trans.AbstractTranslator;
import cn.com.wind.wdp.util.TranslateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public final class YoudaoTranslator extends AbstractTranslator {
    private static final String url = "http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule";

    public YoudaoTranslator(){
        super(url);
    }

    @Override
    public void setLangSupport() {
        langMap.put(LANG.ZH, "zh-CHS");
        langMap.put(LANG.EN, "en");
        langMap.put(LANG.JP, "ja");
        langMap.put(LANG.KOR, "ko");
        langMap.put(LANG.FRA, "fr");
        langMap.put(LANG.RU, "ru");
        langMap.put(LANG.DE, "de");
    }

    @Override
    public void setFormData(LANG from, LANG to, String text) {
        String slat = String.valueOf(System.currentTimeMillis() + (long)(Math.random() * 10 + 1));
        String sign = TranslateUtil.md5("fanyideskweb" + text + slat + "Nw(nmmbP%A-r6U3EUn]Aj");

        formData.put("i", text);
        formData.put("from", langMap.get(from));
        formData.put("to", langMap.get(to));
        formData.put("smartresult", "dict");
        formData.put("client", "fanyideskweb");
        formData.put("salt", slat);
        formData.put("sign", sign);
        formData.put("doctype", "json");
        formData.put("version", "2.1");
        formData.put("keyfrom", "fanyi.web");
        formData.put("action", "FY_BY_CLICKBUTTION");
        formData.put("typoResult", "false");
    }

    @Override
    public String query() throws Exception {
        HttpPost request = new HttpPost(TranslateUtil.getUrlWithQueryString(url, formData));

        request.setHeader("Cookie", TranslateCacheConfig.youdaoCache);
        request.setHeader("Referer","http://fanyi.youdao.com/");
        request.setHeader("User-Agent",TranslateCacheConfig.userAgent);

        CloseableHttpResponse httpResponse = httpClient.execute(request);
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = EntityUtils.toString(httpEntity, "UTF-8");
        EntityUtils.consume(httpEntity);
        httpResponse.close();

        return result;
    }

    @Override
    public String parses(String text) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.readTree(text).path("translateResult").findPath("tgt").toString();
        s = s.substring(1, s.length() - 1);
        return s;
    }
}
