package cn.net.tongfang.framework.security.vo;

/**
 * SamModuleCategory entity. @author MyEclipse Persistence Tools
 */

public class SamModuleCategory {

	// Fields

	private String id;
	private String name;
	private Integer displayOrder;
	private String clsSetting;
	private String parentId;
	private String parentName;
	private Integer level;
	private Boolean isDetail;
	private String templateId;
	// Constructors

	/** default constructor */
	public SamModuleCategory() {
	}

	/** full constructor */
	public SamModuleCategory(String id, String name, Integer displayOrder,
			String clsSetting, String parentId, Integer level, Boolean isDetail,
			String parentName,String templateId) {
		super();
		this.id = id;
		this.name = name;
		this.displayOrder = displayOrder;
		this.clsSetting = clsSetting;
		this.parentId = parentId;
		this.level = level;
		this.isDetail = isDetail;
		this.parentName = parentName;
		this.templateId = templateId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getClsSetting() {
		return clsSetting;
	}

	public void setClsSetting(String clsSetting) {
		this.clsSetting = clsSetting;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getIsDetail() {
		return isDetail;
	}

	public void setIsDetail(Boolean isDetail) {
		this.isDetail = isDetail;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
}