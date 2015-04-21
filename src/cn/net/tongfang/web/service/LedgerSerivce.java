package cn.net.tongfang.web.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.vo.TchildrenLedger;
import cn.net.tongfang.framework.security.vo.TwomanLedger;
import cn.net.tongfang.web.service.bo.ChildrenLedgerBO;

public class LedgerSerivce extends HibernateDaoSupport {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TchildrenLedger> getChildrenLedger(ChildrenLedgerBO bean) {
		if(bean.getIsDetails().equals(1)){
			Query query = getSession().getNamedQuery("ProcChildrenLedger");
			query.setParameter(0, bean.getDistrictNumber());
			query.setParameter(1, bean.getYears());
			List list = query.list();
			return list;
		}else{
			String hql = "From TchildrenLedger Where disctrictNumber Like ? And years = ?";
			Query query = getSession().createQuery(hql);
			query.setParameter(0, bean.getDistrictNumber() + "%");
			query.setParameter(1, bean.getYears());
			List list = query.list();
			return list;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TwomanLedger> getWomanLedger(ChildrenLedgerBO bean) {
		if(bean.getIsDetails().equals(1)){
			Query query = getSession().getNamedQuery("ProcWomanLedger");
			query.setParameter(0, bean.getDistrictNumber());
			query.setParameter(1, bean.getYears());
			List list = query.list();
			return list;
		}else{
			String hql = "From TwomanLedger Where disctrictNumber Like ? And years = ?";
			Query query = getSession().createQuery(hql);
			query.setParameter(0, bean.getDistrictNumber() + "%");
			query.setParameter(1, bean.getYears());
			List list = query.list();
			return list;
		}
	}
}
