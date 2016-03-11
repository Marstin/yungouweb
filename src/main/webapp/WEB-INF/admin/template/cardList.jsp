<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>1元拍购_管理中心</title>
	<link href="/admin_css/global.css"  rel="stylesheet" type="text/css"/>
	<link href="/admin_css/admin_style.css"  rel="stylesheet" type="text/css"/>
	<script language="javascript" type="text/javascript" src="/js/jquery-1.4.4-min.js"></script>
  </head>
  
  <body>
  <div class="bk10"></div>
  <div class="header-data lr10">
  卡密充值成功后自动销毁，请及时生成卡密。
  </div>
  <div class="bk10"></div>
<div class="table-list lr10">
<table width="100%" cellspacing="0">
<thead>
<tr>
<th width="10%">卡密</th>
<th width="5%">面值金额</th>
<th width="5%">生成时间</th>
<th width="7%">操作</th>
</tr>
</thead>
<tbody>
<s:iterator value="cardpasswordList" var="cardpasswords" status="st">
<tr >
	<td align="left" style="padding-left: 5px;font-size: 14px;">${cardpasswords.randomNo }</td>
	<td align="center">${cardpasswords.money }</td>
	<td align="center">${cardpasswords.date }</td>
	<td align="center">
		<a name="toDelete" ids="${cardpasswords.id }" href="javascript:;">删除</a>
	</td>
</tr>
</s:iterator>
</tbody>
</table>
</div>
 	${pageString }
<script language="javascript" type="text/javascript">
$("[name='toDelete']").click(function(){
	if(confirm("确定要删除该卡密吗？"))
	{
	   	var id = $(this).attr("ids");
	   	var burl = document.URL;
		window.location.href = "/admin_back/deleteCard.action?id="+id+"&backUrl="+burl;  
	}
	
});
</script>
  </body>
</html>
