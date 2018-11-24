package cn.gin.webmvc.support;

public class Constants {

    // Context about.
    public static final String CONTEXT_ATTR_KEY = "applicationContext";

    // System public constants.
    public static final String SYSTEM_CONFIG = "app.properties";
    public static final String APPLICATION_ENCODING = "UTF-8";
    public static final String EMPTY = "";
    public static final String CODEC_MD5 = "MD5";
    public static final String HTTP_POST = "POST";
    public static final String ON = "ON";
    public static final String SEPARATOR_CENTER_LINE = "-";
    public static final String SEPARATOR_UNDER_LINE = "_";
    public static final String SEPARATOR_SLASH = "/";
    public static final String SEPARATOR_BACKSLASH = "\\";
    public static final String MARK_QUESTION = "?";
    public static final String MARK_AND = "&";
    public static final String MARK_EQUAL = "=";
    public static final String MARK_COMMA = ",";
    public static final String MARK_PERCENT = "%";
    public static final String MARK_ZERO = "0";
    public static final String MARK_Q = "Q";
    public static final String MARK_DOT = ".";
    public static final String MARK_SINGLE_QUOTES = "'";
    public static final String MARK_DOUBLE_QUOTES = "\"";
    public static final String MARK_WHITESPACE = " ";
    public static final String COUNT = "count";
    public static final String PAGING_CURRENTPAGE = "page";
    public static final String PAGING_PAGESIZE = "size";
    public static final String PAGING_TOTALCOUNT = "totalCount";
    public static final String PAGING_DEFAULT_ITEMS = "?page=1&size=";
    public static final String ID = "id";
    public static final String ACTION = "action";
    public static final String COMMON_DEFAULT_SERVLET_NAME = "default";

    //
    public static final String PAGING_TEMPLATE_UL_START = "<ul class='list'>";
    public static final String PAGING_TEMPLATE_UL_END = "</ul>";
    public static final String PAGING_TEMPLATE_PAGE_PREVIOUS = "<li class='icon' data-img='left'><a href='javascript:;'></a></li>";
    public static final String PAGING_TEMPLATE_PAGE_PREVIOUS_URL = "<li class='icon' data-img='left'><a href='&url;'></a></li>";
    public static final String PAGING_TEMPLATE_PAGE_NUMBER_CURRENT = "<li class='active'><a href='javascript:;'>&currentPage;</a></li>";
    public static final String PAGING_TEMPLATE_PAGE_NUMBER_URL_CURRENT = "<li class=''><a href='&url;'>&currentPage;</a></li>";
    public static final String PAGING_TEMPLATE_PAGE_ELLIPSIS = "<li class=''><a href='javascript:;'>...</a></li>";
    public static final String PAGING_TEMPLATE_PAGE_NEXT = "<li class='icon' data-img='right'><a href='javascript:;'></a></li>";
    public static final String PAGING_TEMPLATE_PAGE_NEXT_URL = "<li class='icon' data-img='right'><a href='&url;'></a></li>";
    public static final String PAGING_TEMPLATE_PAGE_JUMP_INPUT = "<li class='target-page'><input type='number' min='&min;' max='&max;' value='&value;' id='jump' name='jump' /></li>";
    public static final String PAGING_TEMPLATE_PAGE_JUMP_BTN = "<li class=''><input type='button' value='Go' id='jumpBtn' /></li>";
    public static final String PAGING_TEMPLATE_PARAM_URL = "&url;";
    public static final String PAGING_TEMPLATE_PARAM_CURRENT = "&currentPage;";
    public static final String PAGING_TEMPLATE_PARAM_MIN = "&min;";
    public static final String PAGING_TEMPLATE_PARAM_MAX = "&max;";
    public static final String PAGING_TEMPLATE_PARAM_VALUE = "&value;";

    // Properties key.
    public static final String PROPERTIES_KEY_JDBC_DRIVER = "JDBC_DRIVER";
    public static final String PROPERTIES_KEY_JDBC_URL = "JDBC_URL";
    public static final String PROPERTIES_KEY_JDBC_USERNAME = "JDBC_USERNAME";
    public static final String PROPERTIES_KEY_JDBC_PASSWORD = "JDBC_PASSWORD";
    public static final String PROPERTIES_KEY_ROOT_URI = "web.root.uri";
    public static final String PROPERTIES_KEY_ROOT_REAL = "web.root";
    public static final String PROPERTIES_KEY_SERVER = "web.server";
    public static final String PROPERTIES_KEY_VIEW_PREFIX = "web.view.prefix";
    public static final String PROPERTIES_KEY_VIEW_SUFFIX = "web.view.suffix";
    public static final String PROPERTIES_KEY_VIEW_404 = "web.view.404";
    public static final String PROPERTIES_KEY_PAGE_SIZE = "pageSize";
    public static final String PROPERTIES_KEY_ANON = "anon";
    public static final String PROPERTIES_KEY_STATIC = "static";


