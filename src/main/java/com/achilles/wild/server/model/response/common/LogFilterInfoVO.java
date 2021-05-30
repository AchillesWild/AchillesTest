package com.achilles.wild.server.model.response.common;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import java.io.Serializable;


public class LogFilterInfoVO implements Serializable {

    private static final long serialVersionUID = -4486741818794596662L;


    @ExcelProperty(index = 0,value = "url")
    private String uri;

    @ExcelProperty(index = 1,value = "耗时")
    @ColumnWidth(18)
    private Integer time;

    @ExcelIgnore
    private Integer fire;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
