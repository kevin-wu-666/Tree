// 配置

var ModifyDeleteCont='';
var ModifyDeleteTreeNode='';
var demoMsg =$('#treeDemo');
var setting = {
    async: {
        enable: true,
        type:'get',
        url:"treeNode/getjson",
        dataFilter: filter,
        autoParam:["id", "name=n", "level=lv"],
		otherParam:{"otherParam":"zTreeAsyncTest"}
        
       
    },

    view: {
        expandSpeed:"",
        
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false

    },
    edit: {
        enable: true,
        removeTitle : "删除",
        renameTitle : "修改",
        showRemoveBtn : true,
        showRenameBtn : true
    },
    data: {
        simpleData: {
            enable: false
        }

    },
    check:{
        enable:false
    },
    callback: {
       onClick:getContent,
        beforeRemove:beforeRemove,
        beforeRename:beforeRename,
        beforeAsync: beforeAsync,
		onAsyncSuccess: onAsyncSuccess,
		onAsyncError: onAsyncError
        
    }
};
// 
function beforeAsync() {
			curAsyncCount++;
		}
		
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			curAsyncCount--;
			if (curStatus == "expand") {
				if(treeNode.children=='undefined'){
					return false;
				}else{
					expandNodes(treeNode.children);
				}
			} else if (curStatus == "async") {
				asyncNodes(treeNode.children);
			}

			if (curAsyncCount <= 0) {
				if (curStatus != "init" && curStatus != "") {
					$("#treeDemo").text((curStatus == "expand") ? demoMsg.expandAllOver : demoMsg.asyncAllOver);
					asyncForAll = true;
				}
				curStatus = "";
			}
		}

		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			curAsyncCount--;

			if (curAsyncCount <= 0) {
				curStatus = "";
				if (treeNode!=null) asyncForAll = true;
			}
		}

		var curStatus = "init", curAsyncCount = 0, asyncForAll = false,
		goAsync = false;
		function expandAll() {
			if (!check()) {
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			if (asyncForAll) {
				$("#treeDemo").text(demoMsg.expandAll);
				zTree.expandAll(true);
			} else {
				expandNodes(zTree.getNodes());
				if (!goAsync) {
					$("#treeDemo").text(demoMsg.expandAll);
					curStatus = "";
				}
			}
		}
		function expandNodes(nodes) {
			if (!nodes) return;
			curStatus = "expand";
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.expandNode(nodes[i], true, false, false);
				if (nodes[i].isParent && nodes[i].zAsync) {
					expandNodes(nodes[i].children);
				} else {
					goAsync = true;
				}
			}
		}

		function asyncAll() {
			if (!check()) {
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			if (asyncForAll) {
				$("#treeDemo").text(demoMsg.asyncAll);
			} else {
				asyncNodes(zTree.getNodes());
				if (!goAsync) {
					$("#treeDemo").text(demoMsg.asyncAll);
					curStatus = "";
				}
			}
		}
		function asyncNodes(nodes) {
			if (!nodes) return;
			curStatus = "async";
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			for (var i=0, l=nodes.length; i<l; i++) {
				if (nodes[i].isParent && nodes[i].zAsync) {
					asyncNodes(nodes[i].children);
				} else {
					goAsync = true;
					zTree.reAsyncChildNodes(nodes[i], "refresh", true);
				}
			}
		}
		function reset() {
			if (!check()) {
				return;
			}
			asyncForAll = false;
			goAsync = false;
			$("#treeDemo").text("");
			$.fn.zTree.init($("#treeDemo"), setting);
		}
         
		function check() {
			if (curAsyncCount > 0) {
				$("#treeDemo").text(demoMsg.async);
				return false;
			}
			return true;
		}

		





//生成唯一id
function getOnlyId(){
	var date=new Date();
    var timers=date.getTime();//时间戳为id 除非时间倒流
    return timers;
}



//添加父节点
$('#AddBtn').on('click', function (event) {
    //获取父节点的值
    var timerid=getOnlyId();
    if($('#AddParentsNodes').val()==null || $('#AddParentsNodes').val()==''){
        alert('节点名称不能为空');
        return false;
    }
    else{
        $.ajax({
          type:'get',
          url:'treeNode/addjson',
          data:{id:timerid , Pid:0 , name: $('#AddParentsNodes').val()},
          success:function(){
              alert('添加父节点成功');
              var zTree = $.fn.zTree.getZTreeObj("treeDemo"); 
               $.fn.zTree.init($("#treeDemo"), setting);
               $('#AddParentsNodes').val('');
                window.location.reload();
          },
          error:function(){
              alert('添加失败！');
          }

        });
    }
});


//过滤修改节点name
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i=0, l=childNodes.length; i<l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');

    }

    return childNodes;
}
//修改节点
function beforeRename(treeId, treeNode, newName) {
    if (newName.length == 0) {
        setTimeout(function () {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.cancelEditName();
            alert("文章名称不能为空.");
        }, 0);
        return false;
    }else{
    	
        ModifyNodeName(treeNode,newName);
    }
      
}
//修改节点
function ModifyNodeName(treeNode,newName){
      $.ajax({
            type:'get',
            url:"treeNode/modifyjson",
            data:{id:treeNode.id,name:newName},
            success: function (data) {
                alert("modify nodeName success");
                 window.location.reload();
                return true;
            },
            error: function () {
//                alert(treeNode.id+":"+newName);
                // 正常情况下成功。。
                alert("修改失败！");

            }
        });
}
// addNodes
function addNodes(treeNode){
	var newCount =getOnlyId();
	 $('.lvjing').css('display','block');
    $("#addNodeArea").css('display','block');
        $("#AddNodeClose").on('click',function(){
            $("#addNodeArea").css('display','none');
             $('.lvjing').css('display','none');
        });
        $('#AddNode').on('click',function(){
            var name=$('#addNodeName').val();
            //增加节点
            $.ajax({
                type:'get',
                url:'treeNode/addjson',
                data:{id:newCount, Pid:treeNode.id, name:name},
                success:function(data){
                     //view addNode
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    
                   
                    zTree.addNodes(treeNode, {id:newCount, pId:treeNode.id, name:name});
                     $.fn.zTree.init($("#treeDemo"), setting);
                   
                    $("#addNodeArea").css('display','none');
                     $('.lvjing').css('display','none');
                      window.location.reload();
                },
                error:function(err){
//                    alert("增加节点失败1！"); 
                 
                }
            });

        });
}

