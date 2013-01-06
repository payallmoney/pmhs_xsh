package cn.net.tongfang.web.service.bo;
public class ListExamBeanBO {
	private String id;
	private String fileNo;
	private String name;
	private String examDate;
	private String inputPersonId;
	private String inputPersonName;
	private String orgName;
	private String flag;
	
	public ListExamBeanBO() {
		super();
	}

	public ListExamBeanBO(String id, String fileNo, String name, String examDate,
			String inputPersonId, String inputPersonName, String orgName,
			String flag) {
		super();
		this.id = id;
		this.fileNo = fileNo;
		this.name = name;
		this.examDate = examDate;
		this.inputPersonId = inputPersonId;
		this.inputPersonName = inputPersonName;
		this.orgName = orgName;
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getInputPersonId() {
		return inputPersonId;
	}

	public void setInputPersonId(String inputPersonId) {
		this.inputPersonId = inputPersonId;
	}

	public String getInputPersonName() {
		return inputPersonName;
	}

	public void setInputPersonName(String inputPersonName) {
		this.inputPersonName = inputPersonName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
