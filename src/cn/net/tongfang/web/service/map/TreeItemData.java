package cn.net.tongfang.web.service.map;

import java.util.*;

public class TreeItemData {
	private String id;

	private String parentid;

	private String estateseriallayer;

	private String text; // �ڵ���

	private String children; // ���ڵ�

	private String state; // ���ڵ�

	private String levels;

	private String initCount;

	private String succCount;

	public String getInitCount() {
		return initCount;
	}

	public void setInitCount(String initCount) {
		this.initCount = initCount;
	}

	public String getSuccCount() {
		return succCount;
	}

	public void setSuccCount(String succCount) {
		this.succCount = succCount;
	}

	public String getEstateseriallayer() {
		return estateseriallayer;
	}

	public void setEstateseriallayer(String estateseriallayer) {
		this.estateseriallayer = estateseriallayer;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public static String treeItemContent(String id, String text, String levels,
			String initCount, String successCount) {
		StringBuffer treeItem = new StringBuffer();
		treeItem.append("{");
		treeItem.append("\"id\":\"" + id + "\"");
		treeItem.append(",");
		treeItem.append("\"text\":\"" + text + "-" + initCount + "-"
				+ successCount + "\"");
		if (levels.equals("5")) {
			treeItem.append(",");
			treeItem.append("\"state\":\"open\"");
		} else {
			treeItem.append(",");
			treeItem.append("\"state\":\"closed\"");

		}
		treeItem.append(",");
		treeItem.append("\"attributes\":" + "{\"levels\":\"" + levels + "\"}");
		treeItem.append("},");

		return treeItem.toString();

	}

	public static String positionTreeItemContent(String id, String text,
			String levels) {
		StringBuffer treeItem = new StringBuffer();
		treeItem.append("{");
		treeItem.append("\"id\":\"" + id + "\"");
		treeItem.append(",");
		treeItem.append("\"text\":\"" + text + "\"");
		if (levels.equals("5")) {
			treeItem.append(",");
			treeItem.append("\"state\":\"open\"");
		} else {
			treeItem.append(",");
			treeItem.append("\"state\":\"closed\"");

		}
		treeItem.append(",");
		treeItem.append("\"attributes\":" + "{\"levels\":\"" + levels + "\"}");
		treeItem.append("},");

		return treeItem.toString();

	}

}
