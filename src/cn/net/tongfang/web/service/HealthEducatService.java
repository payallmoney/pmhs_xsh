package cn.net.tongfang.web.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.web.service.bo.HealthEducatBO;

public class HealthEducatService extends HealthMainService<HealthEducatBO> {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(HealthEducatBO data) throws Exception {
		return save_(data);
	}

	@Override
	public Object get(HealthEducatBO data) throws Exception {
		return get_(data);
	}

}
