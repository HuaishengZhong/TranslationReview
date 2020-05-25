package cn.com.wind.wdp.controller;

import cn.com.wind.wdp.bean.TranslationResult;
import cn.com.wind.wdp.service.TranslationResultService;
import cn.com.wind.wdp.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class Submit {

    @Autowired
    private TranslationResultService translationResultService;

    @GetMapping("/submit")
    public String submitResult(@RequestBody TranslationResult translationResult) {
        Date day = new Date();
        translationResult.setSubmitTime(TimeUtil.getNowTime(day));
        int result = translationResultService.insert(translationResult);
        if (result > 0) {
            return "index";
        }
        return "error";
    }

}
