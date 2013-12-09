package cn.net.tongfang.web.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.BabyBarrierRegBO;

public class BabyBarrierRegService extends HealthMainService<BabyBarrierRegBO>{

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(BabyBarrierRegBO data) throws Exception {
		data.setFileNo(EncryptionUtils.encry(data.getFileNo()));
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
		data.setExecDistrictNum(user.getDistrictId());
		return save_(data);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Object get(BabyBarrierRegBO data) throws Exception {
		data = (BabyBarrierRegBO)get_(data);
		data.setFileNo(EncryptionUtils.decipher(data.getFileNo()));
		return data;
	}

}
