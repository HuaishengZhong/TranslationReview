package cn.com.wind.wdp.controller;

import cn.com.wind.wdp.querier.Querier;
import cn.com.wind.wdp.trans.AbstractTranslator;
import cn.com.wind.wdp.trans.impl.*;
import cn.com.wind.wdp.util.TranslateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Translate {

    @GetMapping("/trans")
    @ResponseBody
    public String translate(@RequestParam("engine") String engine,
                            @RequestParam("sl") String sl,
                            @RequestParam("tl") String tl,
                            @RequestParam("text") String text) {

        Querier<AbstractTranslator> querierTrans = new Querier<>();
        querierTrans.setParams(TranslateUtil.getLang(sl), TranslateUtil.getLang(tl), text);

        List<String> resultTrans = null;
        switch (engine) {
            case "google":
                querierTrans.attach(new GoogleTranslator());
                resultTrans = querierTrans.execute();
                return resultTrans.get(0);
            case "baidu":
                querierTrans.attach(new BaiduTranslator());
                resultTrans = querierTrans.execute();
                return resultTrans.get(0);
            case "tencent":
                querierTrans.attach(new TencentTranslator());
                resultTrans = querierTrans.execute();
                return resultTrans.get(0);
            case "youdao":
                querierTrans.attach(new YoudaoTranslator());
                resultTrans = querierTrans.execute();
                return resultTrans.get(0);
        }

        return "";
    }
}
