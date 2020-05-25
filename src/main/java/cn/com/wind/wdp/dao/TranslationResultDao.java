package cn.com.wind.wdp.dao;

import cn.com.wind.wdp.bean.TranslationResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TranslationResultDao {

    List<TranslationResult> getByTime(@Param("start") String start, @Param("end") String end);

    int insert(TranslationResult translationResult);

}
