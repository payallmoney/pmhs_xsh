package cn.net.tongfang.web.json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.ServiceInsert;
import cn.net.tongfang.framework.security.vo.ServiceInsertSub;
import cn.net.tongfang.framework.security.vo.ServiceKey;
import cn.net.tongfang.framework.security.vo.ServiceMain;
import cn.net.tongfang.framework.security.vo.ServiceSub;
import cn.net.tongfang.framework.security.vo.ServiceUpdate;
import cn.net.tongfang.framework.security.vo.ServiceUpdateSub;
import cn.net.tongfang.web.util.FileNoGen;

@Controller
public class JsonServer extends HibernateDaoSupport {
	@Autowired
	private FileNoGen fileNoGen;

	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Transactional
	@RequestMapping("/jsonserver")
	public ModelAndView greeting(HttpServletResponse res,
			HttpServletRequest request,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "key", required = true) String key)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map ret = new HashMap();
		if (validkey(key, ret)) {
			// 继续处理流程
			String json = IOUtils.toString(request.getInputStream(), "UTF-8");
			System.out.println(json);
			Map params = mapper.readValue(json, Map.class);
			try {
				ret = processSql(type, params);
			} catch (Exception e) {
				ret.put("success", false);
				ret.put("msg", e.getMessage());
			}
		}
		System.out.println(mapper.writeValueAsString(ret));
		res.setContentType("application/json; charset=utf8");
		byte[] bytes = mapper.writeValueAsBytes(ret);
		res.getOutputStream().write(bytes);
		res.getOutputStream().flush();
		res.getOutputStream().close();
		return null;

	}

	private Map processSql(String name, Map params) throws Exception {
		Connection conn = getSession().connection();
		Statement st = conn.createStatement();
		List sqllist = getSession().createQuery(
				"from ServiceMain where name = '" + name + "' order by id")
				.list();
		if (sqllist.size() == 0) {
			throw new Exception("接口不存在!");
		}
		ServiceMain main = (ServiceMain) sqllist.get(0);
		if ("query".equals(main.getSvrtype())) {
			if (main.getIslist()) {
				return sqlList(main, params);
			} else {
				return sqlObject(main, params);
			}
		} else if ("update".equals(main.getSvrtype())) {
			return sqlUpdate(main, params);
		} else if ("insert".equals(main.getSvrtype())) {
			return sqlInsert(main, params);
		} else {
			throw new Exception("接口配置不正确!请与系统管理员联系!");
		}
	}

	@Transactional(readOnly = true)
	private boolean validkey(String keystr, Map ret) {
		boolean flag = false;
		List list = getSession().createQuery(
				"from ServiceKey where validkey = '" + keystr + "'").list();
		if (list.size() == 0) {
			ret.put("msg", "序列号无效!");
		} else {
			ServiceKey key = (ServiceKey) list.get(0);
			if (new Date().after(key.getDateend())
					|| new Date().before(key.getDatebegin())) {
				ret.put("msg", "序列号无效!");
			} else {
				flag = true;
				ret.put("success", true);
			}
		}
		ret.put("success", flag);
		return flag;
	}

	@Transactional(readOnly = true)
	public Map sqlList(ServiceMain main, Map params) throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		// No DataSource so we must handle Connections manually
		try {
			Connection conn = getSession().connection();
			List sublist = getSession().createQuery(
					"from ServiceSub where  mainid = " + main.getId()
							+ " order by ord").list();
			Map<String, ServiceSub> submap = new HashMap();
			for (int i = 0; i < sublist.size(); i++) {
				ServiceSub vo = (ServiceSub) sublist.get(i);
				submap.put(vo.getName(), vo);
			}

			String sql = main.getSql();
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				ServiceSub vo = submap.get(key);
				sql = sql + " and " + vo.getColstr();
			}
			sql = sql + " " + main.getGroupby() + " " + main.getOrderby();

			sql = sql.replaceAll("\"", "'");
			PreparedStatement stmt = conn.prepareStatement(sql);
			int paramidx = 1;
			SimpleDateFormat inputfomart2 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat inputfomarttime = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				Object value = params.get(key);
				ServiceSub vo = submap.get(key);
				if(vo != null){
					paramidx = setparam(vo.getType(), paramidx, stmt,
							value, inputfomart2, inputfomarttime);
				}
			}
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
			List retlist = new ArrayList();
			while (rs.next()) {
				Map row = new HashMap();
				for (int i = 1; i <= numberOfColumns; i++) {
					Class cls = Class.forName(rsMetaData.getColumnClassName(i));
					if (cls.isAssignableFrom(String.class)) {
						row.put(rsMetaData.getColumnLabel(i), rs.getString(i));
					} else if (cls.isAssignableFrom(Float.class)) {
						row.put(rsMetaData.getColumnLabel(i), rs.getFloat(i));
					} else if (cls.isAssignableFrom(Integer.class)) {
						row.put(rsMetaData.getColumnLabel(i), rs.getInt(i));
					} else if (cls.isAssignableFrom(Long.class)) {
						row.put(rsMetaData.getColumnLabel(i), rs.getLong(i));
					} else if (cls.isAssignableFrom(Date.class)) {
						Date obj = rs.getDate(i);
						row.put(rsMetaData.getColumnLabel(i),
								fomart.format(obj));
					} else if (cls.isAssignableFrom(java.sql.Timestamp.class)) {
						Date obj = rs.getDate(i);
						row.put(rsMetaData.getColumnLabel(i),
								fomart.format(obj));
					}
				}
				retlist.add(row);
			}
			Map ret = new HashMap();
			ret.put("rows", retlist);
			ret.put("currentpage", 1);
			ret.put("total", 1);
			ret.put("pages", 1);
			ret.put("success", true);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Transactional(readOnly = true)
	public Map sqlObject(ServiceMain main, Map params) throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		// No DataSource so we must handle Connections manually
		try {
			Connection conn = getSession().connection();
			List sublist = getSession().createQuery(
					"from ServiceSub where  mainid = " + main.getId()
							+ " order by mainid , ord").list();
			Map<String, ServiceSub> submap = new HashMap();
			for (int i = 0; i < sublist.size(); i++) {
				ServiceSub vo = (ServiceSub) sublist.get(i);
				submap.put(vo.getName(), vo);
			}
			String sql = main.getSql();
			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				ServiceSub vo = submap.get(key);
				if (vo != null)
					sql = sql + " and " + vo.getColstr();
			}
			sql = sql + " " + main.getGroupby() + " " + main.getOrderby();

			sql = sql.replaceAll("\"", "'");
			System.out.println(sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			int paramidx = 1;
			SimpleDateFormat inputfomart2 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat inputfomarttime = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");

			for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				Object value = params.get(key);
				ServiceSub vo = submap.get(key);
				if (vo != null) {
					paramidx = setparam(vo.getType(), paramidx, stmt,
							value, inputfomart2, inputfomarttime);
				}
			}
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
			Map row = new HashMap();
			if (rs.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					Class cls = Class.forName(rsMetaData.getColumnClassName(i));
					if (cls.isAssignableFrom(String.class)) {
						row.put(rsMetaData.getColumnLabel(i), rs.getString(i));
					} else if (cls.isAssignableFrom(Float.class)) {
						row.put(rsMetaData.getColumnLabel(i), rs.getFloat(i));
					} else if (cls.isAssignableFrom(Integer.class)) {
						row.put(rsMetaData.getColumnLabel(i), rs.getInt(i));
					} else if (cls.isAssignableFrom(Long.class)) {
						row.put(rsMetaData.getColumnLabel(i), rs.getLong(i));
					} else if (cls.isAssignableFrom(Date.class)) {
						Date obj = rs.getDate(i);
						row.put(rsMetaData.getColumnLabel(i),
								fomart.format(obj));
					} else if (cls.isAssignableFrom(java.sql.Timestamp.class)) {
						Date obj = rs.getDate(i);
						row.put(rsMetaData.getColumnLabel(i),
								fomart.format(obj));
					}
				}
			}
			row.put("success", true);
			return row;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Map sqlUpdate(ServiceMain main, Map params) throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		System.out.println("11111111111");
		// No DataSource so we must handle Connections manually
		try {
			Connection conn = getSession().connection();
			List updatevos = getSession().createQuery(
					"from ServiceUpdate where  mainid = " + main.getId())
					.list();
			System.out.println("2222222222222222222");
			for (int i = 0; i < updatevos.size(); i++) {
				ServiceUpdate updatevo = (ServiceUpdate) updatevos.get(i);
				Map wherecols = new HashMap();
				String[] wherecol = updatevo.getWherecol().split(",");
				for (int m = 0; m < wherecol.length; m++) {
					String colstr = wherecol[m].trim();
					if (colstr.length() > 0) {
						wherecols.put(colstr, colstr);
					}
				}
				List sublist = getSession().createQuery(
						"from ServiceUpdateSub where  updateid = "
								+ updatevo.getId()).list();
				Map<String, ServiceUpdateSub> submap = new HashMap();
				for (int j = 0; j < sublist.size(); j++) {
					ServiceUpdateSub vo = (ServiceUpdateSub) sublist.get(j);
					submap.put(vo.getName(), vo);
				}
				System.out.println("33333333333333333");
				String update = "";
				int updatecount = 0;
				String wherestr = " where 1=1 ";
				List updatelist = new ArrayList();
				List whereList = new ArrayList();
				for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					ServiceUpdateSub vo = submap.get(key);
					if (vo != null) {
						if (wherecols.containsKey(key)) {
							wherestr = wherestr + " and " + vo.getColstr();
							whereList.add(key);
						} else {
							update = update + "," + vo.getColstr();
							updatelist.add(key);
						}
					}
				}
				if (update.length() > 0) {
					update = "update " + updatevo.getTablename() + " set "
							+ update.substring(1);
				} else {
					continue;
				}
				System.out.println("4444444444444444");
				String sql = update + wherestr;
				sql = sql.replaceAll("\"", "'");
				System.out.println(sql);
				PreparedStatement stmt = conn.prepareStatement(sql);
				int paramidx = 1;
				SimpleDateFormat inputfomart2 = new SimpleDateFormat(
						"yyyy-MM-dd");
				SimpleDateFormat inputfomarttime = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				updatelist.addAll(whereList);
				System.out.println("55555555555555555");
				for (int n = 0; n < updatelist.size(); n++) {
					String key = (String) updatelist.get(n);
					String value = (String) params.get(key);
					ServiceUpdateSub vo = submap.get(key);
					if (vo != null) {
						paramidx = setparam(vo.getType(), paramidx, stmt,
								value, inputfomart2, inputfomarttime);
					}
				}
				int count = stmt.executeUpdate();
				if (count == 0) {
					throw new Exception("未更新任何数据!请检查查询条件是否正确!");
				}
				System.out.println("666666666666666");
			}
			Map ret = new HashMap();
			ret.put("success", true);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private int setparam(String type, int paramidx, PreparedStatement stmt,
			Object value, SimpleDateFormat inputfomart2,
			SimpleDateFormat inputfomarttime) throws Exception {
		if (type.equals("date")) {
			stmt.setDate(paramidx,
					new java.sql.Date(inputfomart2.parse((String) value)
							.getTime()));
		} else if (type.equals("time")) {
			stmt.setDate(
					paramidx,
					new java.sql.Date(inputfomarttime.parse(
							(String) value + " 00:00:00").getTime()));
		} else if (type.equals("string")) {
			stmt.setString(paramidx, (String) value);
		} else if (type.equals("int")) {
			stmt.setInt(paramidx, Integer.parseInt((String) value));
		} else {
			stmt.setFloat(paramidx, Float.parseFloat((String) value));
		}
		return paramidx + 1;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Map sqlInsert(ServiceMain main, Map params)
			throws Exception {
		TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager
				.currentOperator();
		System.out.println("=====insert to ===============");
		try {
			Connection conn = getSession().connection();
			List<ServiceInsert> insertvos = getSession().createQuery(
					"from ServiceInsert where  mainid = " + main.getId()
							+ " order by ord").list();
			Map<String, String> hasOthertablevalue = new HashMap();
			Map<String,Object> othertablevalue = new HashMap();
			Map<Integer, Map> submaps = new HashMap();
			Map defaultMap = new HashMap();
			for (int i = 0; i < insertvos.size(); i++) {
				ServiceInsert insert = insertvos.get(i);
				List subvos = getSession().createQuery(
						"from ServiceInsertSub where insertid  = "
								+ insert.getId()).list();
				Map<String, ServiceInsertSub> insertMap = new HashMap();
				for (int j = 0; j < subvos.size(); j++) {
					ServiceInsertSub sub = (ServiceInsertSub) subvos.get(j);
					insertMap.put(sub.getName(), sub);
					if ("othertable".equals(sub.getColgen())) {
						hasOthertablevalue.put(insert.getTablename() + "."
								+ sub.getName(), insert.getTablename() + "."
								+ sub.getName());
					}
					if (sub.getColgen() !=null && sub.getColgen().trim().length()>0) {
						defaultMap.put(sub.getName(), sub);
					}
				}
				submaps.put(insertvos.get(i).getId(), insertMap);
			}
			System.out.println("2222222222222222222");
			Map<String, Object> oldvalue = new HashMap();
			for (int i = 0; i < insertvos.size(); i++) {
				ServiceInsert insert = insertvos.get(i);
				Map<String, ServiceInsertSub> insertMap = submaps.get(insert
						.getId());
				System.out.println("33333333333333333");
				String insertsql = "";
				int updatecount = 0;
				String valuessql = "";
				List updatelist = new ArrayList();
				List whereList = new ArrayList();
				for (Iterator iter = defaultMap.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					ServiceInsertSub vo = insertMap.get(key);
					if (vo != null) {
						insertsql = insertsql + "," + vo.getColname();
						valuessql = valuessql + ",?";
					}
				}
				for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					ServiceInsertSub vo = insertMap.get(key);
					if (vo != null && !defaultMap.containsKey(key)) {
						insertsql = insertsql + "," + vo.getColname();
						valuessql = valuessql + ",?";
					}
				}
				if (insertsql.length() > 0) {
					insertsql = "insert into  " + insert.getTablename() + " ( "
							+ insertsql.substring(1) + ") values("
							+ valuessql.substring(1) + ")";
				} else {
					continue;
				}
				System.out.println("4444444444444444");
				String sql = insertsql;
				sql = sql.replaceAll("\"", "'");
				System.out.println(sql);
				PreparedStatement stmt = conn.prepareStatement(sql);
				int paramidx = 1;
				SimpleDateFormat inputfomart2 = new SimpleDateFormat(
						"yyyy-MM-dd");
				SimpleDateFormat inputfomarttime = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				updatelist.addAll(whereList);
				System.out.println("55555555555555555");
				for (Iterator iter = defaultMap.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					ServiceInsertSub vo = insertMap.get(key);
					if (vo != null) {
						String realvalue="";
						if ("fileno".equals(vo.getColgen())) {
							if(!params.containsKey("districtNumber")){
								throw new Exception("新增必须包含行政区划编码districtNumber!请与系统管理员联系!");
							}else{
								realvalue = fileNoGen.getNextFileNo((String)params.get("districtNumber"));
							}
							params.put(vo.getName(), realvalue);
						}else if ("othertable".equals(vo.getColgen())) {
							realvalue = (String)othertablevalue.get(insert.getTablename() + "."	+ vo.getName());
						}else if ("uuid".equals(vo.getColgen())) {
							realvalue = UUID.randomUUID().toString();
						}else if ("default".equals(vo.getColgen())) {
							realvalue = vo.getVal();
						}
						System.out.println( vo.getColname()+"==="+realvalue);
						paramidx = setparam(vo.getType(), paramidx, stmt,
								realvalue, inputfomart2, inputfomarttime);
					}
				}
				for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
					Object key = iter.next();
					Object value = params.get(key);
					ServiceInsertSub vo = insertMap.get(key);
					if (vo != null && !defaultMap.containsKey(key)) {
							paramidx = setparam(vo.getType(), paramidx, stmt,
									value, inputfomart2, inputfomarttime);
							System.out.println( vo.getColname()+"==="+value);
					}
				}
				System.out.println("666666666666666");
				stmt.execute();
			}
			params.put("success", "true");
			return params;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private List sqlListHead(String id) throws Exception {
		try {
			Connection conn = getSession().connection();
			List sqllist = getSession().createQuery(
					"from ServiceMain where id = " + id + " order by id")
					.list();
			ServiceMain main = (ServiceMain) sqllist.get(0);
			String sql = main.getSql();
			sql = sql + " and 1=2 ";
			sql = sql + " " + main.getGroupby() + " " + main.getOrderby();
			sql = sql.replaceAll("\"", "'");
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, null);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			List retlist = new ArrayList();
			for (int i = 1; i <= numberOfColumns; i++) {
				Map colmap = new HashMap();
				colmap.put("field", "col" + i);
				colmap.put("title", rsMetaData.getColumnLabel(i));
				retlist.add(colmap);
			}
			return retlist;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
}
