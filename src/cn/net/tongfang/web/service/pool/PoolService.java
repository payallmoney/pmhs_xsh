package cn.net.tongfang.web.service.pool;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class PoolService extends HibernateDaoSupport{
	private static final Logger log = Logger.getLogger(PoolService.class);
	@Autowired
	private DataSource dataSource;
	// 查询分数
	public Map queryPoolStatus() {
		BasicDataSource ds = (BasicDataSource)dataSource;
		Map ret = new HashMap();
		ret.put("maxactive", ds.getMaxActive());
		ret.put("maxidle", ds.getMaxIdle());
		ret.put("maxwait", ds.getMaxWait());
		ret.put("numactive", ds.getNumActive());
		ret.put("numidle", ds.getNumIdle());
		ret.put("initialsize", ds.getInitialSize());
		return ret;
	}

}
