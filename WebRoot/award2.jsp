<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String weChatName = request.getParameter("weChatName");
%>
<!DOCTYPE HTML>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<style type="text/css">
	.demo{width:417px; height:417px; position:relative; margin:50px auto} 
	#disk{width:417px; height:417px; background:url(files/disk.jpg) no-repeat} 
	#start{width:163px; height:320px; position:absolute; top:46px; left:130px;} 
	#start img{cursor:pointer} 
</style>
<script type="text/javascript" src="files/jquery.min.js"></script>
<script type="text/javascript" src="files/jQueryRotate.2.2.js"></script>
<script type="text/javascript" src="files/jquery.easing.min.js"></script>
<script type="text/javascript">
$(function(){ 
    $("#startbtn").rotate({ 
        bind:{ 
            click:function(){//绑定click单击事件 
                 var a = Math.floor(Math.random() * 360); //生成随机数 
                 $("#disk").rotate({ 
                         duration:10000,//转动时间间隔（转动速度） 
                         angle: 0,  //开始角度 
                        animateTo:3600+a, //转动角度，10圈+ 
                        easing: $.easing.easeOutSine, //动画扩展 
                        callback: function(){ //回调函数 
                            alert('中奖了！'); 
                        } 
                 }); 
            } 
        } 
    }); 
}); 
</script>
</head>

<body>
	<div class="demo"> 
	    <div id="disk"></div> 
	    <div id="start"><img src="files/start.png" id="startbtn"></div> 
	</div> 
</body>
</html>