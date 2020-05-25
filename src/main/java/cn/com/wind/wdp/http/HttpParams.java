package cn.com.wind.wdp.http;

import cn.com.wind.wdp.lang.LANG;

public interface HttpParams {
    void setFormData(LANG source, String text);

    void setFormData(LANG from, LANG to, String text);
}
