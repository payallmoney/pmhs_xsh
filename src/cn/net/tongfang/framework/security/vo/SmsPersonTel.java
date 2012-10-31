package cn.net.tongfang.framework.security.vo;

import cn.net.tongfang.framework.util.EncryptionUtils;

/**
 * SmsPersonTel entity. @author MyEclipse Persistence Tools
 */

public class SmsPersonTel implements java.io.Serializable {

	// Fields

	private String id;
	private String fileNo;
	private Integer filetype;
	private String name;
	private String tel;
	private String updateCol;
	private Integer updatetype;

	// Constructors

	/** default constructor */
	public SmsPersonTel() {
	}

	/** full constructor */
	public SmsPersonTel(String fileNo, Integer filetype, String name,
			String tel, String updateCol, Integer updatetype) {
		this.fileNo = fileNo;
		this.filetype = filetype;
		this.name = name;
		this.tel = tel;
		this.updateCol = updateCol;
		this.updatetype = updatetype;
	}
	
	public SmsPersonTel(SmsPersonTel vo) {
		this.id = vo.getId();
		this.fileNo = EncryptionUtils.decipher(vo.getFileNo());
		this.filetype = vo.getFiletype();
		this.name = EncryptionUtils.decipher(vo.getName());
		this.tel = vo.getTel();
		this.updateCol = vo.getUpdateCol();
		this.updatetype = vo.getUpdatetype();
	}
	
	public SmsPersonTel makeSaveVO() {
		SmsPersonTel retvo = new SmsPersonTel();
		retvo.id = this.getId();
		retvo.fileNo = EncryptionUtils.encry(this.getFileNo());
		retvo.filetype = this.getFiletype();
		retvo.name = EncryptionUtils.encry(this.getName());
		retvo.tel = this.getTel();
		retvo.updateCol = this.getUpdateCol();
		retvo.updatetype = this.getUpdatetype();
		return retvo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileNo() {
		return this.fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public Integer getFiletype() {
		return this.filetype;
	}

	public void setFiletype(Integer filetype) {
		this.filetype = filetype;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUpdateCol() {
		return this.updateCol;
	}

	public void setUpdateCol(String updateCol) {
		this.updateCol = updateCol;
	}

	public Integer getUpdatetype() {
		return this.updatetype;
	}

	public void setUpdatetype(Integer updatetype) {
		this.updatetype = updatetype;
	}

}