    // Session and application attribute name.
    public static final String ATTR_SESSION_USER = "user";
    public static final String ATTR_SESSION_QUESTION_IDENTIFIER = "questionIdentifier";
    public static final String ATTR_SESSION_FLASHMSG = "flushMsg";
    public static final String ATTR_SESSION_FLASHMSG_QUESTION_CREATE = "Create the question succeed.";
    public static final String ATTR_SESSION_FLASHMSG_QUESTION_EDIT = "Edit the question data succeed.";
    public static final Object ATTR_SESSION_FLASHMSG_QUESTION_EDIT_FALSE = "Edit the question data faild.";
    public static final String ATTR_SESSION_FLASHMSG_QUESTION_DELETE = "Delete ? items.";
    public static final String ATTR_APPLICATION_SERVER = "server";
    public static final String ATTR_APPLICATION_ROOT = "root";

    public static final String ATTR_REQUEST_PAGINATION = "pagination";
    public static final String ATTR_REQUEST_QUESTION = "question";
    public static final String ATTR_SESSION_QUESTION_DELETESET = "questionDeleteSet";

    // Cookie attribute name.
    public static final String COOKIE_PATH = "/oes/";
    public static final String COOKIE_TOKEN = "TOKEN";
    public static final String COOKIE_SERIES = "SERIES";
    public static final String COOKIE_JESSIONID = "JSESSIONID";

    // Client parameter name.
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String REMEMBER_ME = "rememberMe";
    public static final String KEY = "key";
    public static final String ORDER = "order";
    public static final String IDENTIFIER = "identifier";
    public static final String IDENTIFIERCOPY = "identifierCopy";
    public static final String CONTENT = "content";
    public static final String ANSWER = "answer";
    public static final String FIRST = "first";
    public static final String SECOND = "second";
    public static final String THIRD = "third";
    public static final String FOURTH = "fourth";

    // System path.
    public static final String REQUEST_USER = "/user/*";
    public static final String REQUEST_USER_CONTROLLER = "/user";
    public static final String REQUEST_USER_LOGIN = "/login";
    public static final String REQUEST_USER_LOGOUT = "/logout";
    public static final String VIEW_USER_LOGIN = "/user/login";
    public static final String VIEW_WELCOME = "/welcome";

    public static final String REQUEST_QUESTION = "/question/*";
    public static final String REQUEST_QUESTION_CONTROLLER = "/question";
    public static final String REQUEST_QUESTION_FIND = "/find";
    public static final String REQUEST_QUESTION_FIND_UPDATE = "/find/update";
    public static final String VIEW_QUESTION_FIND = "/question/find";
    public static final String REQUEST_QUESTION_CREATE = "/create";
    public static final String VIEW_QUESTION_CREATE = "/question/create";
    public static final String REQUEST_QUESTION_EDIT = "/edit";
    public static final String VIEW_QUESTION_EDIT = "/question/edit";
    public static final String REQUEST_QUESTION_DELETE = "/delete";

    // Error message.
    public static final String ERROR_CLIENT = "errorClient";
    public static final String ERROR_SERVER = "errorServer";
    public static final String ERROR_ACCOUNT_EMPTY = "User account cannot be null.";
    public static final String ERROR_PASSWORD_EMPTY = "User password cannot be null.";
    public static final String ERROR_PASSWORD_UNMATCH = "Login password is incorrect.";
    public static final String ERROR_USER_NONEXISTS = "Username does not exist.";
    public static final String ERROR_COOKIE_REMEMBER_ME_INVALID = "Authentication infomations are invalid.";
    public static final String ERROR_DEFAULT_SERVLET_NOTFOUND = "A RequestDispatcher could not be located for the default servlet: ";

    public static final String ERROR_PAGINATION_NONE_DATA = "There is no result.";
    public static final String ERROR_PAGINATION_CURRENTPAGE_INVALID = "The current page number is more than total page number.";

    // JDBC properties key.
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://10.254.100.78:3306/oes?useUnicode=true&amp;characterEncoding=UTF-8";
    public static final String JDBC_USERNAME = "root";
    public static final String JDBC_PASSWORD = "111";

    // SQL statement.
    public static final String SQL_FRAGMENT_LIMIT = "limit ?, ?";
    public static final String SQL_FRAGMENT_QUESTION_COUNTENT_LIKE = "WHERE `content` LIKE ?";

    public static final String SQL_USER_GETBYID = "SELECT * FROM `oes_user` WHERE user_id = ?";
    public static final String SQL_USER_GETBYACCOUNT = "SELECT * FROM `oes_user` WHERE `account` = ?";
    public static final String SQL_USER_TOKEN_CREATE = "INSERT INTO `oes_user_token`(`user_id`, `account`, `token`, `series`) VALUES(?, ?, ?, ?)";
    public static final String SQL_USER_TOKEN_UPDATE = "UPDATE `oes_user_token` SET account = ?, token = ?, series = ? where user_id = ?";
    public static final String SQL_USER_TOKEN_GETBYID = "SELECT * FROM `oes_user_token` WHERE `user_id` = ?";
    public static final String SQL_USER_TOKEN_GETBYACCOUNT = "SELECT * FROM `oes_user_token` WHERE `account` = ?";

