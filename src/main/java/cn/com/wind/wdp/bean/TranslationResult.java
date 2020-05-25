package cn.com.wind.wdp.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TranslationResult {

    private String translationEngine;
    private String source;
    private String target;
    private int mark;
    private String submitTime;

}
