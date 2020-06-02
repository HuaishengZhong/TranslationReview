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

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class BaiduTranslator extends AbstractTranslator {
    private static final String url = "https://fanyi.baidu.com/v2transapi";

    public BaiduTranslator(){
        super(url);
    }

    @Override
    public void setLangSupport() {
        langMap.put(LANG.ZH, "zh");
        langMap.put(LANG.EN, "en");
        langMap.put(LANG.JP, "jp");
        langMap.put(LANG.KOR, "kor");
        langMap.put(LANG.FRA, "fra");
        langMap.put(LANG.RU, "ru");
        langMap.put(LANG.DE, "de");
    }

    @Override
    public void setFormData(LANG from, LANG to, String text) {
        formData.put("from", langMap.get(from));
        formData.put("to", langMap.get(to));
        formData.put("query", text);
        formData.put("simple_means_flag", "3");
        formData.put("sign", token(text, "320305.131321201"));
        formData.put("token", "cc0ec1ffb8781ff8ca3cfd7f7b530c57");
    }

    @Override
    public String query() throws Exception {
        HttpPost request = new HttpPost(url);

        request.setEntity(new UrlEncodedFormEntity(TranslateUtil.map2list(formData), "UTF-8"));
        request.setHeader("Cookie", TranslateCacheConfig.baiduCache);
        request.setHeader("User-Agent", TranslateCacheConfig.userAgent);

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "UTF-8");

        EntityUtils.consume(entity);
        response.getEntity().getContent().close();
        response.close();

        return result;
    }

    @Override
    public String parses(String text) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.readTree(text).path("trans_result").findPath("dst").toString();
        s = s.substring(1, s.length() - 1);
        return s;
    }

    private String token(String text, String gtk) {
        String result = "";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        try {
            FileReader reader = new FileReader("src/main/resources/tk/Baidu.js");
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable)engine;
                result = String.valueOf(invoke.invokeFunction("token", text, gtk));
            }
        } catch (ScriptException | NoSuchMethodException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
