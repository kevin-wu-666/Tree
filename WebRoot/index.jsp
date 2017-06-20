<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>欢迎来到主界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <!-- <link rel="stylesheet" href="http://cdn.cdnjs.net/zTree.v3/3.5.24/css/zTreeStyle/zTreeStyle.min.css"/> -->
    <link rel="stylesheet" href="css/zTreeStyle.css"/>
    <!--<link rel="stylesheet" href="css/animate.css"/>-->
    <link rel="stylesheet" href="css/main.css"/>
   <style>
         #booktrees{
            min-height: 500px;
            height:auto;


        }
        .col-xs-3{
            z-index: 66;
        }
        #contents{

            margin-left:50px;

        }
        .operations{

            height:40px;
            border-top-right-radius: 10px;
            border-top-left-radius: 10px;
            background: #129fea;
            color:white;
            text-align: center;
            line-height: 40px;
            font-size:20px;
        }
        .operationModify{
            height:50px;
            border-bottom-right-radius: 10px;
            border-bottom-left-radius: 10px;
            background: #129fea;
            margin-bottom: 30px;
            text-align: right;
            padding-right: 70px;
            line-height: 30px;
            padding-top: 7px;
        }
        #contents h4{
            text-align: center;
        }

        #contents #text{

            text-indent: 20px;
            line-height: 25px;
            color:black;
            height:600px;
            overflow: auto;
            border-left:4px solid  #129fea;
            border-right:4px solid  #129fea;
            text-align: left;

        }

        #AddBtn:hover{

            cursor: pointer;
        }
        .scrollstyle::-webkit-scrollbar-track
        {
            -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
            border-radius: 10px;
            background-color: white;
        }

        .scrollstyle::-webkit-scrollbar
        {
            width: 6px;
            background-color:white;
        }

        .scrollstyle::-webkit-scrollbar-thumb
        {
            border-radius: 10px;
            -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
            background-color: #129fea;
        }
        .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
        .lvjing{
            position: fixed;

            top:0px;
            left:0px;
            width:100%;
            height:100%;
            background:rgba(0,0,0,0.7);
            display: none;
            z-index: 500;
        }

    </style>
</head>
<body>
<div class="navbar-wrapper">
    <div class="container">

        <nav class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand " href="#"><img src="img/hncj.png"  alt="logo" width="30" height="30">&nbsp;河南城建种田学院</a>
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>

                </div>
                <div id="navbar" class="navbar-collapse collapse  navbar-right ">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">首页</a></li>
                        <li><a href="">种油菜指南</a></li>
                        <li><a href="">种花生指南</a></li>
                         <li>
                        <form class="navbar-form navbar-left" role="search">
						  <div class="form-group">
						    <input type="text" class="form-control" placeholder="Search">
						  </div>
						  <button type="submit" class="btn btn-success glyphicon glyphicon-search"></button>
						</form>
                            
                        </li>
                        <li ><a href="javascript:void(0);" style="color:white;"><i class="fa fa-user-circle" aria-hidden="true"></i>&nbsp;Welcome <span style="font-size:16px;">${sessionScope.user.username }</span></a></li>
                    <li class="" id="loginOut"><a href="javascript:void(0);" ><i class="fa fa-power-off" aria-hidden="true"></i>&nbsp; <span style="font-size:16px;">注销</span></a></li>
                    
                    
                       
                    </ul>
                </div>
            </div>
        </nav>

    </div>
</div>
<!--主体内容-->
<div class="container-fluid " style="height:auto; margin-top:100px;">
     <div class="row">
         <div class="col-xs-3" id="booktrees">
         <form action="#">
                 <div class="form-group">
                     <label for="parentsNode" class="sr-only">增加父节点</label>
                     <div class="input-group">
                         <input type="text" name="AddParentsNodes" placeholder="添加父节点" id="AddParentsNodes" class="form-control"/>
                         <div class="input-group-addon " id="AddBtn" ><span class="glyphicon glyphicon-plus" ></span></div>
                     </div>
                 </div>
             </form>
         

             <ul id="treeDemo" class="ztree" >
                 <div id="rMenu" style="z-index:100;">
                     <ul>
                         <li id="m_add" onclick="addTreeNode();">新增节点</li>
                         <li id="m_del" onclick="removeTreeNode();">删除节点</li>
                         <li id="m_edit" onclick="editTreeNode();" style="border-bottom:1px solid #cecece">编辑节点</li><li id="m_left">升级</li>
                         <li id="m_right">降级</li>
                         <li id="m_up">上移</li>
                         <li id="m_down" style="border-bottom:1px solid #cecece">下移</li>
                         <li id="m_reset" onclick="resetTree();">重置节点</li> <li id="m_open" onclick="treeOpen()">展开所有</li>
                         <li id="m_stop" onclick="treeStop()">收起所有</li>
                     </ul>
                 </div>
             </ul>
         </div>
       <div class="col-xs-8 " id="contents">
                 <div class="operations" >
                                                                        行多远方，为执着，思多久，方为远见。
                 </div>

                 <div class="text scrollstyle" id="text">

                 </div>

             <div class="operationModify" class="col-xs-12">
                 <button id="deleteContent" class="btn btn-danger">删除文章</button>
                 <button id="ModifyContent" class="btn btn-success">修改文章</button>
             </div>
         </div>
     </div>
