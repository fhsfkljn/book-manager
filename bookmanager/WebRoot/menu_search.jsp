<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/my.js">
	
</script>
<script type="text/javascript">
	window.onload = function(){
		//得到搜索框对象
		var searchElement = document.getElementById("name");
		//得到DIV元素
		var div = document.getElementById("context1");
		
		searchElement.onkeyup = function(){//给文件框注册按键弹起事件
			//获取文本框的值
			var name = this.value;
			//如果文本框不没有数据时，把div隐藏，且不向服务器发送请求
			if(name==""){
				div.style.display="none";
				return;
			} 
			
			//获得xhr对象
			var xhr = getXMLHttpRequest();
			
			//处理结果
			xhr.onreadystatechange = function(){
				if(xhr.readyState==4){//请求一 切正常
					if(xhr.status==200){//服务器响应一切正常
						var str = xhr.responseText;//得到服务器返回的数据
					
						var ss = str.split(","); // 把字符串 1001，1002，1003 截成数组
						var childDivs = "";
						//循环把数据放入小的div中
						for(var i=0;i<ss.length;i++){
							childDivs+="<div onclick='writeText(this)' onmouseover='changeBackground_over(this)' onmouseout='changeBackground_out(this)'>"+ss[i]+"</div>";//把数组中的每个元素放到div中
						}
						
						div.innerHTML= childDivs;//把多个childDivs（div）放入列表div中
						div.style.display="block";//把列表隐藏
					}
				}
			}
			
			xhr.open("get","${pageContext.request.contextPath}/servlet/searchBookAJAXServlet?name="+name+"&time="+new Date().getTime());
			
			xhr.send(null);
		}
	}
	
	//鼠标悬浮时，改变背景色
	function changeBackground_over(div){
		div.style.backgroundColor = "gray";
	}
	//鼠标离开时，恢复背景色
	function changeBackground_out(div){
		div.style.backgroundColor = "";
	}
	
	//填充文本到搜索框
	function writeText(div){
		//得到搜索框
		var searchElement = document.getElementById("name");
		searchElement.value = div.innerHTML;//把div中的文本添加到搜索框中
		div.parentNode.style.display="none";//把context1的div隐藏
	}
	
</script>

<div id="divmenu">
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=文学">文学</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=生活">生活</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=计算机">计算机</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=外语">外语</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=经营">经管</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=励志">励志</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=社科">社科</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=学术">学术</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=少儿">少儿</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=艺术">艺术</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=原版">原版</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=科技">科技</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=考试">考试</a>
	<a
		href="${pageContext.request.contextPath}/showProductByPage?category=生活百科">生活百科</a>
	<a href="${pageContext.request.contextPath}/showProductByPage"
		style="color:#FFFF00">全部商品目录</a>
</div>
<div id="divsearch">
	<form action="${pageContext.request.contextPath}/findProductBySearch"
		method="post">
		<table width="100%" border="0" cellspacing="0">
			<tr>
				<td style="text-align:right; padding-right:220px">
				Search <input
					type="text" name="name" class="inputtable"
					id="name" /> 
					<input type="image" src="images/serchbutton.gif"
					border="0" style="margin-bottom:-4px">
				</td>
			</tr>
		</table>

	</form>
</div>
	<div id="context1" style="display:block;border:1px solid red;background-color:white; width:128px;position:absolute;left:1113px;top:135px;">
</div>