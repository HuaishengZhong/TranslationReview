package cn.com.wind.wdp.querier;

import cn.com.wind.wdp.http.AbstractHttpAttribute;
import cn.com.wind.wdp.lang.LANG;

import java.util.ArrayList;
import java.util.List;

public final class Querier<T extends AbstractHttpAttribute> {
    private LANG from;
    private LANG to;
    private String text;
    private List<T> collection;

    public Querier() {
        collection = new ArrayList<T>();
    }

    public List<String> execute() {
        List<String> result = new ArrayList<String>();

        for (T element : collection) {
                result.add(element.run(from, to, text));
        }
        return result;
    }

    public void setParams(LANG from, LANG to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public void setParams(LANG source, String text) {
        this.from = source;
        this.text = text;
    }

    public void attach(T element){
        collection.add(element);
    }

    public void detach(T element) {
        collection.remove(element);
    }
}
