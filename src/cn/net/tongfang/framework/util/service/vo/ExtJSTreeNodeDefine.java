package cn.net.tongfang.framework.util.service.vo;

import java.util.ArrayList;
import java.util.List;

public class ExtJSTreeNodeDefine {
	/*
	 * [ { "text" : "登记信息管理", "id": 'regMgr', "leaf" : false, "cls" : "folder",
	 * "children" : [ {"text" : "个人预登记信息审核", "id": '/js/app/ireg.js', "leaf" :
	 * true, "cls" : "file"}, {"text" : "个人预登记信息查询", "id": 'individualQry.js',
	 * "leaf" : true, "cls" : "file"} ] },
	 */

	private String text, id, cls;
	private boolean leaf,checked;
	
	private List<ExtJSTreeNodeDefine> children = new ArrayList<ExtJSTreeNodeDefine>();

	private Object data;

	public ExtJSTreeNodeDefine(String text, String id, String cls, boolean leaf,boolean checked) {
		this.text = text;
		this.id = id;
		this.cls = cls;
		this.leaf = leaf;
		this.checked = checked;
	}
	public ExtJSTreeNodeDefine(String text, String id, String cls, boolean leaf,boolean checked, Object data) {
		this.text = text;
		this.id = id;
		this.cls = cls;
		this.leaf = leaf;
		this.checked = checked;
		this.data = data;
	}
	public ExtJSTreeNodeDefine(String text, String id, boolean leaf,boolean checked, Object data) {
		this.text = text;
		this.id = id;
		this.leaf = leaf;
		if ( leaf ) {
			this.cls = "file";
			this.checked = checked;
		} else {
			this.cls = "folder";
		}
		this.data = data;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public List<ExtJSTreeNodeDefine> getChildren() {
		return children;
	}

	public void setChildren(List<ExtJSTreeNodeDefine> children) {
		this.children = children;
	}

	public void addChild(ExtJSTreeNodeDefine node) {
		this.children.add(node);
	}

	public void addChildren(List<ExtJSTreeNodeDefine> children) {
		this.children.addAll(children);
	}

}
