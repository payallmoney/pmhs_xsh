package cn.net.tongfang.web.service.map;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.AreaRestrict;
import cn.net.tongfang.framework.security.vo.Pointer;

public class MapService extends HibernateDaoSupport {

	public String saveAreaRestrictConfig(String xzqCode, String sw_x,
			String sw_y, String ne_x, String ne_y, String centerPoint_x,
			String centerPoint_y, String zoomLevel) {
		AreaRestrict ar = new AreaRestrict();
		ar.setId(xzqCode);
		ar.setSw(sw_x + "," + sw_y);
		ar.setNe(ne_x + "," + ne_y);
		ar.setCenterPoint(centerPoint_x + "," + centerPoint_y);
		ar.setZoomLevel(zoomLevel);
		getSession().save(ar);
		getSession().flush();
		return "00";
	}

	public MapConfigBean getAreaRestrictConfig() {
		MapConfigBean mcb = new MapConfigBean();
		TaxempDetail user = SecurityManager.currentOperator();
		String districtId = String.valueOf(user.getDistrictId());
//		System.out.println("districtId--------" + districtId + "-------");
		List<AreaRestrict> list = getSession().createQuery(
				"from AreaRestrict vo where id = '" + districtId + "'").list();
		if (list != null && list.size() > 0) {
			AreaRestrict ar = list.get(0);
			mcb.setCenterPoint(ar.getCenterPoint());
			mcb.setZoomLevel(ar.getZoomLevel());
			mcb.setSw(ar.getSw());
			mcb.setNe(ar.getNe());
			return mcb;
		} else {
			return null;
		}
	}

	public String savePointer(String coordinates, String organId,
			String organName) {
		Pointer p = new Pointer();
		p.setId(coordinates);
		p.setOrganId(organId);
		p.setOrganName(organName);
		getSession().saveOrUpdate(p);
		getSession().flush();
		return "00";
	}

	public List<Pointer> queryPointer() {
		List<Pointer> list = getSession().createQuery("from Pointer ").list();
		Pointer p = new Pointer();
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public Pointer queryPointerById(String id) {
		List<Pointer> list = getSession().createQuery(
				"from Pointer vo where vo.id = '" + id + "'").list();
		Pointer p = new Pointer();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public String deletePointerById(String id) {
		getSession().createQuery(
				"delete from Pointer vo where vo.id = '" + id + "'")
				.executeUpdate();
		return "00";
	}

	public List<Pointer> queryPointerByOrgId() {
		List<Pointer> retList = new ArrayList();
		TaxempDetail user = SecurityManager.currentOperator();
		String orgId = String.valueOf(user.getOrgId());
		String districtId = String.valueOf(user.getDistrictId());
		String sql = "select a.Id as id,a.OrganId as organId,a.OrganName as organName,b.ParentID as parentId,b.Level as level from Pointer a,Organization b,District c where 1=1 and a.OrganId = b.ID and c.ID = b.DistrictNumber and c.ParentID = '"
				+ districtId + "'";
		List list = getSession().createSQLQuery(sql).addScalar("id",
				Hibernate.STRING).addScalar("organId", Hibernate.STRING)
				.addScalar("organName", Hibernate.STRING).addScalar("parentId",
						Hibernate.INTEGER)
				.addScalar("level", Hibernate.INTEGER).list();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				String id = (String) objs[0];
				String organId = (String) objs[1];
				String organName = (String) objs[2];
				int parentId = (Integer) objs[3];
				int level = (Integer) objs[4];
				Pointer p = new Pointer();
				p.setId(id);
				p.setOrganId(organId);
				p.setOrganName(organName);
				retList.add(p);
			}

		}

		if (retList != null && retList.size() > 0) {
			return retList;
		} else {
			return null;
		}
	}

	public MapConfigBean getAreaRestrictConfigByDistrictId(String districtId) {
		MapConfigBean mcb = new MapConfigBean();
		TaxempDetail user = SecurityManager.currentOperator();
//		System.out.println("districtId--------" + districtId + "-------");
		List<AreaRestrict> list = getSession().createQuery(
				"from AreaRestrict vo where id = '" + districtId + "'").list();
		if (list != null && list.size() > 0) {
			AreaRestrict ar = list.get(0);
			mcb.setCenterPoint(ar.getCenterPoint());
			mcb.setZoomLevel(ar.getZoomLevel());
			mcb.setSw(ar.getSw());
			mcb.setNe(ar.getNe());
			return mcb;
		} else {
			return null;
		}
	}

	public String getOrgIdAndLevel() {
		TaxempDetail user = SecurityManager.currentOperator();
		return user.getOrg().getId() + "-" + user.getOrg().getLevel();
	}
	
	public String getOrganListByDistrictId(String districtId) {
		String sql = "select a.Id as id,a.OrganId as organId,a.OrganName as organName,b.ParentID as parentId,b.Level as level from Pointer a,Organization b,District c where 1=1 and a.OrganId = b.ID and c.ID = b.DistrictNumber and c.ParentID = '"
				+ districtId + "'";
		List list = getSession().createSQLQuery(sql).addScalar("id",
				Hibernate.STRING).addScalar("organId", Hibernate.STRING)
				.addScalar("organName", Hibernate.STRING).addScalar("parentId",
						Hibernate.INTEGER)
				.addScalar("level", Hibernate.INTEGER).list();
		StringBuffer sb = new StringBuffer();
		sb.append("{'total':'" + list.size() +"','rows':[");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				String id = (String) objs[0];
				String organId = (String) objs[1];
				String organName = (String) objs[2];
				int parentId = (Integer) objs[3];
				int level = (Integer) objs[4];
				sb.append("{'id':'" + id + "','organId':'" + organId + "','organName':'" + organName + "'}");
				sb.append(",");
			}
			sb.delete(sb.lastIndexOf(","), sb.length());
			sb.append("]}");

		}else{
			sb.append("]}");
		}
		String ret = sb.toString().replaceAll("\"", "").replaceAll("'", "\"");
		System.out.println("-----------" + ret);
		return ret;
	}
}