//添加节点或是文章内容

function addHoverDom(treeId, treeNode) {        // url treeNode/addjson (id parentid name)
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='增加' '></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(event){
//        alert(treeId+":"+treeNode.id+":"+treeNode.pid);
        
        
        // 判断是对节点操作还是文章
         $.ajax({
        type:'get',
        url:'treeNode/confirm',
        data:{Pid:treeNode.id},
        success:function(flag){
           var flag=flag;
         
            if(flag=="isContent:true"){
            	//判断该节点下是否有文章
            	$.ajax({
            		type:'get',
            		url:'treeContent/getContent',
            		data:{id:treeNode.id},
            		success:function(data){
                      var content=data;
                      
                      if(content.length==0 || content==''){
                      	alert('该节点下没有文章，接下进行添加文章操作');
                      
                        addContent(treeNode,content); //增添文章
                      }else{
                      	
                      	alert('该节点下已经有文章，禁止添加！');
                      
                      	return false;
                      }
            		},
            		error:function(error){
                     console.log('添加文章判断是否该节点有文章的过程失败!');
            		}
            	});
            	
            }else{
            	
                 addNodes(treeNode);//添加节点
            }

        },
        error:function(error){
            return false;
        }
    });
        
          event.stopPropagation();
        });



}
//添加文章
function addContent(treeNode,content){
		var newCount =getOnlyId();
	$('.lvjing').css('display','block');
    $("#addText").css('display','block');
    CKEDITOR.instances.addContent.setData(content);
    $("#CreateClose").on('click', function () {
        $("#addText").css('display','none');
        $('.lvjing').css('display','none');
    });
    $("#CreateAdd").on('click', function () {
   
        var addContents=CKEDITOR.instances.addContent.getData();
        if((addContents==''||addContents==null)){
            alert("必填项不能为空！");
        }else{
            $.ajax({
                type:"get",
                url:"treeContent/addContent",
                data:{id:treeNode.id,content:addContents},
                success:function(){
                	
                   alert('addcontents success');
                    
                },
                error:function(error){
                    console.log(error);
                }

            });
                    $.fn.zTree.init($("#treeDemo"), setting);
                    $("#addText").css('display','none');
                    $('.lvjing').css('display','none');
                    $('#text').html(addContents);
                   window.location.reload();

                
                   
        }
    });

}