</div>
<!--powerby-->
<section >
    <div class="container" id="footer">
        <div class="row">
            <div class="col-xs-offset-2 col-xs-2">
                <a  title="分享" href="javascript:void(0);"><i class="fa fa-telegram fa-2x" aria-hidden="true"></i></a>
            </div>
            <div class="col-xs-2">
                <a href="http://www.ituite.com/"><i class="fa fa-twitter-square fa-2x" aria-hidden="true"></i></a>
            </div>
            <div class="col-xs-2">
                <a href="https://github.com/maliaoMJ/" title="my github" ><i class="fa fa-github fa-2x" aria-hidden="true"></i></a>

            </div>
            <div class="col-xs-2"><!-- http://wpa.qq.com/msgrd?v=3&uin=1789769971&site=qq&menu=yes -->
                <a target="_blank" title="QQ" href="tencent://message/?uin=904822026&Site=QQ交谈&Menu=yes"><i class="fa fa-qq fa-x" aria-hidden="true">
                </i></a>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-offset-2 col-xs-8" id="power">
                <p>Copyright ©2015-2017  Powered By @xxxxxx </p>
            </div>
        </div>
    </div>
</section>
<!--组件开发-->
<div class="lvjing">
<div id="modifyContentsArea" style="display:none;width:80%;height:550px;color:white;border-radius:10px;background:gray;position:absolute; top:10%;left:10%;">
    <h3 style="text-align:center;">修改文章内容</h3><br/>
    <div style="width: 90% ;margin:10px auto;">
        <div class="form-group">

            <textarea cols="30" rows="15"  class="form-control"  id="modifyContents" placeholder="content"></textarea>

        </div>
        <div class="form-group" style="text-align: center">
            <button class="btn btn-success" id="ModifyBtn" style="padding-right:10px; ">保存</button>
            <button class="btn btn-danger" id="ModifyClose" style="padding-left:10px; ">取消</button>
        </div>



    </div>
</div>
<div id="addNodeArea" style="display:none;width:600px;height:200px;color:white;border-radius:10px;background:gray;position:absolute; top:50%;left:50%;margin-left:-300px;margin-top:-150px;">
    <h3 style="text-align:center;">添加目录节点</h3>
    <div style="width: 80% ;margin:10px auto;">
        <div class="form-group">
            <label for="">节点名称:</label>
            <input type="text"  id="addNodeName" class="form-control" placeholder="NodeName"/>
        </div>
        <div class="form-group" style="text-align: center">
            <button class="btn btn-success" id="AddNode" style="padding-right:10px; ">提交</button>
            <button class="btn btn-danger" id="AddNodeClose" style="padding-left:10px; ">关闭</button>
        </div>
    </div>
</div>

    <div class="addText" id="addText" style="display:none;width:80%;height:550px;color:white;border-radius:10px;background:gray;position:absolute; top:10%;left:10%;">
        <h3 style="text-align:center;">添加文章</h3>
        <div style="width: 86% ;margin:10px auto;">


            <div class="form-group">
                <br/>
                <textarea cols="40"  rows="10"   id="addContent" placeholder="content"></textarea>

            </div>
            <div class="form-group" style="text-align: center">
                <button class="btn btn-success" id="CreateAdd" style="padding-right:10px; ">提交</button>
                <button class="btn btn-danger" id="CreateClose" style="padding-left:10px; ">关闭</button>
            </div>
        </div>

    </div>
</div>



</body>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.js"></script>
<!-- <script type="text/javascript" src="http://cdn.cdnjs.net/zTree.v3/3.5.24/js/jquery.ztree.all.js"></script> -->
<script type="text/javascript" src="js/jquery.ztree.all.js"></script>
<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>

<script type="text/javascript" src="js/wow.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
</html>