    public static final String SQL_QUESTION_GETBYID = "SELECT * FROM `oes_question` WHERE question_id = ?";
    public static final String SQL_QUESTION_FIND = "SELECT * FROM `oes_question` WHERE `question_status` IN(1, 2, 3, 4) ";
    public static final String SQL_QUESTION_GETCOUNT = "SELECT COUNT(`question_id`) FROM `oes_question` WHERE `question_status` IN(1, 2, 3, 4) ";
    public static final String SQL_QUESTION_FINDPAGINATION = "SELECT * FROM `oes_question` WHERE `question_status` IN(1, 2, 3, 4)  LIMIT ?, ?";
    public static final String SQL_QUESTION_GETCOUNT_ITEM = "SELECT * FROM `oes_question` WHERE `content like ? `LIMIT ?, ?";
    public static final String SQL_QUESTION_CREATE = "INSERT INTO `oes_question`(`identifier`, `creator`, `content`, `answer`, `answer_first`, `answer_second`, `answer_third`, `answer_fourth`, `update_time`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_QUESTION_GETIDENTIFIER = "SELECT MAX(`identifier`) FROM `oes_question`";
    public static final String SQL_QUESTION_UPDATE = "UPDATE `oes_question` SET `content` = ?, `answer` = ?, `answer_first` = ?, `answer_second` = ?, `answer_third` = ?, `answer_fourth` = ?, `update_time` = ? WHERE `question_id` = ?";
    public static final String SQL_QUESTION_DELETE = "UPDATE `oes_question` SET `question_status` = 5 WHERE `question_id` = ?";
    public static final String SQL_QUESTION_DELETEBATCH = "UPDATE `oes_question` SET `question_status` = 5 WHERE `question_id` in (?)";

    // SQL parameter name.
    public static final String FIELD_USER_ID = "user_id";
    public static final String FIELD_USER_ACCOUNT = "account";
    public static final String FIELD_USER_PASSWORD = "password";
    public static final String FIELD_USER_NICKNAME = "nickname";
    public static final String FIELD_USER_SALT = "salt";
    public static final String FIELD_USER_TOKEN = "token";
    public static final String FIELD_USER_SERIES = "series";

    public static final String FIELD_QUESTION_ID = "question_id";
    public static final String FIELD_QUESTION_IDENTIFIER = "identifier";
    public static final String FIELD_QUESTION_CLASSIFY = "classify";
    public static final String FIELD_QUESTION_TYPE = "type";
    public static final String FIELD_QUESTION_DIFFICUTLY = "difficulty";
    public static final String FIELD_QUESTION_CONTENT = "content";
    public static final String FIELD_QUESTION_ANSWER = "answer";
    public static final String FIELD_QUESTION_FIRST = "answer_first";
    public static final String FIELD_QUESTION_SECOND = "answer_second";
    public static final String FIELD_QUESTION_THIRD = "answer_third";
    public static final String FIELD_QUESTION_FOURTH = "answer_fourth";
    public static final String FIELD_QUESTION_STATUS = "question_status";
    public static final String FIELD_QUESTION_VERSION = "version";

    // MVC about.
    public static final String MVC_CONFIGURATION_SUFFIX_CLASSFILE = ".class";

    public static final String MVC_CONFIGURATION_DEFAULT = "beans.xml";
    public static final String MVC_CONFIGURATION_TAG_KEY_PROPERTIES = "properties";
    public static final String MVC_CONFIGURATION_TAG_KEY_CONTEXT = "context";
    public static final String MVC_CONFIGURATION_TAG_KEY_BEAN = "bean";
    public static final String MVC_CONFIGURATION_TAG_KEY_ATTR_LOCATION = "location";
    public static final String MVC_CONFIGURATION_TAG_KEY_ATTR_BASEPACKAGE = "base-package";
    public static final String MVC_CONFIGURATION_TAG_KEY_ATTR_NAME = "name";
    public static final String MVC_CONFIGURATION_TAG_KEY_ATTR_CLASS = "class";
    public static final String MVC_CONFIGURATION_TAG_KEY_ATTR_VALUE = "value";
    public static final String MVC_CONFIGURATION_TAG_KEY_ATTR_REF = "ref";
    public static final String MVC_CONFIGURATION_TAG_KEY_ATTR_ANNOTATION = "annotation";
    public static final String MVC_ERROR_CONFIGURATION_NO = "There is no context configuration can be found.";
    public static final String MVC_ERROR_BEAN_SAME = "Cannot use the same bean name: ";
}