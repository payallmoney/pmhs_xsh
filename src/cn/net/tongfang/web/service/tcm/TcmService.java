package cn.net.tongfang.web.service.tcm;

import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.StringUtils;

import cn.net.tongfang.framework.security.bo.NewQryCondition;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.util.service.ModuleMgr;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;

public class TcmService extends HibernateDaoSupport {
	private static final Logger log = Logger.getLogger(TcmService.class);

	// 列表
	public PagingResult<Map> findChildren(NewQryCondition cod, PagingParam pp)
			throws Exception {
		if (pp == null)
			pp = new PagingParam();
		try {
			CallableStatement proc = getSession().connection().prepareCall(
					"{ call Proc_TCM(?,?,?,?,?,?,?) }");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			proc.setString(1, cod.getDistrict());
			String begindate = null;
			if(StringUtils.hasText(cod.getValueByName("begindate"))){
				begindate = df.format(Long.parseLong(cod.getValueByName("begindate")));
			}
			proc.setString(2,begindate);
			String enddate = null;
			if(StringUtils.hasText(cod.getValueByName("begindate"))){
				enddate = df.format(Long.parseLong(cod.getValueByName("enddate")));
			}
			proc.setString(3,enddate);
			System.out.println("============"+cod.getParamsStr());
			proc.setString(4, cod.getParamsStr());
			proc.setInt(5, pp.getStart());
			proc.setInt(6, pp.getLimit());
			proc.registerOutParameter(7, Types.INTEGER);
			ResultSet rs = proc.executeQuery();

			List<Map> ret = new ArrayList();
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				Map row = new HashMap();
				for (int i = 1; i <= columnCount; i++) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				ret.add(row);
			}

			int totalSize = proc.getInt(7);
			return new PagingResult<Map>(totalSize, ret);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	// 查询明细
		public List finddetail(String type ,String id)
				throws Exception {
			List<Map> ret = new ArrayList();
			try {
				CallableStatement proc = getSession().connection().prepareCall(
						"{ call Proc_TCM_detail(?,?) }");
				proc.setString(1, type);
				proc.setString(2,id);
				ResultSet rs = proc.executeQuery();
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				while (rs.next()) {
					Map row = new HashMap();
					for (int i = 1; i <= columnCount; i++) {
						row.put(md.getColumnName(i), rs.getObject(i));
					}
					ret.add(row);
				}
				return ret;
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
		}
		

}