//删除
function beforeRemove(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    var flags = confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
    if (flags) {
        // 数据库————后台删除操作
        $.ajax({
            // url treeNode/deletejson(id)
            type: "get",
            url: "treeNode/deletejson",
            data: {id: treeNode.id},
            success: function (deleteData) {
                $.fn.zTree.init($("#treeDemo"), setting);
                 window.location.reload();
            },
            error: function (error,textStatus) {
                alert('删除失败！'+textStatus);
//                $.fn.zTree.init($("#treeDemo"), setting);
                return false;
            }
        });
    }else{
        return false;
    }
}
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
}
//通过ID获取查看文章的数据
function getContent(event,treeId,treeNode){
	 
     console.log("是否为父节点"+treeNode.isParent);
     //判断节点是否为父节点 如果不为父节点 通过ID 获取文章内容
      if(!treeNode.isParent){
              $.ajax({
                type:'get',
                url:'treeNode/confirm',
                data:{Pid:treeNode.id},
                success:function(flag){
                   var flag=flag;
                   console.log(flag);
                    if(flag=="isContent:true"){
                         $.ajax({
                              type:"get",
                              url:"treeContent/getContent",
                              data:{id:treeNode.id},
                              success:function(contents){
                                  var content=contents; 
                                         
                                        
                                 
                                  if(content.length==19 || content==''){
                                      alert('此节点下没有文章');
                                        ModifyDeleteCont='';
                                        ModifyDeleteTreeNode=treeNode.id;
                                        $('#text').html('');
                                     


                                  }else{
                                  	// alert('获取到文章数据，可以对文章进行操作');
                                  	console.log("获取的文章内容"+content);
                                      $('.operations').html(treeNode.name);
                                      showContent(content);
                                      ModifyDeleteCont=content;
                                     ModifyDeleteTreeNode=treeNode;
                                     console.log('点击节点treeNode'+treeNode.id);
                                     event.stopPropagation(); 
                                event.preventDefault();

                                  }
                              },
                              error:function(error){
                                  console.log('getContent  error');
                              }
                          });

                    }
                },
                error:function(error){
                    return false;
                }
                
    });

      }else{
          return false;
      }




};
// 删除事件
$('#deleteContent').on('click',function(){
   if(ModifyDeleteCont.length==19 || ModifyDeleteCont==''){
   	alert('当前目录下没有文章，无法进行删除操作！');
   }else{
   	var flagdelete=confirm('您确定删除该文章？此过程不可逆');
   
   	    DeleteContents(ModifyDeleteTreeNode,flagdelete);
   	    
   }
		
});
//修改事件
$('#ModifyContent').on('click',function(){
	if(ModifyDeleteCont.length==19 || ModifyDeleteCont==''){
   	alert('当前目录下没有文章，无法进行修改操作！');
   }else{
   	
   
   	    
   	    modifyContent(ModifyDeleteTreeNode,ModifyDeleteCont)
   }
      ;                               	
                                     	
 });





//查看文章把返回的数据放到html
function showContent(content){
	$('#text').html('');
    $("#text").html(content);
}

//删除文章
  function DeleteContents(treeNode,flagdelete){
       
      
     if(flagdelete){
     	 $('#text').html();
         $.ajax({
	     	type:'get',
	     	url:'treeContent/deleteContent',
	     	data:{id: treeNode.id},
	     	success:function(data){
	          alert('删除文章成功!');
	          $('#text').html('');
	           window.location.reload();
	         
	     	},
	     	error:function(err){
//	          alert('删除文章失败！！！');
             $('#text').html(''); 
	     	}
	     });
	 $('#text').html('');  
	 
     }else{
     	return flase;
     }
    
  }

//修改文章
function modifyContent(treeNode,content){
	       var getContent=$('#text').html();
	       ;
           //展现平面
            $('.lvjing').css('display','block');
            $('#modifyContentsArea').css('display','block');
            $('#ModifyClose').on('click', function () {
            	$('.lvjing').css('display','none');
                $('#modifyContentsArea').css('display','none');
            });
            //把文章内容放到平面上
            // var content=CKEDITOR.instances.modifyContents.getData();  //取值
            
            CKEDITOR.instances.modifyContents.setData(getContent); 
            
            //update
            $('#ModifyBtn').on('click', function (event) {
                var updateContents=CKEDITOR.instances.modifyContents.getData();
                if(updateContents==''||updateContents==null){
                    alert('修改的内容不能为空!  ');
                }else{
                    $.ajax({
                        type:'get',
                        url:'treeContent/modifyContent',
                        data:{id:treeNode.id,content:updateContents},
                        success: function (data) {
                            
                            if(data.toString()=='1'){
                            	
                            	alert('文章内容修改成功！');
                                console.log('文章内容修改成功！');
			                    $('#modifyContentsArea').css('display','none');
			                    $('.lvjing').css('display','none');
			                    $('#text').html(updateContents);
			                  
			                    console.log(data);
			                    window.location.reload();

			                    
                            }
                            

                        },
                        error: function (err) {
//                            alert('修改数据失败--插入数据库');
                        }
                    });

                }
                
            });
             
}

//



//-----------------------------------

$(document).ready(function(){
    //动画
    var wow = new WOW({
        boxClass: 'wow',
        animateClass: 'animated',
        offset: 0,
        mobile: true,
        live: true
    });
    wow.init();
    //动画结束
    CKEDITOR.replace('addContent');
    CKEDITOR.replace('modifyContents');
 

    //zTree 初始化目录节点

          $.fn.zTree.init($("#treeDemo"), setting);
            //
            setTimeout(function(){
            	
            	expandAll();
            },0);

    // ztree 目录初始化节点结束

// loginOut
$('#loginOut').on('click',function(){
if(confirm('确定注销？')){
//		window.location.href="http://localhost:8080/TreeView-Struts/login.jsp";
		location.href="user/logout";
		alert("注销成功！");
}else{
	return false;
}
});
});