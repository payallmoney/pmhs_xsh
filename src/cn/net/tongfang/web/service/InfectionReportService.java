package cn.net.tongfang.web.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.web.service.bo.InfectionReportBO;

public class InfectionReportService extends HealthMainService<InfectionReportBO> {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(InfectionReportBO data) throws Exception {
		return save_(data);
	}

	@Override
	public Object get(InfectionReportBO data) throws Exception {
		return get_(data);
	}

}
