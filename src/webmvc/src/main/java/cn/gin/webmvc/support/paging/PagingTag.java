package cn.gin.webmvc.support.paging;

import cn.gin.webmvc.support.Constants;
import cn.gin.webmvc.support.util.NumberUtils;
import cn.gin.webmvc.support.util.StringUtils;
import cn.gin.webmvc.support.util.ValidateUtils;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PagingTag extends SimpleTagSupport {

    public static final String PAGE_ITEM = "page=";

    private String currentPage;
    private String pageSize;
    private String pageCount;
    private String url;
    private String queryItems = StringUtils.EMPTY;


    private PageContext pageContext;
    private JspFragment jspFragment;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
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

    public PageContext getPageContext() {
        return pageContext;
    }

    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public JspFragment getJspFragment() {
        return jspFragment;
    }

    public void setJspFragment(JspFragment jspFragment) {
        this.jspFragment = jspFragment;
    }

    @Override
    public void setJspContext(JspContext pc) {
        this.pageContext = (PageContext) pc;
    }

    @Override
    public void doTag() throws IOException {

        int currentPage, pageSize, pageCount;

        try {
            ValidateUtils.assertNotEmpty(this.getCurrentPage(), this.getPageSize(), this.getPageCount());
            currentPage = NumberUtils.createInteger(this.getCurrentPage(), 1);
            pageSize = NumberUtils.createInteger(this.getPageSize(), 10);
            pageCount = NumberUtils.createInteger(this.getPageCount(), 1);
        }
        catch(Exception exception) {
            throw new RuntimeException(exception);
        }

        StringBuilder builder = new StringBuilder(Constants.PAGING_TEMPLATE_UL_START);
        final String queryItemsStr = StringUtils.appendTo(getQueryItems() , Constants.MARK_AND);
        final String pageSizeStr = "&size=" + String.valueOf(pageSize);

        if (pageCount <= 5) {
            // 1. Display all the page code.
            // 1.1 The previous page.
            if(currentPage == 1) {
                builder.append(Constants.PAGING_TEMPLATE_PAGE_PREVIOUS);
            }
            else {
                String newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage - 1) + pageSizeStr;
                builder.append(Constants.PAGING_TEMPLATE_PAGE_PREVIOUS_URL.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl));
            }
            // 1.2 The center page.
            for (int i = 1; i <= pageCount; i++) {

                if (currentPage != i) {
                    // Isn't the current page.
                    String newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + i + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(i)));
                }
                else {
                    // Is the current page.
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_CURRENT.replace("&currentPage;", String.valueOf(i)));
                }
            }
            // 1.3 The next page.
            if(currentPage == pageCount) {
                builder.append(Constants.PAGING_TEMPLATE_PAGE_NEXT);
            }
            else {
                String newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage + 1) + pageSizeStr;
                builder.append(Constants.PAGING_TEMPLATE_PAGE_NEXT_URL.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl));
            }
        }
        else {
            // 2. Display part of the page code and others with ellipsis.
            boolean leftEllipsis = Math.abs(currentPage - pageCount) < (currentPage - 1);

            // 2.1 The previous page.
            if(currentPage <= 1) {
                builder.append(Constants.PAGING_TEMPLATE_PAGE_PREVIOUS);
            }
            else {
                String newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage - 1) + pageSizeStr;
                builder.append(Constants.PAGING_TEMPLATE_PAGE_PREVIOUS_URL.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl));
            }

            // 2.2 The center page.
            if (!leftEllipsis) {
                if(currentPage == 1) {
                    // Page code 1.
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(1)));
                    // Page code 2.
                    String newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage + 1) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage + 1)));
                    // Page code 3.
                    newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage + 2) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage + 2)));
                    // Page code 4.
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_ELLIPSIS);
                    // Page code 5.
                    newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (pageCount) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(pageCount)));
                }
                else {
                    // Page code 1.
                    String newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage - 1) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage - 1)));
                    // Page code 2.
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage)));
                    // Page code 3.
                    newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage + 1) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage + 1)));
                    // Page code 4.
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_ELLIPSIS);
                    // Page code 5.
                    newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (pageCount) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(pageCount)));
                }
            }
            else {
                // Page code 1.
                String newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (1) + pageSizeStr;
                builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(1)));
                // Page code 2.
                builder.append(Constants.PAGING_TEMPLATE_PAGE_ELLIPSIS);
                if (currentPage < pageCount) {
                    // Page code 3.
                    newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage - 1) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage - 1)));
                    // Page code 4.
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage)));
                    // Page code 5.
                    newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage + 1) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage + 1)));
                }
                else {
                    // Page code 3.
                    newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage - 2) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage - 2)));
                    // Page code 4.
                    newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage - 1) + pageSizeStr;
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl).replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(currentPage - 1)));
                    // Page code 5.
                    builder.append(Constants.PAGING_TEMPLATE_PAGE_NUMBER_CURRENT.replace(Constants.PAGING_TEMPLATE_PARAM_CURRENT, String.valueOf(pageCount)));
                }
            }

            // 2.3 The next page.
            if(currentPage == pageCount) {
                builder.append(Constants.PAGING_TEMPLATE_PAGE_NEXT);
            }
            else {
                String newUrl = getUrl() + Constants.MARK_QUESTION + queryItemsStr + PAGE_ITEM + (currentPage + 1) + pageSizeStr;
                builder.append(Constants.PAGING_TEMPLATE_PAGE_NEXT_URL.replace(Constants.PAGING_TEMPLATE_PARAM_URL, newUrl));
            }
        }

        // Handler the select of page size.
        int mostTotalCount = pageSize * pageCount;

        if (mostTotalCount <= 5) {
            builder.append("<li class='page-size'><select id='pageSize' ><option value='5' selected='selected'>5</option></select>Per page</li>");
        }
        else if (mostTotalCount <= 10) {

            if(pageSize == 10) {
                builder.append("<li class='page-size'><select id='pageSize'><option value='5'>5</option><option value='10' selected='selected'>10</option></select>Per page</li>");
            }
            else {
                builder.append("<li class='page-size'><select id='pageSize'><option value='5' selected='selected'>5</option><option value='10'>10</option></select>Per page</li>");
            }
        }
        else if (mostTotalCount <= 100) {

            if(pageSize == 20) {
                builder.append("<li class='page-size'><select id='pageSize'><option value='5'>5</option><option value='10'>10</option><option value='20' selected='selected'>20</option></select>Per page</li>");
            }
            else if(pageSize == 10) {
                builder.append("<li class='page-size'><select id='pageSize'><option value='5'>5</option><option value='10' selected='selected'>10</option><option value='20'>20</option></select>Per page</li>");
            }
            else {
                builder.append("<li class='page-size'><select id='pageSize'><option value='5' selected='selected'>5</option><option value='10'>10</option><option value='20'>20</option></select>Per page</li>");
            }
        }
        else if (mostTotalCount <= 500) {

            if(pageSize == 50) {
                builder.append("<li class='page-size'><select id='pageSize'><option value='10'>10</option><option value='20'>20</option><option value='50' selected='selected'>50</option></select>Per page</li>");
            }
            else if(pageSize == 20) {
                builder.append("<li class='page-size'><select id='pageSize'><option value='10'>10</option><option value='20' selected='selected'>20</option><option value='50'>50</option></select>Per page</li>");
            }
            else {
                builder.append("<li class='page-size'><select id='pageSize'><option value='10' selected='selected'>10</option><option value='20'>20</option><option value='50'>50</option></select>Per page</li>");
            }
        }
        else if (mostTotalCount <= 1000) {

            if(pageSize == 100) {
                builder.append("<li class='page-size'><select id='pageSize'><option value='20'>20</option><option value='50'>50</option><option value='100' selected='selected'>100</option></select>Per page</li>");
            }
            else if(pageSize == 50) {
                builder.append("<li class='page-size'><select id='pageSize'><option value='20'>20</option><option value='50' selected='selected'>50</option><option value='100'>100</option></select>Per page</li>");
            }
            else {
                builder.append("<li class='page-size'><select id='pageSize'><option value='20' selected='selected'>20</option><option value='50'>50</option><option value='100'>100</option></select>Per page</li>");
            }
        }
        else {
            builder.append("<li class='page-size'><select id='pageSize'><option value='100'>100</option><option value='200'>200</option><option value='500'>500</option></select>Per page</li>");
        }

        final int min = 1;
        final int max = pageCount;
        int value = currentPage;
        builder.append(Constants.PAGING_TEMPLATE_PAGE_JUMP_INPUT.replace(Constants.PAGING_TEMPLATE_PARAM_MIN, String.valueOf(min)).replace(Constants.PAGING_TEMPLATE_PARAM_MAX, String.valueOf(max)).replace(Constants.PAGING_TEMPLATE_PARAM_VALUE, String.valueOf(value)));
        builder.append(Constants.PAGING_TEMPLATE_PAGE_JUMP_BTN);
        builder.append(Constants.PAGING_TEMPLATE_UL_END);

        JspWriter out = this.getPageContext().getOut();
        out.write(builder.toString());
        out.flush();
    }
}