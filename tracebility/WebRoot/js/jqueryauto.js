//自动补全框最开始应该隐藏起来
//表示当前高亮的节点
var highlightindex = -1;
var timeoutId;
$(document).ready(function(){
	var wordInput = $("#word");
	var wordInputoffset = wordInput.offset();
	$("#auto").hide().css("border","1px black solid").css("position","absolut")
	    .css("top",wordInputoffset.top + wordInput.height() + 5 + "px")
	    .css("left",wordInputoffset.left + "px")
	    .width(wordInput.width() + 2);
	
	//给文本框添加键盘按下并弹起的时间
	wordInput.keyup(function(event) {
		//处理文本框中的键盘事件
		 var myEvent = event || window.event;
	     var keyCode = myEvent.keyCode;
		//如果输入的是字母，应该将文本框中的最新的信息发送给服务器
	     //如果输入的是退格键（键值是8）或者删除键（键值是46），应该将文本框中的最新的信息发送给服务器
		if(keyCode >=65 && keyCode <=90 || keyCode == 8 || keyCode == 46) {
			
		//1.获取文本框里的内容
		var wordText = $("#word").val();
		var autoNode = $("#auto");
		if(wordText != "") {
		//2.将文本框的内容发送给服务器
		//对上次未完成的延时操作进行取消
			clearTimeout(timeoutId);
		//对于服务器端进行延迟500毫秒，避免快速打字造成的频繁请求服务器
	    timeoutId = setTimeout(function(){
		$.post("AutoComplete",{word:wordText},function(data) {
		//将dom对象data转换成jquery对象
			var jqueryObj = $(data);
			//找到所有的word节点
			var wordNodes = jqueryObj.find("word");
			//遍历所有的word节点，取出单词内容，然后将单词内容添加到弹出框中
			//需要清空原来的内容
            autoNode.html("");
			wordNodes.each(function(i){
				//获取单词内容
				var wordNode = $(this);
				//新建div节点，将单词内容加入到新建的节点中
				//将新建的节点加入到弹出框节点中
				var newDivNode = $("<div>").attr("id",i);
				newDivNode.html(wordNode.text()).appendTo(autoNode);
               //增加鼠标进入事件,高亮节点
				newDivNode.mouseover(function() {
					//将原来高亮的节点取消高亮
					if(highlightindex != -1) {
						$("#auto").children("div").eq(highlightindex)
						  .css("background-color","white");
					}
					//记录新的高亮索引
					highlightindex	= $(this).attr("id");
					//鼠标进入的节点高亮
				    $(this).css("background-color","red");
				});
				//增加鼠标移除事件,取消当前节点的高亮
			   newDivNode.mouseout(function() {
				   //取消鼠标移除节点的高亮
				   $(this).css("background-color","white");
			   });
			  //增加鼠标点击事件，可以进行补全
				newDivNode.click(function() {
					//取出高亮节点的内容
					  var comTest = $(this).text();
					  $("#auto").hide();
					  highlightindex = -1; 	
					  //文本框中的内容变成高亮节点的内容
					  $("#word").val(comTest); 
				});
				
			});
			//如果服务器端有数据返回，则显示弹出框
			if(wordNodes.length > 0) {
				autoNode.show();	
			}else {
				autoNode.hide();
				//弹出框隐藏的同时，高亮节点的值也至成-1
				highlightindex = -1;
			}
		},"xml");
  },500);
	} else {
		autoNode.hide();
		highlightindex = -1;
	}
	  }else if(keyCode ==38 || keyCode == 40){
		  //如果输入的是向上38或向下40键
		  if(keyCode == 38) {
			  //向上键
			  var autoNodes = $("#auto").children("div");
			  if(highlightindex != -1) {
				  //如果原来存在高亮节点，则背景颜色改成白色
				  autoNodes.eq(highlightindex).css("background-color","white");
				  highlightindex--;
			  }else {
				  highlightindex = autoNodes.length -1;
			  }
			  if(highlightindex == -1) {
				  //如果修改索引值以后index变成-1，则将索引值指向最后一个元素
				  highlightindex = autoNodes.length - 1;
			  }
			  // 让现在高亮的内容变成红色
			  autoNodes.eq(highlightindex).css("background-color","red");
		  }
		  if(keyCode == 40) {
			  //向下键
			  var autoNodes = $("#auto").children("div");
			  if(highlightindex != -1) {
				  //如果原来存在高亮节点，则背景颜色改成白色
				  autoNodes.eq(highlightindex).css("background-color","white");
			  }
			  highlightindex++;
			  if(highlightindex == autoNodes.length) {
				  //如果修改索引值以后index变成-1，则将索引值指向最后一个元素
				  highlightindex = 0;
			  }
			  // 让现在高亮的内容变成红色
			  autoNodes.eq(highlightindex).css("background-color","red");
		  }
	  }else if(keyCode == 13) {
		  //如果输入的是回车
		  
		  //下来框有高亮内容
		  if(highlightindex != -1) {
			  //取出高亮节点的内容
			  var comTest = $("#auto").hide().children("div").eq(highlightindex).text();
			  highlightindex = -1; 	
			  //文本框中的内容变成高亮节点的内容
			  $("#word").val(comTest);
		  }else {
			  //下拉框没有高亮内容
			  alert("文本框中的内容["+$("#word").val()+"]被提交了");
			  $("#auto").hide();
			  $("#word").get(0).blur();
		  }
	  }
	});
	//给按钮添加事件，表示文本框的内容被提交
	$("input[type='button']").click(function() {
		alert("文本框中的内容["+$("#word").val()+"]被提交了");
	});
	
})
