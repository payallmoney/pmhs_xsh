package cn.net.tongfang.web.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.web.service.bo.ChildrenMediExam3BO;

public class ChildrenMediExam3Service extends HealthMainService<ChildrenMediExam3BO> {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(ChildrenMediExam3BO data) throws Exception {
		return save_(data);
	}

	@Override
	public Object get(ChildrenMediExam3BO data) throws Exception {
		return get_(data);
	}

}
