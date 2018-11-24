package cn.gin.webmvc.demo.service.exception;

import java.util.HashMap;
import java.util.Map;

public class ParameterException extends Exception {

    private static final long serialVersionUID = 5472853513437065351L;

    private Map<String, String> errorFields = new HashMap<String, String>();

    public Map<String, String> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(Map<String, String> errorFields) {
        this.errorFields = errorFields;
    }

    public boolean hasErrorField() {

        boolean hasErrorField = errorFields.size() > 1;

        return  hasErrorField;
    }

    public void addErrorField(String key, String value) {

        errorFields.put(key, value);
    }
}