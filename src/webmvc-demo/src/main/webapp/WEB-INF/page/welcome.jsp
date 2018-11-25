<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="${root}/static/css/static.css">
    <link type="text/css" rel="stylesheet" href="${root}/static/css/welcome.template.css">
    <link type="text/css" rel="stylesheet" href="${root}/static/css/question.find.css">
    <title>Welcome</title>
  </head>
  <body data-pa="Welcome">
    <!-- Header -->
    <jsp:include page="/WEB-INF/page/template/header.jsp"></jsp:include>
    
    <!-- Navigator -->
    <div id="nav">
      <div class="nav-wrapper clear">
        <div class="ques item active">
          <a href="${root}/question/find">Question Management</a>
        </div>
        <div class="exam item">
          Exam Management
        </div>
      </div>
      <div class="nav-list">
        <span class="inactive">Question Management</span>
        <span class="inactive">Question List</span>
        <span class="active">Q002008</span>
      </div>
    </div>
    
    <!-- Main -->
    <div id="main">
      <div class="main-wrapper">
        <div class="menu-wrapper">
          <ul class="menu">
           <li class="item">
             <a href="${root}/question/find" class="active">Question List</a>
           </li>
           <li class="item">
             <a href="${root}/question/create">Create Question</a>
           </li>
          </ul>
        </div>
        <div class="content-wrapper">
          <div class="content">
            <div class="search">
              <form action="" id="QuestionSearch" class="clear">
                <div class="input">
                  <input type="text" name="key" placeholder="Please input the keyword">
                  <span class="icon" data-img="search" title="Search"></span>
                </div>
              </form>
            </div>
            <div class="table">
              <div class="thead">
                <ul class="clear">
                  <li class="item placeholder"></li>
                  <li class="item no"></li>
                  <li class="item placeholder"></li>
                  <li class="item id">ID</li>
                  <li class="item placeholder"></li>
                  <li class="item desc">Description</li>
                  <li class="item placeholder"></li>
                  <li class="item edit">Edit</li>
                  <li class="item placeholder"></li>
                  <li class="item op">
                    <span class="icon" data-img="unchecked"></span>
                  </li>
                  <li class="item placeholder"></li>
                </ul>
              </div>
              <div class="tbody">
                <c:forEach begin="1" end="10" varStatus="status">
                <ul class="clear">
                  <li class="item"></li>
                  <li class="item no">${status.count}</li>
                  <li class="item"></li>
                  <li class="item id">ID</li>
                  <li class="item"></li>
                  <li class="item desc">Description</li>
                  <li class="item"></li>
                  <li class="item edit">
                    <span class="icon" data-img="edit"></span>
                  </li>
                  <li class="item"></li>
                  <li class="item op">
                    <span class="icon" data-img="unchecked"></span>
                  </li>
                  <li class="item"></li>
                </ul>
                </c:forEach>
              </div>
              <div class="tfoot">
                
              </div>
              <table>
              </table>
            </div>
            <div class="paging">
              
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Footer -->
    <div id="footer">
      <p class="record">Copyright © 2014 Augmentum. Inc. All Rights reserved.</p>
    </div>
    
    <!-- Scripts -->
    <script type="text/javascript" src="${root}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${root}/static/js/history.js"></script>
    <script type="text/javascript" src="${root}/static/js/static.js"></script>
  </body>
</html>