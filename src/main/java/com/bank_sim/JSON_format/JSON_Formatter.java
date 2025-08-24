package com.bank_sim.JSON_format;

public class JSON_Formatter {
    private String value;
    private String key;
    public JSON_Formatter(){}
    public JSON_Formatter(String key, String value){
        this.value = value;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String FormatJSON(String key, String value){
        return String.format("{\"%s\": \"%s\"}" ,key,value);
    }
}
