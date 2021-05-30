package com.achilles.wild.server.model.query;

import org.apache.commons.lang.Validate;

import java.io.Serializable;

public class Paginator implements Serializable {

    private static final long serialVersionUID = 4756853368609254845L;

    /**
     * 起始行
     * */
    private int start;

    /**
     * 分页大小
     * */
    private int size;

    /**
     * 总记录数
     */
    private int totalItem;

    public Paginator() {

    }

    public Paginator(int start, int size) {
        Validate.isTrue(size > 0 && start >= 0);
        this.start = start;
        this.size = size;
    }

    /**
     * 直接翻页
     * @param pageNum 页码，从1开始
     * */
    public void gotoPage(int pageNum) {
        //如果输入非法参数，则默认为第一页
        if (pageNum <= 1) {
            pageNum = 1;
        }
        start = (pageNum - 1) * size;
    }

    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public int calcPageCount(int rows) {
        if (rows <= 0) {
            rows = 1;
        }
        return (int) Math.ceil((double)rows / (double)getSize());
    }

    public int calcPageCount() {
        return this.calcPageCount(this.totalItem);
    }

    public int calCurrnePage()
    {
        int pageSize = getSize();
        int currPage =1;

        if(pageSize >= 1)
        {
            currPage= (getStart()+ getSize())/ getSize();
        }
        return currPage ;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }
}
