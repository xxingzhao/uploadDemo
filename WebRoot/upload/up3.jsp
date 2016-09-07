<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>多文件上传</title>
    

  </head>
  
  <body>
        <form name="multidata" action='<c:url value="/Up3Servlet'"/>' method="post" enctype="multipart/form-data">
             	<table id="tb" border="1">
             	 <tr>
             	    <td>File:</td>
             	    <td>
             	         <input type="file" name="file">
             	         <button onclick="_del(this)">删除</button>
             	    </td>
             	 </tr>
             	</table>
             	<br/>
             	<input type="button" value="上传"  onclick="_submit()">
             	<input type="button" value="增加"  onclick="_add()">
        </form>
  </body>
  <script type="text/javascript">
  
        function _add(){
        	var tb = document.getElementById("tb");
        	//写入一行
        	var tr = tb.insertRow();
        	//写入列
        	var td = tr.insertCell();
        	//写入数据
        	td.innerHTML = "File:";
        	//再声明一个新的td
        	var td2 = tr.insertCell();
        	//写入一个inpt
        	td2.innerHTML = '<input type="file" name="file"><button onclick="_del(this)">删除</button>';
        }
        
        function _del(btn){
        	var tr = btn.parentNode.parentNode;
        	var index = tr.rowIndex;
        	var tb = document.getElementById("tb");
        	tb.deleteRow(index);
        }
        
        function _submit(){
        	var files = document.getElementsByName("file");
        	if(files.length==0){
        		alert("没有可以上传的文件!");
        		return false;
        	}
        	
        	for(var i=0;i<files.length;i++){
        		if(files[i].value==""){
        			alert("第"+(i+1)+"文件不能为空!");
        			return false;
        		}
        	}
        	document.forms["multidata"].submit();
        }
  </script>
</html>
