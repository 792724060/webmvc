package cn.gin.webmvc.controller.model;

import java.util.HashMap;
import java.util.Map;

public class ParameterMap extends HashMap<String, String[]> {

    private static final long serialVersionUID = 1L;

    public ParameterMap() {}

    public ParameterMap(Map<? extends String, ? extends String[]> m) {
        super(m);
    }

    @Override
    public String[] get(Object key) {

        return super.get(key);
    }

    public String getString(Object key) {

        String[] strings = super.get(key);

        if (strings != null && strings.length == 1) {

            return strings[0];
        }

        return null;
    }
}