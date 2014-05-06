package cn.net.tongfang.framework.security.bo;

import java.util.List;

import cn.net.tongfang.framework.security.vo.District;
import cn.net.tongfang.framework.security.vo.SamTaxorgcode;

public class OperatorBo {
	
	private String id;
	private String username;
	private String password;
	//private String roleId;
	private Integer orgId;
	private String districtId;
	private Integer isLookAuthority;
	private Integer isChangeCertifiAuthority;
	private Integer isSupplyCertifiAuthority;
	private Integer isAdvancedCertifiAuthority;
	private Integer isOver2MonthCertifiAuthority;
	//private String roleName; //role name
	private List<ModuleBo> modules;
	private List<CategoryBo> categories;
	//add by Daved 2009-03-16
	
	private District district;
	public Integer getIsChangeCertifiAuthority() {
		return isChangeCertifiAuthority;
	}

	public Integer getIsOver2MonthCertifiAuthority() {
		return isOver2MonthCertifiAuthority;
	}

	public void setIsOver2MonthCertifiAuthority(Integer isOver2MonthCertifiAuthority) {
		this.isOver2MonthCertifiAuthority = isOver2MonthCertifiAuthority;
	}

	public Integer getIsAdvancedCertifiAuthority() {
		return isAdvancedCertifiAuthority;
	}

	public void setIsAdvancedCertifiAuthority(Integer isAdvancedCertifiAuthority) {
		this.isAdvancedCertifiAuthority = isAdvancedCertifiAuthority;
	}

	public void setIsChangeCertifiAuthority(Integer isChangeCertifiAuthority) {
		this.isChangeCertifiAuthority = isChangeCertifiAuthority;
	}

	public Integer getIsSupplyCertifiAuthority() {
		return isSupplyCertifiAuthority;
	}

	public void setIsSupplyCertifiAuthority(Integer isSupplyCertifiAuthority) {
		this.isSupplyCertifiAuthority = isSupplyCertifiAuthority;
	}

	private SamTaxorgcode org;
	
	
	private String validFlag;

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ModuleBo> getModules() {
		return modules;
	}

	public void setModules(List<ModuleBo> modules) {
		this.modules = modules;
	}

	public List<CategoryBo> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryBo> categories) {
		this.categories = categories;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public SamTaxorgcode getOrg() {
		return org;
	}

	public void setOrg(SamTaxorgcode org) {
		this.org = org;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public Integer getIsLookAuthority() {
		return isLookAuthority;
	}

	public void setIsLookAuthority(Integer isLookAuthority) {
		this.isLookAuthority = isLookAuthority;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer("username:[" + username
				+ "], ");
		
		if ( modules != null ) {
			s.append("modules:[");
			for (ModuleBo m : modules) {
				s.append( m.toString() + ";");
			}
			s.append("]");
		}
		if ( categories != null ) {
			s.append("categories:[");
			for (CategoryBo m : categories) {
				s.append( m.toString() + ";");
			}
			s.append("]");
		}
		s.append(",orgId:[" + orgId + "]");
		
		return s.toString();
	}
	
	
	
	
}
