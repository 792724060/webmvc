package cn.gin.webmvc.support.paging;

import cn.gin.webmvc.support.Constants;
import cn.gin.webmvc.support.config.Global;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pagination<T> {

    private int totalCount;
    private int pageCount;
    private int pageSize;
    private int currentPage;
    private int offset;
    private String url;

    // The query items of this pagination request, the format is like: key1=val1&key2=val2&key3=val3
    private String queryItems = StringUtils.EMPTY;
    private Map<String, String> queryItemsMap = new HashMap<String, String>();

    private List<T> list = new ArrayList<T>();

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;

        if (totalCount < 1) {
            setPageCount(0);
        } else {
            setPageCount((totalCount - 1) / getPageSize() + 1);
        }
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {

        if (pageSize == 0) {
            String pageSizeStr = Global.getConfig(Constants.PROPERTIES_KEY_PAGE_SIZE);
            pageSize = Integer.parseInt(pageSizeStr);
        }

        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {

        if(currentPage < 1) {
            setCurrentPage(1);
        }

        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getOffset() {
        offset = (getCurrentPage() - 1) * getPageSize();
        return offset;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQueryItems() {
        return queryItems;
    }

    public void setQueryItems(String queryItems) {
        this.queryItems = queryItems;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Map<String, String> getQueryItemsMap() {
        return queryItemsMap;
    }

    public void setQueryItemsMap(Map<String, String> queryItemsMap) {
        this.queryItemsMap = queryItemsMap;
    }

    public void validate() {
        // Validate the current page and page size.
        if(getCurrentPage() > getPageCount()) {
            setCurrentPage(1);
        }
    }

    public void appendQueryItems(String queryItems) {

        if (StringUtils.isEmpty(getQueryItems())) {
            this.setQueryItems(queryItems);
        } else {
            this.setQueryItems(getQueryItems() + Constants.MARK_AND + queryItems);
        }
    }

    public void addItems(String key, String value) {

        if (!StringUtils.isEmpty(key)) {

            if (queryItemsMap == null) {
                queryItemsMap = new HashMap<String, String> ();
            }
            queryItemsMap.put(key, value);
            appendQueryItems(key + Constants.MARK_EQUAL + value);
        }
    }

    public String getItems(String key) {

        if (queryItemsMap == null || StringUtils.isEmpty(key)) {

            return null;
        }

        return queryItemsMap.get(key);
    }

    public boolean hasQueryItems() {

        if (!StringUtils.isEmpty(queryItems) && queryItemsMap !=null && queryItemsMap.size() > 0) {

            return true;
        }

        return false;
    }
}