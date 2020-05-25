package cn.com.wind.wdp.service.impl;

import cn.com.wind.wdp.bean.TranslationResult;
import cn.com.wind.wdp.dao.TranslationResultDao;
import cn.com.wind.wdp.service.TranslationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TranslationResultServiceImpl implements TranslationResultService {

    @Autowired
    private TranslationResultDao translationResultDao;

    @Override
    public List<TranslationResult> getByTime(String start, String end) {
        return translationResultDao.getByTime(start, end);
    }

    @Override
    public int insert(TranslationResult translationResult) {
        return translationResultDao.insert(translationResult);
    }
}
