package cn.com.wind.wdp.controller;

import cn.com.wind.wdp.bean.TranslationResult;
import cn.com.wind.wdp.service.RedisService;
import cn.com.wind.wdp.service.TranslationResultService;
import cn.com.wind.wdp.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class Submit {

    @Autowired
    private TranslationResultService translationResultService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/submit")
    public String submitResult(@RequestBody TranslationResult translationResult) {
        Date day = new Date();
        translationResult.setSubmitTime(TimeUtil.getNowTime(day));
        String today = TimeUtil.getToday(day);
        redisService.lPush(today + "-result", translationResult);
        int result = translationResultService.insert(translationResult);
        if (result > 0) {
            return "index";
        }
        return "error";
    }

}
