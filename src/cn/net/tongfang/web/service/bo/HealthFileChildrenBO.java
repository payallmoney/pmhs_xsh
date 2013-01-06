package cn.net.tongfang.web.service.bo;

import java.util.List;

import cn.net.tongfang.framework.security.vo.ChildrenException;
import cn.net.tongfang.framework.security.vo.HealthFileChildren;
import cn.net.tongfang.framework.security.vo.MontherException;

public class HealthFileChildrenBO extends HealthFileChildren {
	List<ChildrenException> childrenException;
	List<MontherException> montherException;
	public List<ChildrenException> getChildrenException() {
		return childrenException;
	}
	public void setChildrenException(List<ChildrenException> childrenException) {
		this.childrenException = childrenException;
	}
	public List<MontherException> getMontherException() {
		return montherException;
	}
	public void setMontherException(List<MontherException> montherException) {
		this.montherException = montherException;
	}
	
	
}
