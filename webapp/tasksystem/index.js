function loadHtml(url,selector){
	console.log(url,selector);
}

var setting = {
	check: {
		enable: true,
		chkboxType: { "Y" : "ps", "N" : "ps" }
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};

var zNodes =[
	{ id:1, pId:0, name:"展开、折叠 自定义图标不同", open:true, iconOpen:"../assets/zTree/css/zTreeStyle/img/diy/1_open.png", iconClose:"../assets/zTree/css/zTreeStyle/img/diy/1_close.png"},
	{ id:11, pId:1, name:"叶子节点1",open:false,icon:"../assets/zTree/css/zTreeStyle/img/diy/1_close.png",  iconOpen:"../assets/zTree/css/zTreeStyle/img/diy/1_open.png", iconClose:"../assets/zTree/css/zTreeStyle/img/diy/1_close.png"},
	{ id:12, pId:1, name:"叶子节点2",open:false,  iconOpen:"../assets/zTree/css/zTreeStyle/img/diy/1_open.png", iconClose:"../assets/zTree/css/zTreeStyle/img/diy/1_close.png"},
	{ id:13, pId:1, name:"叶子节点3",open:false,  iconOpen:"../assets/zTree/css/zTreeStyle/img/diy/1_open.png", iconClose:"../assets/zTree/css/zTreeStyle/img/diy/1_close.png"},
	{ id:2, pId:0, name:"展开、折叠 自定义图标相同", open:true, iconOpen:"../assets/zTree/css/zTreeStyle/img/diy/1_open.png", iconClose:"../assets/zTree/css/zTreeStyle/img/diy/1_close.png"},
	{ id:21, pId:2, name:"叶子节点1", icon:"../assets/zTree/css/zTreeStyle/img/diy/6.png"},
	{ id:22, pId:2, name:"叶子节点2", icon:"../assets/zTree/css/zTreeStyle/img/diy/7.png"},
	{ id:23, pId:2, name:"叶子节点3", icon:"../assets/zTree/css/zTreeStyle/img/diy/8.png"},
	{ id:3, pId:0, name:"不使用自定义图标", open:true },
	{ id:31, pId:3, name:"叶子节点1"},
	{ id:32, pId:3, name:"叶子节点2"},
	{ id:34, pId:3, name:"叶子节点3"},
	{ id:35, pId:3, name:"叶子节点3"},
	{ id:36, pId:3, name:"叶子节点3"},
	{ id:37, pId:3, name:"叶子节点3"},
	{ id:38, pId:3, name:"叶子节点3"},
	{ id:39, pId:3, name:"叶子节点3"},
	{ id:40, pId:3, name:"叶子节点3"},
	{ id:41, pId:3, name:"叶子节点3"},
	{ id:42, pId:3, name:"叶子节点3"},
	{ id:43, pId:3, name:"叶子节点3"},
	{ id:44, pId:3, name:"叶子节点3"},
	{ id:45, pId:3, name:"叶子节点3"},
	{ id:46, pId:3, name:"叶子节点3"},
	{ id:47, pId:3, name:"叶子节点3"},
	{ id:48, pId:3, name:"叶子节点3"},
	{ id:49, pId:3, name:"叶子节点3"},
	{ id:50, pId:3, name:"叶子节点3"},
	{ id:51, pId:3, name:"叶子节点3"},
	{ id:52, pId:3, name:"叶子节点3"},
	{ id:53, pId:3, name:"叶子节点3"},
	{ id:54, pId:3, name:"叶子节点3"},
	{ id:55, pId:3, name:"叶子节点3"},
	{ id:56, pId:3, name:"叶子节点3"},
	{ id:57, pId:3, name:"叶子节点3"},
	{ id:58, pId:3, name:"叶子节点3"},
	{ id:59, pId:3, name:"叶子节点3"},
	{ id:60, pId:3, name:"叶子节点3"},
	{ id:61, pId:3, name:"叶子节点3"},
	{ id:62, pId:3, name:"叶子节点3"},
	{ id:63, pId:3, name:"叶子节点3"},
	{ id:64, pId:3, name:"叶子节点3"},
	{ id:65, pId:3, name:"叶子节点3"},
	{ id:66, pId:3, name:"叶子节点3"},
	{ id:67, pId:3, name:"叶子节点3"}

];

$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});
