package cn.com.wind.wdp.controller;

import cn.com.wind.wdp.bean.TranslationResult;
import cn.com.wind.wdp.config.FilePathConfig;
import cn.com.wind.wdp.service.RedisService;
import cn.com.wind.wdp.service.TranslationResultService;
import cn.com.wind.wdp.util.ExcelUtil;
import cn.com.wind.wdp.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class Download {

    @Autowired
    private TranslationResultService translationResultService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/download")
    public String downloadResult() {
        Date day = new Date();
        List<TranslationResult> result = translationResultService.getByTime(TimeUtil.getToday(day), TimeUtil.getNextDay(day));
        String[] s = {"engine", "source", "target", "mark", "time"};
        try {
            ExcelUtil.createExcel(FilePathConfig.downloadPath + TimeUtil.getNowDay(day) + ".xlsx", s, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping("/getJson")
    @ResponseBody
    public List<Object> getJson() {
        Date day = new Date();
        String today = TimeUtil.getToday(day);
        List<Object> result = redisService.lRange(today + "-result", 0, redisService.lSize(today + "-result") - 1);
        return result;
    }
}
