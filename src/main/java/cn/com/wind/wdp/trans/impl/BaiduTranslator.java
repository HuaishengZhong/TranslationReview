package cn.com.wind.wdp.trans.impl;

import cn.com.wind.wdp.lang.LANG;
import cn.com.wind.wdp.trans.AbstractTranslator;
import cn.com.wind.wdp.util.TranslateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.Get;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.util.EntityUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;

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
        request.setHeader("Cookie", "BIDUPSID=09D8D04C588D781C2E4D8471F1F41D86; PSTM=1575458604; MCITY=-%3A; BAIDUID=2731C8CE95BBD973D2E14D6290697A24:FG=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDUSS=XNVcTJpLWtJZmpJNkVRTGVyeHBiTXQyakRDSklOVXBwNE1qdkJ4MmE2ZmZjZDllRVFBQUFBJCQAAAAAAAAAAAEAAAAf3Owzy6~U2sLDzb7W0LXEyMsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAN~kt17f5Ldea; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; HISTORY_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1589199798,1590248286,1590248334,1590330715; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; delPer=0; PSINO=3; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1590396945; __yjsv5_shitong=1.0_7_d678a9943d7574123b0700f4d8cd0cd2e347_300_1590396945133_112.2.113.119_e73b02ec; yjs_js_security_passport=9cd6a99890f2175d54f826792c75e33f7e32ca71_1590396948_js; H_PS_PSSID=31730_1455_21085_31111_31254_30841_31464_30824_26350"); // fixme: 此处填写cookie
        request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");

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
        return mapper.readTree(text).path("trans_result").findPath("dst").toString();
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
