package cn.net.tongfang.web.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.web.service.bo.PrinterBO;

public class PrinterService extends HibernateDaoSupport {
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List printer(PrinterBO bean) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Session session = getSession();
			conn = session.connection();
			ps = conn.prepareStatement("Exec PrintInformationProc ?,?");
			if(bean.getPrintType().equals("0")){
				bean.setPrintWhere(EncryptionUtils.encry(bean.getPrintWhere()));
			}
			ps.setObject(1, bean.getPrintType());
			ps.setObject(2, bean.getPrintWhere());
			rs = ps.executeQuery();
			List list = new ArrayList();
			if (rs.next()) {
				ResultSetMetaData md = rs.getMetaData();
				int num = md.getColumnCount();
				Map mapOfColValues = new HashMap(num);
				for (int i = 1; i <= num; i++) {
					if(md.getColumnName(i).equals("FileNo") || md.getColumnName(i).equals("Name") || md.getColumnName(i).equals("IDNumber") ){
						mapOfColValues.put(md.getColumnName(i), EncryptionUtils.decipher(rs.getObject(i).toString()));
					}else {
						mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
					}
				}
				list.add(mapOfColValues);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(conn != null)
					conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}