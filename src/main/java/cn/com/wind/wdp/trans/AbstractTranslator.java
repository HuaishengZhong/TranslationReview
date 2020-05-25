package cn.com.wind.wdp.trans;

import cn.com.wind.wdp.http.AbstractHttpAttribute;
import cn.com.wind.wdp.http.HttpParams;
import cn.com.wind.wdp.lang.LANG;

import java.io.IOException;

public abstract class AbstractTranslator extends AbstractHttpAttribute implements HttpParams {

    public AbstractTranslator(String url) {
        super(url);
        setLangSupport();
    }

    @Override
    public String run(LANG source, String text) {
        return null;
    }

    @Override
    public String run(LANG from, LANG to, String text) {
        String result = "";
        setFormData(from, to, text);
        try {
            result = parses(query());
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
        return result;
    }

    public abstract void setLangSupport();

    @Override
    public void setFormData(LANG source, String text) {}

    @Override
    public abstract void setFormData(LANG from, LANG to, String text);

    @Override
    public abstract String query() throws Exception;

    public abstract String parses(String text) throws IOException;
}
