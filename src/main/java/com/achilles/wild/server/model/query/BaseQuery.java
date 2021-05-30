package com.achilles.wild.server.model.query;

public class BaseQuery implements java.io.Serializable{

    private static final long serialVersionUID = -4112218132291243361L;

    public static final int DEFAULT_PAGE_SIZE = 30;

    public static final int MAX_PAGE_SIZE = 5000;


    private Integer pageNo;

    private int pageSize;

    private Paginator paginator;


    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public static Paginator getPaginator(Integer pageNo,Integer pageSize){

        pageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;

        if(pageSize == null || pageSize == 0L){
            pageSize = DEFAULT_PAGE_SIZE;
        }else if(pageSize>MAX_PAGE_SIZE){
            pageSize = MAX_PAGE_SIZE;
        }

        Paginator paginator = new Paginator();
        paginator.setStart((pageNo-1) * pageSize);
        paginator.setSize(pageSize);
        return paginator;
    }

    public static Paginator getDefaultPaginator(){
        return new Paginator(0,DEFAULT_PAGE_SIZE);
    }
}
