package cn.com.wind.wdp.service;

import cn.com.wind.wdp.bean.TranslationResult;

import java.util.List;

public interface TranslationResultService {

    List<TranslationResult> getByTime(String start, String end);

    int insert(TranslationResult translationResult);
}
