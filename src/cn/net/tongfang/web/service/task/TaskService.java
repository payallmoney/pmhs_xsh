package cn.net.tongfang.web.service.task;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.bo.Condition;
import cn.net.tongfang.framework.security.bo.QryCondition;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.*;
import cn.net.tongfang.framework.util.BusiUtils;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.SmsUtil;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;
import cn.net.tongfang.web.service.commonexam.CommonExamUtil;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskService extends HibernateDaoSupport {
    private static final Logger log = Logger.getLogger(TaskService.class);
    private static final int tel_len = 11;
    private static final String triggername = "tel";
    public static final String Send_Status_NotCreated = "-1";
    public static final String Send_Status_CreatedNotSend = "0";
    public static final String Send_Status_Sended = "1";
    public static final String Send_Status_Sending = "2";

    public static Map<String, Integer> typemap = new HashMap();

    static {
        typemap.put("y", GregorianCalendar.YEAR);
        typemap.put("m", GregorianCalendar.MONTH);
        typemap.put("d", GregorianCalendar.DAY_OF_MONTH);
    }

    private static Map<String, Map<String, String>> queryParam = new HashMap();

    static {
        Gson gs = new Gson();
        queryParam.put("name", gs.fromJson("{key:'hf.name',text:'姓名',opt:'like', sql:'\\'%?%\\'',type:'string',inlist:'true' }", Map.class));
        queryParam.put("fileno", gs.fromJson("{key:'info.fileNo',text:'档案编码',opt:'=' , sql:'\\'?\\'',type:'string',inlist:'true' }", Map.class));
        queryParam.put("idnumber", gs.fromJson("{key:'info.idnumber',text:'身份证号',opt:'=' , sql:'\\'?\\'',type:'string',inlist:'true' }", Map.class));
        queryParam.put("begindate", gs.fromJson("{key:'vo.smsdate',text:'日期起',opt:'>=' , sql:'convert(date,\\'?\\')',type:'date',inlist:'false' }", Map.class));
        queryParam.put("enddate", gs.fromJson("{key:'vo.smsdate',text:'日期止',opt:'<' , sql:'dateadd(day,1,convert(date,\\'?\\'))',type:'date',inlist:'false' }", Map.class));
        queryParam.put("district", gs.fromJson("{key:'info.fileNo',text:'行政区划',opt:'like' , sql:'\\'?%\\'',type:'district',inlist:'false' }", Map.class));
    }

    private TaskUtil taskUtil;
    private CommonExamUtil examUtil;


    // @Cacheable(cacheName = "messageCache")
    public List<CodTelUpdateCol> getTelRule() {
        String hql = "from  CodTelUpdateCol order by ord desc";
        try {
            List<CodTelUpdateCol> list = getHibernateTemplate().find(hql);
            log.debug("List size is " + list.size());
            return list;
        } catch (Throwable t) {
            t.printStackTrace();
            return new ArrayList<CodTelUpdateCol>();
        }
    }

    // 查询提取规则
    public PagingResult<CodTelUpdateCol> findRules(CodTelUpdateCol qryCond,
                                                   PagingParam pp) throws Exception {
        if (pp == null)
            pp = new PagingParam();

        List params = new ArrayList();
        Map likemap = new HashMap();
        likemap.put("name", null);
        StringBuilder where = new StringBuilder(" where 1=1 ");
        makeParamsList(qryCond, likemap, where, params);
        StringBuilder hql = new StringBuilder("from  CodTelUpdateCol").append(
                where).append(" order by ord");
        log.debug("hql: " + hql.toString());
        Query query = getSession().createQuery(hql.toString());
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i, params.get(i));
        }
        int totalSize = query.list().size();

        query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

        List<CodTelUpdateCol> list = (List<CodTelUpdateCol>) query.list();
        return new PagingResult<CodTelUpdateCol>(totalSize, list);
    }

    public static String camelcasify(String in) {
        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = false;
        int count = 0;
        for (char c : in.toCharArray()) {
            if (count == 0) {
                sb.append(Character.toUpperCase(c));
                // sb.append(c);
                capitalizeNext = false;
            } else {
                if (c == '_') {
                    capitalizeNext = true;
                } else {
                    if (capitalizeNext) {
                        sb.append(Character.toUpperCase(c));
                        capitalizeNext = false;
                    } else {
                        sb.append(c);
                    }
                }
            }
            count++;
        }
        return sb.toString();
    }

    // 增加保存提取规则
    public String editRule(CodTelUpdateCol bo) throws Exception {
        CodTelUpdateCol module = bo;
        if (module.getId() != null && module.getId().isEmpty())
            module.setId(null);
        try {
            DatabaseMetaData metaData = getHibernateTemplate()
                    .getSessionFactory().getCurrentSession().connection()
                    .getMetaData();
            ResultSet rs = metaData.getTables(null, "dbo",
                    module.getTablename(), new String[]{"TABLE"});
            if (!rs.next()) {
                throw new Exception("表名为" + module.getTablename() + "的表不存在!");
            } else {
                rs = metaData.getColumns(null, "dbo", module.getTablename(),
                        module.getCol());
                if (!rs.next()) {
                    throw new Exception("表" + module.getTablename() + "中字段"
                            + module.getCol() + "不存在!");
                }
            }
            // 取保存前的列表
            Map<String, List> old_tableMap = new HashMap();
            List<CodTelUpdateCol> old_rulelist = getTelRule();
            for (CodTelUpdateCol rule : old_rulelist) {
                getHibernateTemplate().evict(rule);
                if (old_tableMap.containsKey(rule.getTablename())) {
                    List<String> s = (List) old_tableMap.get(rule
                            .getTablename());
                    s.add(rule.getCol());
                } else {
                    List<String> s = new ArrayList();
                    s.add(rule.getCol());
                    old_tableMap.put(rule.getTablename(), s);
                }
            }
            getHibernateTemplate().saveOrUpdate(module);
            // 取保存后的列表
            Map<String, List> tableMap = new HashMap();
            List<CodTelUpdateCol> rulelist = getTelRule();

            for (CodTelUpdateCol rule : rulelist) {
                getHibernateTemplate().evict(rule);
                if (tableMap.containsKey(rule.getTablename())) {
                    List<String> s = (List) tableMap.get(rule.getTablename());
                    s.add(rule.getCol());
                } else {
                    List<String> s = new ArrayList();
                    s.add(rule.getCol());
                    tableMap.put(rule.getTablename(), s);
                }

            }
            getSession().createSQLQuery(" set nocount on ").executeUpdate();
            // 删除更新后的表中的触发器
            for (String key : old_tableMap.keySet()) {
                if (!tableMap.containsKey(key)) {
                    getSession().createSQLQuery(
                            " drop TRIGGER " + triggername + "_" + key)
                            .executeUpdate();
                }
            }
            for (String key : tableMap.keySet()) {
                List<String> cols = tableMap.get(key);
                try {
                    getSession().createSQLQuery(
                            " drop TRIGGER " + triggername + "_" + key)
                            .executeUpdate();
                } catch (Exception ex) {
                    // 不处理异常
                }
                String sql = getTirggerSQL(key, cols);
                getSession().createSQLQuery(sql).executeUpdate();

            }
            getSession().createSQLQuery(" set nocount off ").executeUpdate();
            return module.getId();
        } catch (SQLException ex) {
            return null;
        }
    }

    // 删除提取规则
    public void removeRule(String modules) {
        if (!StringUtils.hasText(modules))
            return;
        // 取保存前的列表
        Map<String, List> old_tableMap = new HashMap();
        List<CodTelUpdateCol> old_rulelist = getTelRule();
        for (CodTelUpdateCol rule : old_rulelist) {
            if (old_tableMap.containsKey(rule.getTablename())) {
                List<String> s = (List) old_tableMap.get(rule.getTablename());
                s.add(rule.getCol());
            } else {
                List<String> s = new ArrayList();
                s.add(rule.getCol());
                old_tableMap.put(rule.getTablename(), s);
            }
        }
        for (String id : modules.split(",")) {
            getHibernateTemplate().delete(
                    getHibernateTemplate().get(CodTelUpdateCol.class, id));
        }
        // 取保存后的列表

        Map<String, List> tableMap = new HashMap();
        List<CodTelUpdateCol> rulelist = getTelRule();
        for (CodTelUpdateCol rule : rulelist) {
            if (tableMap.containsKey(rule.getTablename())) {
                List<String> s = (List) tableMap.get(rule.getTablename());
                s.add(rule.getCol());
            } else {
                List<String> s = new ArrayList();
                s.add(rule.getCol());
                tableMap.put(rule.getTablename(), s);
            }

        }
        getSession().createSQLQuery(" set nocount on ").executeUpdate();
        // 删除更新后不存在的表中的触发器
        for (String key : old_tableMap.keySet()) {
            if (!tableMap.containsKey(key)) {
                getSession().createSQLQuery(
                        " drop TRIGGER " + triggername + "_" + key)
                        .executeUpdate();
            }
        }
        // 重新设置触发器
        for (String key : tableMap.keySet()) {
            List<String> cols = tableMap.get(key);
            try {
                getSession().createSQLQuery(
                        " drop TRIGGER " + triggername + "_" + key)
                        .executeUpdate();
            } catch (Exception ex) {
                // 不处理异常
            }
            String sql = getTirggerSQL(key, cols);
            getSession().createSQLQuery(sql).executeUpdate();
        }
        getSession().createSQLQuery(" set nocount off ").executeUpdate();
    }

    private String getTirggerSQL(String key, List<String> cols) {
        String sql = "CREATE TRIGGER "
                + triggername
                + "_"
                + key
                + " on "
                + key
                + "  FOR  INSERT ,  UPDATE AS \n begin \nif exists (select 1 from deleted) \n begin ";
        for (String col : cols) {
            sql += "\n update Sms_PersonTel set tel = b."
                    + col
                    + " "
                    + "from Sms_PersonTel a , inserted b , deleted c "
                    + "where a.fileNo = b.fileNo and a.fileNo =c.fileNo "
                    + " and b."
                    + col
                    + " <> c."
                    + col
                    + " \n"
                    + " insert into Sms_PersonTel "
                    + " select b.fileNo,b.fileNo,'0',hf.name, b."
                    + col
                    + ",'HealthFile.TEL',-1 "
                    + " from HealthFile hf ,inserted b"
                    + " where  hf.fileNo=b.fileNo "
                    + " and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	";
        }
        sql += " \nend \n else \n begin \n";
        for (String col : cols) {
            sql += " \n update Sms_PersonTel set tel = b."
                    + col
                    + " from Sms_PersonTel a , inserted b  "
                    + "where a.fileNo = b.fileNo  "
                    + " \n"
                    + " insert into Sms_PersonTel "
                    + " select b.fileNo,b.fileNo,'0',hf.name, b."
                    + col
                    + ",'HealthFile.TEL',-1 "
                    + " from HealthFile hf ,inserted b"
                    + " where hf.fileNo=b.fileNo "
                    + " and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	";
        }
        sql += " \nend \n end";
        return sql;
    }


    // 查询发送规则
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PagingResult<TaskRule> findSendRules(TaskRule qryCond, PagingParam pp)
            throws Exception {
        if (pp == null)
            pp = new PagingParam();
        List params = new ArrayList();
        Map likemap = new HashMap();
        likemap.put("name", null);
        StringBuilder where = new StringBuilder(" where 1=1 ");
        makeParamsList(qryCond, likemap, where, params);
        StringBuilder hql = new StringBuilder("from CodTelSendRule").append(
                where).append(" order by optdate");
        log.debug("hql: " + hql.toString());

        Query query = getSession().createQuery(hql.toString());
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i, params.get(i));
        }
        int totalSize = query.list().size();

        query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

        List<TaskRule> list = (List<TaskRule>) query.list();
        return new PagingResult<TaskRule>(totalSize, list);
    }

    // 增加保存发送规则
    @Transactional
    public String editSendRule(TaskRule bo) throws Exception {
        TaskRule module = bo;
        if (module.getId() != null && module.getId().isEmpty())
            module.setId(null);
        module.setOptdate(new Timestamp(new Date().getTime()));
        DatabaseMetaData metaData = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().connection().getMetaData();
        System.out
                .println("==========camelcasify(module.getTablename())========="
                        + camelcasify(module.getTablename()));
        String tablename = camelcasify(module.getTablename());
        ResultSet rs = metaData.getTables(null, "dbo", module.getTablename(),
                new String[]{"TABLE"});

        if (!rs.next()) {
            throw new Exception("表名为" + module.getTablename() + "的表不存在!");
        } else {
            if ("0".equals(module.getType())) {
                rs = metaData.getColumns(null, "dbo", module.getTablename(),
                        "fileNo");
                if (!rs.next()) {
                    throw new Exception("表" + module.getTablename()
                            + "中没有fileNo字段,该表不能生成发送规则!");
                } else {
                    rs = metaData.getColumns(null, "dbo",
                            module.getTablename(), module.getCol());
                    if (!rs.next()) {
                        throw new Exception("表" + module.getTablename() + "中字段"
                                + module.getCol() + "不存在!");
                    } else {
                        if (!"date".equals(rs.getString(6))
                                && !"datetime".equals(rs.getString(6))) {
                            throw new Exception("表" + module.getTablename()
                                    + "中字段" + module.getCol() + "不是时间类型!");
                        }
                    }
                    rs = metaData.getColumns(null, "dbo",
                            module.getTablename(), module.getTableidname());
                    if (!rs.next()) {
                        throw new Exception("表" + module.getTablename() + "中字段"
                                + module.getTableidname() + "不存在!");
                    }
                    // 检查模板内容
                    Class objClass = Class
                            .forName("cn.net.tongfang.framework.security.vo."
                                    + tablename);
                    Map<String, Boolean> propertys = getPropertysFromTemplate(module
                            .getMsg());
                    for (String property : propertys.keySet()) {
                        Field field = null;
                        try {
                            field = objClass.getDeclaredField(property);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            throw new Exception(
                                    "表"
                                            + module.getTablename()
                                            + "中属性"
                                            + property
                                            + "不存在!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
                        }
                        if (field != null) {
                            boolean isDate = propertys.get(property);
                            if (isDate
                                    && !java.util.Date.class
                                    .isAssignableFrom(field.getType())) {
                                throw new Exception(
                                        "表"
                                                + module.getTablename()
                                                + "中属性"
                                                + property
                                                + "不是时间类型,不能使用$(date+10d)的模板格式!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
                            }
                        }
                    }
                }
            } else {
                rs = metaData.getColumns(null, "dbo", module.getTablename(),
                        module.getCol());
                if (!rs.next()) {
                    throw new Exception("表" + module.getTablename() + "中字段"
                            + module.getCol() + "不存在!");
                } else {
                    if (!"date".equals(rs.getString(6))
                            && !"datetime".equals(rs.getString(6))) {
                        throw new Exception("表" + module.getTablename() + "中字段"
                                + module.getCol() + "不是时间类型!");
                    }
                }
                rs = metaData.getColumns(null, "dbo", module.getTablename(),
                        module.getTableidname());
                if (!rs.next()) {
                    throw new Exception("表" + module.getTablename() + "中字段"
                            + module.getTableidname() + "不存在!");
                }
                // 检查模板内容
                Class objClass = Class
                        .forName("cn.net.tongfang.framework.security.vo."
                                + tablename);
                Map<String, Boolean> propertys = getPropertysFromTemplate(module
                        .getMsg());
                for (String property : propertys.keySet()) {
                    Field field = null;
                    try {
                        field = objClass.getDeclaredField(property);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new Exception(
                                "表"
                                        + module.getTablename()
                                        + "中属性"
                                        + property
                                        + "不存在!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
                    }
                    if (field != null) {
                        boolean isDate = propertys.get(property);
                        if (isDate
                                && !java.util.Date.class.isAssignableFrom(field
                                .getType())) {
                            throw new Exception(
                                    "表"
                                            + module.getTablename()
                                            + "中属性"
                                            + property
                                            + "不是时间类型,不能使用$(date+10d)的模板格式!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
                        }
                    }
                }
            }

        }
        getHibernateTemplate().saveOrUpdate(module);
        return module.getId();
    }

    // 删除发送规则
    @Transactional
    public void removeSendRule(String ids) {
        if (!StringUtils.hasText(ids))
            return;

        for (String id : ids.split(",")) {
            getHibernateTemplate().delete(
                    getHibernateTemplate().get(TaskRule.class, id));
        }
    }

    // 查询发送人群规则
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PagingResult<SmsSendTarget> findSendTargets(SmsSendTarget qryCond,
                                                       PagingParam pp) throws Exception {
        if (pp == null)
            pp = new PagingParam();

        List params = new ArrayList();

        Map likemap = new HashMap();
        likemap.put("name", null);
        StringBuilder where = new StringBuilder(" where 1=1 ");
        makeParamsList(qryCond, likemap, where, params);
        StringBuilder hql = new StringBuilder("from SmsSendTarget").append(
                where).append(" order by name");
        log.debug("hql: " + hql.toString());

        Query query = getSession().createQuery(hql.toString());
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i, params.get(i));
        }
        int totalSize = query.list().size();

        query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

        List<SmsSendTarget> list = query.list();
        return new PagingResult<SmsSendTarget>(totalSize, list);
    }

    // 返回下拉列表数据
    public List getSendTargetOption() throws Exception {

        StringBuilder where = new StringBuilder();
        List params = new ArrayList();

        if (params.size() != 0) {
            where.replace(0, 4, " where ");
        }
        StringBuilder hql = new StringBuilder(
                "select new list(id , name,msg) from SmsSendTarget");

        Query query = getSession().createQuery(hql.toString());
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i, params.get(i));
        }
        List list = query.list();
        List addmap = new ArrayList();
        addmap.add("99");
        addmap.add("------增加人群------");
        list.add(addmap);
        return list;
    }

    // 增加保存发送人群规则
    @Transactional
    public String editSendTarget(SmsSendTarget bo) throws Exception {
        SmsSendTarget module = bo;
        String tablename = this.camelcasify(module.getTablename());
        if (module.getId() != null && module.getId().isEmpty())
            module.setId(null);
        DatabaseMetaData metaData = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().connection().getMetaData();
        ResultSet rs = metaData.getTables(null, "dbo", module.getTablename(),
                new String[]{"TABLE"});
        if (!rs.next()) {
            throw new Exception("表名为" + module.getTablename() + "的表不存在!");
        } else {
            rs = metaData.getColumns(null, "dbo", module.getTablename(),
                    "fileNo");
            if (!rs.next()) {
                throw new Exception("表" + module.getTablename()
                        + "中没有fileNo字段,该表不能生成发送规则!");
            } else {
                rs = metaData.getColumns(null, "dbo", module.getTablename(),
                        module.getTableidname());
                if (!rs.next()) {
                    throw new Exception("表" + module.getTablename() + "中字段"
                            + module.getTableidname() + "不存在!");
                }
                // 检查模板内容
                Class objClass = Class
                        .forName("cn.net.tongfang.framework.security.vo."
                                + tablename);
                Map<String, Boolean> propertys = getPropertysFromTemplate(module
                        .getMsg());
                for (String property : propertys.keySet()) {
                    Field field = null;
                    try {
                        field = objClass.getDeclaredField(property);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new Exception(
                                "表"
                                        + module.getTablename()
                                        + "中属性"
                                        + property
                                        + "不存在!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
                    }
                    if (field != null) {
                        boolean isDate = propertys.get(property);
                        if (isDate
                                && !java.util.Date.class.isAssignableFrom(field
                                .getType())) {
                            throw new Exception(
                                    "表"
                                            + module.getTablename()
                                            + "中属性"
                                            + property
                                            + "不是时间类型,不能使用$(date+10d)的模板格式!<br>(注:属性名和表中的列名有所不同,第一位应小写,下划线\"_\"应去掉,下划线\"_\"后面的字母应大写)");
                        }
                    }
                }
            }
        }
        getHibernateTemplate().saveOrUpdate(module);
        return module.getId();
    }

    // 删除发送人群规则
    @Transactional
    public void removeSendTarget(String ids) {
        if (!StringUtils.hasText(ids))
            return;
        for (String id : ids.split(",")) {
            getHibernateTemplate().delete(
                    getHibernateTemplate().get(SmsSendTarget.class, id));
        }
    }

    // 查询其他人群规则
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PagingResult<Map> findOtherSendTargets(SmsSendTargetOther qryCond,
                                                  PagingParam pp) throws Exception {
        if (pp == null)
            pp = new PagingParam();

        StringBuilder where = new StringBuilder();
        where.append(" where ssto.districtNumber = dist.id and ssto.type = basic.id ");
        List params = new ArrayList();
        Map likemap = new HashMap();
        likemap.put("name", null);
        Map likemap1 = new HashMap();
        likemap1.put("districtNumber", null);
        Map bean = PropertyUtils.describe(qryCond);
        for (Object key : bean.keySet()) {
            String keystr = (String) key;
            if ("class".equals(keystr)) {
                continue;
            }
            Object value = bean.get(key);
            if (value != null) {
                if (value instanceof String) {
                    if (!StringUtils.hasText((String) value)) {
                        value = null;
                    }
                }
                if (value != null) {
                    if (!likemap.containsKey(keystr)) {
                        if (likemap1.containsKey(keystr)) {
                            where.append(" and ssto." + keystr + " like ? ");
                            params.add(value + "%");
                        } else {
                            where.append(" and ssto." + keystr + " = ? ");
                            params.add(value);
                        }
                    } else {
                        where.append(" and ssto." + keystr + " like ?");
                        params.add("%" + value + "%");
                    }
                }
            }
        }
        StringBuilder hql = new StringBuilder(
                "select new map(ssto.id as id , ssto.name as name ,ssto.tel as tel,ssto.districtNumber as districtNumber ,dist.name as districtNumber_name ,ssto.type as type,basic.name as type_name,ssto.isTest as isTest) "
                        + "from SmsSendTargetOther ssto , District dist , BasicInformation basic ")
                .append(where).append(" order by dist.id");
        StringBuilder counthql = new StringBuilder(
                "select count(*) "
                        + "from SmsSendTargetOther ssto , District dist , BasicInformation basic ")
                .append(where).append(" ");
        log.debug("hql: " + hql.toString());
        Query query = getSession().createQuery(hql.toString());
        Query querycount = getSession().createQuery(counthql.toString());
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i, params.get(i));
            querycount.setParameter(i, params.get(i));
        }
        List ret = querycount.list();
        int totalSize = 0;
        if (ret != null && ret.size() > 0) {
            totalSize = ((Long) ret.get(0)).intValue();
        }
        query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());
        List<Map> list = query.list();
        return new PagingResult<Map>(totalSize, list);
    }

    // 返回下拉列表数据
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List getTaskRuleOption() throws Exception {
        StringBuilder hql = new StringBuilder(
                "select new list(id,name,parent,ord) from TaskRule order by parent , ord ");
        Query query = getSession().createQuery(hql.toString());
        List<List<String>> list = query.list();
        List<List<String>> newlist = new ArrayList();
        List addmap = new ArrayList();
        addmap.add("");
        addmap.add("全部");
        newlist.add(addmap);
        newlist.addAll(list);
        return newlist;
    }

    public List getTaskCatOption() throws Exception {
        StringBuilder hql = new StringBuilder(
                "select new list(id,name,ord) from TaskCat order by ord ");
        Query query = getSession().createQuery(hql.toString());
        List<List<String>> list = query.list();
        List<List<String>> newlist = new ArrayList();
        List addmap = new ArrayList();
        addmap.add("");
        addmap.add("全部");
        addmap.add(-1);
        newlist.add(addmap);
        newlist.addAll(list);
        return newlist;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List getDistrictOption() throws Exception {
        StringBuilder hql = new StringBuilder(
                "select new list(id,name) from District where id like '5301%'");
        Query query = getSession().createQuery(hql.toString());
        List<List<String>> list = query.list();
        for (List<String> item : list) {
            if (item.get(0).length() == 9) {
                item.set(1, "|--" + item.get(1));
            } else if (item.get(0).length() == 12) {
                item.set(1, "|--|--" + item.get(1));
            }
        }
        return list;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List getTypeOption() throws Exception {
        StringBuilder hql = new StringBuilder(
                "select new list(id , name) from BasicInformation where type = '4001' and isMain = 0 and isBeforehand = 1 ");
        Query query = getSession().createQuery(hql.toString());
        List list = query.list();
        return list;
    }

    // 增加保存其他人群规则
    @Transactional
    public String editOtherSendTarget(SmsSendTargetOther bo) throws Exception {
        SmsSendTargetOther module = bo;
        if (module.getIsTest() == null) {
            module.setIsTest(0);
        }
        if (module.getId() != null && module.getId().isEmpty())
            module.setId(null);
        getHibernateTemplate().saveOrUpdate(module);
        return module.getId();
    }

    // 删除其他人群规则
    @Transactional
    public void removeOtherSendTarget(String ids) {
        if (!StringUtils.hasText(ids))
            return;
        for (String id : ids.split(",")) {
            getHibernateTemplate().delete(
                    getHibernateTemplate().get(SmsSendTargetOther.class, id));
        }
    }

    // 查询联系电话
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PagingResult<SmsPersonTel> findTels(QryCondition qryCond,
                                               PagingParam pp) throws Exception {
        if (pp == null)
            pp = new PagingParam();
        StringBuilder where = new StringBuilder(" where 1=1 ");
        List params = new ArrayList();
        Map likemap = new HashMap();
        likemap.put("vo.name", null);
        likemap.put("vo.tel", null);
        Map encMap = new HashMap();
        encMap.put("vo.name", null);
        encMap.put("vo.fileNo", null);
        String district = qryCond.getDistrict();

        if (StringUtils.hasText(district)) {
            where.append(" and vo.fileNo = hf.fileNo and hf.districtNumber like ? ");
            params.add(district + "%");
        }
        for (Condition obj : qryCond.getConditions()) {
            String key = obj.getFilterKey();
            String value = obj.getFilterVal();
            if (StringUtils.hasText(value)) {
                if (encMap.containsKey(key)) {
                    value = EncryptionUtils.encry(value);
                }
                if (!likemap.containsKey(key)) {
                    where.append(" and " + key + " = ? ");
                    params.add(value);
                } else {
                    where.append(" and " + key + " like ?");
                    params.add("%" + value + "%");
                }
            }
        }
        StringBuilder hql = new StringBuilder(
                "select new SmsPersonTel(vo)from SmsPersonTel vo , HealthFile hf  ")
                .append(where);
        StringBuilder counthql = new StringBuilder(
                "select count(*) from SmsPersonTel vo , HealthFile hf  ")
                .append(where);
        log.debug("hql: " + hql.toString());

        Query countquery = getSession().createQuery(counthql.toString());
        for (int i = 0; i < params.size(); i++) {
            countquery.setParameter(i, params.get(i));
        }
        List ret = countquery.list();
        int totalSize = 0;
        if (ret != null && ret.size() > 0) {
            totalSize = ((Long) ret.get(0)).intValue();
        }

        Query query = getSession().createQuery(hql.toString());
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i, params.get(i));
        }

        query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

        List<SmsPersonTel> list = (List<SmsPersonTel>) query.list();
        return new PagingResult<SmsPersonTel>(totalSize, list);
    }

    // 更新联系电话
    @Transactional
    public String createTels(SmsPersonTel qryCond) {
        List<CodTelUpdateCol> infos = getTelRule();
        String truncateSQL = " truncate table Sms_PersonTel ";
        getSession().createSQLQuery(truncateSQL).executeUpdate();
        String insertsql = " insert into Sms_PersonTel select fileNo,fileNo,'0',name, TEL,'HealthFile.TEL',-1 from HealthFile ";
        int count = getSession().createSQLQuery(insertsql).executeUpdate();
        for (CodTelUpdateCol rule : infos) {
            String sql = " insert into Sms_PersonTel select a.fileNo,a.fileNo,'0',a.name, b."
                    + rule.getCol()
                    + ",'"
                    + rule.getTablename()
                    + "."
                    + rule.getCol()
                    + "',-1 "
                    + " from HealthFile a , "
                    + rule.getTablename()
                    + " b where a.fileNo = b.fileNo and not exists (select 1 from Sms_PersonTel c where c.fileNo = a.fileno) ";
            count += getSession().createSQLQuery(sql).executeUpdate();
        }
        String ret = "更新联系电话成功!共成功更新" + count + "户!";
        return ret;
    }

    // 短信发送任务启动
    public String smsStartSend() throws Exception {
        System.out.println("============启动任务生成");
        if (!taskUtil.isStarted()) {
            taskUtil.setStarted(true);

        }
        taskUtil.createMsgJob();
        sendMsgJobonly();
        return "true";
    }

    public boolean isStarted() {
        return taskUtil.isStarted();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PagingResult<Map> queryLogs(QryCondition qryCond, PagingParam pp)
            throws Exception {

        try {

            if (pp == null)
                pp = new PagingParam();
            Map leftlikemap = new HashMap();
            leftlikemap.put("vo.fileNo", null);
            Map likemap = new HashMap();
            likemap.put("vo.name", null);
            likemap.put("vo.tel", null);
            Map encMap = new HashMap();
            encMap.put("vo.name", null);
            encMap.put("vo.fileNo", null);
            Map intMap = new HashMap();
            intMap.put("vo.status", null);
            Map dateMap = new HashMap();
            dateMap.put("vo.smsdate", null);
            dateMap.put("convert(date,vo.smsdate)", null);
            StringBuilder where = new StringBuilder(" where vo.fileno = info.fileNo and vo.querytype='0' and vo.examid = rule.id ");
            List params = new ArrayList();
            if (StringUtils.hasText(qryCond.getDistrict())) {
                String dist = qryCond.getDistrict();
                if (dist.substring(dist.length() - 2).equals("00")) {
                    dist = dist.substring(0, dist.length() - 2);
                }
                where.append(" and vo.fileno like '" + dist + "%' ");
            }
            for (Condition obj : qryCond.getConditions()) {
                String key = obj.getFilterKey();
                String value = obj.getFilterVal();
                Object objvalue = value;
                System.out.println(key + "============" + value);
                if (StringUtils.hasText(value)) {
                    if (encMap.containsKey(key)) {
                        value = EncryptionUtils.encry(value);
                    }
                    if (leftlikemap.containsKey(key)) {
                        where.append(" and " + key + " like ?");
                        params.add(value + "%");
                    } else if (likemap.containsKey(key)) {
                        where.append(" and " + key + " like ?");
                        params.add("%" + objvalue + "%");
                    } else {
                        if (intMap.containsKey(key)) {
                            where.append(" and " + key + " = ? ");
                            params.add(Integer.parseInt(value));
                        } else if (dateMap.containsKey(key)) {
                            where.append(" and " + key + obj.getOpt() + "  ? ");
                            System.out.println("============"
                                    + new Date(Long.parseLong(value)));
                            Timestamp st = BusiUtils.parseDate(value);
                            params.add(st);
                        } else {
                            where.append(" and " + key + " = ? ");
                            params.add(value);
                        }
                    }
                }
            }
            // 这里的返回结果是用js进行的解密,所以没写解密的代码
            StringBuilder hql = new StringBuilder("select vo,info,rule  from TaskLog vo , PersonalInfo info,TaskRule rule  ")
                    .append(where + " order by vo.smsdate desc,vo.sendtime desc ");
            StringBuilder counthql = new StringBuilder(
                    "select count(*) from TaskLog vo , PersonalInfo info , TaskRule rule   ").append(where);
            log.debug("hql: " + hql.toString());

            Query countquery = getSession().createQuery(counthql.toString());
            for (int i = 0; i < params.size(); i++) {
                countquery.setParameter(i, params.get(i));
            }
            List ret = countquery.list();
            int totalSize = 0;
            if (ret != null && ret.size() > 0) {
                totalSize = ((Long) ret.get(0)).intValue();
            }

            Query query = getSession().createQuery(hql.toString());
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
            System.out.println("pp.getStart()===" + pp.getStart());
            System.out.println("pp.getLimit()===" + pp.getLimit());
            query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

            List<Object[]> list = query.list();
            List<Map> retlist = new ArrayList();
            for (Object[] objs : list) {
                TaskLog log = (TaskLog) objs[0];
                Map data = BeanUtils.describe(log);
                PersonalInfo info = (PersonalInfo) objs[1];
                TaskRule rule = (TaskRule) objs[2];
                data.put("birthday", info.getBirthday());
                data.put("idnumber", info.getIdnumber());
                System.out.println("===================" + new Gson().toJson(data));
                data.put("inputpage", rule.getInputpage());
                retlist.add(data);
            }

            return new PagingResult<Map>(totalSize, retlist);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    public List getQueryParams() {
        List<Map<String,String>> ret = new ArrayList();
        for(String key : queryParam.keySet()){
            Map<String,String> item = new HashMap();
            String inlist = queryParam.get(key).get("inlist");
            if("true".equals(inlist)) {
                String text = queryParam.get(key).get("text");
                item.put("key", key);
                item.put("text", text);
                ret.add(item);
            }
        }
        return ret;
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PagingResult<Map> queryLogsnew(Map paramsmap, PagingParam pp)
            throws Exception {
        Map<String, Object> condition = (Map<String, Object> )paramsmap;
        System.out.println("===========================");
        System.out.println(new Gson().toJson(condition));
        System.out.println("===========================");
        try {
            if (pp == null)
                pp = new PagingParam();
            StringBuilder where = new StringBuilder(" where vo.fileno = info.fileNo and info.fileNo = hf.fileNo and vo.querytype='0' and vo.examid = rule.id ");
            List params = new ArrayList();
            for (String key : condition.keySet()) {
                if (queryParam.containsKey(key) && condition.get(key)!=null && StringUtils.hasText(String.valueOf(condition.get(key)))) {
                    Map<String, String> item = queryParam.get(key);
                    String keystr = item.get("key");
                    String opt = item.get("opt");
                    String type = item.get("type");
                    String sql = item.get("sql");
                    System.out.println("key==="+key);

                    String value =String.valueOf( condition.get(key));
                    if("district".equals(type)){
                        while("00".equals(value.substring(value.length()-2))){
                            value = value.substring(0,value.length()-2);
                        }
                    }
                    where.append(" and "+keystr+" "+opt+" " + sql.replaceAll("\\?",value) + " ");
                }
            }
            // 这里的返回结果是用js进行的解密,所以没写解密的代码
            String selectcol = " select  vo,info,rule ";
            String fromstr = " from TaskLog vo ,HealthFile hf , PersonalInfo info,TaskRule rule  " +where ;
            String orderby = " order by vo.smsdate desc,vo.fileno ";
            String counthql =   "select count(*)   " + fromstr  ;
            String hql = selectcol+fromstr+orderby;
            System.out.println(hql);
            Query countquery = getSession().createQuery(counthql.toString());
            for (int i = 0; i < params.size(); i++) {
                countquery.setParameter(i, params.get(i));
            }
            List ret = countquery.list();
            int totalSize = 0;
            if (ret != null && ret.size() > 0) {
                totalSize = ((Long) ret.get(0)).intValue();
            }

            Query query = getSession().createQuery(hql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
            query.setFirstResult(pp.getStart()).setMaxResults(pp.getLimit());

            List<Object[]> list = query.list();
            List<Map> retlist = new ArrayList();
            for (Object[] objs : list) {
                TaskLog log = (TaskLog) objs[0];
                Map data = BeanUtils.describe(log);
                PersonalInfo info = (PersonalInfo) objs[1];
                TaskRule rule = (TaskRule) objs[2];
                data.put("birthday", info.getBirthday());
                data.put("idnumber", info.getIdnumber());
                data.put("inputpage", rule.getInputpage());
                retlist.add(data);
            }

            return new PagingResult<Map>(totalSize, retlist);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    // 定时任务
    @Transactional
    public void sendMsgJob() throws Exception {
        System.out.println("========发送短信的任务===========");
        taskUtil.createMsgJob();
        sendMsgJobonly();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void sendMsgJobonly() throws Exception {
        System.out.println("========只发送,不生成数据,发送短信的任务===========");
        taskUtil.setStarted(true);
        List<TaskLog> sendinglist = getHibernateTemplate().find(
                "from TaskLog where status = " + SmsUtil.IS_SENDED_FALSE + "");
        String exceptStr = "";

        System.out.println("sendinglist.size()===" + sendinglist.size());
        int count = 0;
        for (TaskLog log : sendinglist) {
            System.out.println((count++) + "   /" + sendinglist.size());
            if (taskUtil.isStarted()) {
                int result = 0;
                Serializable id = null;
                String tablename = camelcasify(log.getTablename());
                Type idtype = this
                        .getSessionFactory()
                        .getClassMetadata(
                                Class.forName("cn.net.tongfang.framework.security.vo."
                                        + tablename)).getIdentifierType();
//                System.out
//                        .println("==============idtype.getClass().getName()====="
//                                + idtype.getClass().getName());
                // 判断如果是数字型，则转换
                if ("org.hibernate.type.LongType".equals(idtype.getClass()
                        .getName())) {
                    id = Long.parseLong(log.getTableidvalue());
                } else {
                    id = log.getTableidvalue();
                }
                Object obj = this.getHibernateTemplate().get(
                        Class.forName("cn.net.tongfang.framework.security.vo."
                                + tablename), id);
                if (obj == null) {
                    exceptStr = exceptStr + "表" + log.getTablename() + "中主键为"
                            + log.getTableidvalue() + "的数据不存在!\r\n";
                }
                String msg = getMsgFromTemplate(log.getMsg(), obj);
//				log.setSendtime(new Timestamp(new Date().getTime()));
                log.setMsg(msg);
                log.setStatus(SmsUtil.IS_SENDED_TRUE);
                getHibernateTemplate()
                        .saveOrUpdate(log);
            } else {
                break;
            }
        }
        taskUtil.setStarted(false);
    }

    // 发送模板得到内容
    private String getMsgFromTemplate(String template, Object obj) {
        String ret = template;
        String pattern = "\\$\\s*\\(\\s*([\\w$+-]+)\\s*\\)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(template);
        while (m.find()) {
            try {
                String str = m.group(0);
                String testStr = m.group(1);
                String pattern1 = "(\\w+)\\s*([+-])\\s*(\\d+)([ymd])";
                Pattern p1 = Pattern.compile(pattern1);
                if (testStr.startsWith("$")) {
                    String testStr1 = testStr.substring(1);
                    Matcher m1 = p1.matcher(testStr1);
                    if (m1.find()) {
                        if ("today".equals(m1.group(1))) {
                            GregorianCalendar today = new GregorianCalendar();
                            int num = Integer.parseInt(m1.group(3));
                            if ("-".equals(m1.group(2))) {
                                num = -num;
                            }
                            today.add(typemap.get(m1.group(4)), num);
                            str = new SimpleDateFormat("yyyy年M月d日")
                                    .format(today.getTime());
                        }
                    }
                } else {
                    Matcher m1 = p1.matcher(testStr);
                    if (m1.find()) {
                        Object retobj = PropertyUtils.getProperty(obj,
                                m1.group(1));
                        if (retobj instanceof java.util.Date) {
                            GregorianCalendar dateobj = new GregorianCalendar();
                            dateobj.setTime((Date) retobj);
                            int num = Integer.parseInt(m1.group(3));
                            if ("-".equals(m1.group(2))) {
                                num = -num;
                            }
                            dateobj.add(typemap.get(m1.group(4)), num);
                            str = new SimpleDateFormat("yyyy年M月d日")
                                    .format(dateobj.getTime());
                        } else {
                            str = String.valueOf(retobj);
                        }
                    } else {
                        Object retobj = PropertyUtils.getProperty(obj, testStr);
                        if (retobj instanceof java.util.Date) {
                            str = new SimpleDateFormat("yyyy年M月d日")
                                    .format((Date) retobj);
                        } else {
                            str = String.valueOf(retobj);
                        }
                    }
                }
                ret = ret.replace(m.group(0), str);
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
                if (m.group(1).toLowerCase().indexOf("date") >= 0) {
                    String str = new SimpleDateFormat("yyyy年M月d日")
                            .format(new Date());
                    ret = ret.replace(m.group(0), str);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ret;
    }

    // 发送模板得到内容
    private Map<String, Boolean> getPropertysFromTemplate(String template) {
        String pattern = "\\$\\s*\\(\\s*([\\w$+-]+)\\s*\\)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(template);
        Map<String, Boolean> propertyList = new HashMap<String, Boolean>();
        while (m.find()) {
            try {
                String testStr = m.group(1);
                String pattern1 = "(\\w+)\\s*([+-])\\s*(\\d+)([ymd])";
                Pattern p1 = Pattern.compile(pattern1);
                if (!testStr.startsWith("$")) {
                    Matcher m1 = p1.matcher(testStr);
                    if (m1.find()) {
                        propertyList.put(m1.group(1), true);
                    } else {
                        propertyList.put(testStr, false);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return propertyList;
    }

    public void Test() {
        VisitBeforeBorn vb = new VisitBeforeBorn();
        vb.setNextVisitDate(new Timestamp(new Date().getTime()));
        System.out.println("==================="
                + getMsgFromTemplate("测试时间$(nextVisitDate-)", vb));
    }

    public static void main(String[] args) {
        TaskService ss = new TaskService();
        ss.Test();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public String querySendStatus() {
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        List isCreated = getSession()
                .createQuery("from TaskStatus where smsdate = ?")
                .setParameter(0, today).list();
        if (isCreated.isEmpty()) {
            return Send_Status_NotCreated;
        } else {
            if (taskUtil.isStarted()) {
                return Send_Status_Sending;
            } else {
                return Send_Status_CreatedNotSend;
            }
        }
    }

    // 停止短信发送
    public String smsStopSend() {
        if (taskUtil.isStarted()) {
            taskUtil.setStarted(false);
        }
        return "";
    }

    // 新增或修改联系电话
    @Transactional
    public String editTel(SmsPersonTel bo) throws Exception {
        SmsPersonTel module = bo.makeSaveVO();
        if (module.getId() != null && module.getId().isEmpty())
            module.setId(null);
        getHibernateTemplate().saveOrUpdate(module);
        return module.getId();
    }

    private void makeParamsList(Object qryCond, Map likemap,
                                StringBuilder where, List params) throws Exception {
        Map bean = PropertyUtils.describe(qryCond);
        for (Object key : bean.keySet()) {
            String keystr = (String) key;
            if ("class".equals(keystr)) {
                continue;
            }
            Object value = bean.get(key);
            if (value != null) {
                if (value instanceof String) {
                    if (!StringUtils.hasText((String) value)) {
                        value = null;
                    }
                }
                if (value != null) {
                    if (!likemap.containsKey(keystr)) {
                        where.append(" and " + keystr + " = ? ");
                        params.add(value);
                    } else {
                        where.append(" and " + keystr + " like ? ");
                        params.add("%" + value + "%");
                    }
                }
            }
        }
    }

    public TaskUtil getTaskUtil() {
        return taskUtil;
    }

    public void setTaskUtil(TaskUtil taskUtil) {
        this.taskUtil = taskUtil;
    }

    //TODO 判断是否有权限打开任务
    public Map hasTaskAuth() {
        Map ret = new HashMap();
        ret.put("hasauth", true);
        return ret;
    }

    //TODO 保存默认值
    @Transactional
    public Map saveDefault(String url, String name, String data) {
        System.out.println(url);
        System.out.println(name);
        System.out.println(data);
        Map ret = new HashMap();
        TaskDefaultValue defaultValue = new TaskDefaultValue();
        defaultValue.setUrlname(url);
        TaskDefaultValueId id = new TaskDefaultValueId();
        id.setName(name);
        id.setEmpcode(cn.net.tongfang.framework.security.SecurityManager.currentOperator().getUsername());
        defaultValue.setId(id);
        defaultValue.setContent(data);
        getHibernateTemplate().saveOrUpdate(defaultValue);
        ret.put("success", true);
        return ret;
    }

    //TODO 获得默认值列表
    public List getDefaultList(String url) {
        return getHibernateTemplate().find("select id.name from TaskDefaultValue where urlname = ? and id.empcode = ?  ", new String[]{url, cn.net.tongfang.framework.security.SecurityManager.currentOperator().getUsername()});
    }

    //TODO 获得默认值
    public Map getDefault(String url, String name) {
        TaskDefaultValue val = (TaskDefaultValue) (getHibernateTemplate().find(" from TaskDefaultValue where urlname = ? and empcode = ? and name = ?  ",
                new String[]{url, cn.net.tongfang.framework.security.SecurityManager.currentOperator().getUsername(),
                        name})).get(0);
        List cfglist = getHibernateTemplate().find(" from TaskDefaults where urlname = ?    ", url);
        Map ret = new HashMap();
        ret.put("val", val);
        ret.put("cfglist", cfglist);
        return ret;
    }

    //TODO 删除默认值
    public Map delDefault(String url, String name) {
        Map ret = new HashMap();
        ret.put("success", true);
        getHibernateTemplate().bulkUpdate(" delete TaskDefaultValue  where urlname = ? and name = ? ", new String[]{url, name});
        return ret;
    }

    //TODO 默认值重命名
    public Map renameDefault(String url, String name, String newname) {
        Map ret = new HashMap();
        getHibernateTemplate().bulkUpdate(" update TaskDefaultValue set name = ? where urlname = ? and name = ? ", new String[]{newname, url, name});
        ret.put("success", true);

        return ret;
    }

    //TODO 查询未完成任务
    private long queryUncompleteTaskData(String type, int status) throws Exception {
        Map ret = new HashMap();
        TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
        String sqlext = "";
        List<Object> params = new ArrayList();
        params.add(user.getOrgId());
        Timestamp today = new Timestamp(new Date().getTime());
        if ("curmonth".equals(type)) {
            sqlext = " and a.smsdate >= ? and a.smsdate < ?";
            Timestamp begin = new Timestamp(DateUtils.truncate(today, Calendar.MONTH).getTime());
            Timestamp end = new Timestamp(DateUtils.addMonths(DateUtils.truncate(today, Calendar.MONTH), 1).getTime());
            params.add(begin);
            params.add(end);
        } else if ("curday".equals(type)) {
            sqlext = " and a.smsdate >= ? and a.smsdate < ?";
            Timestamp begin = new Timestamp(DateUtils.truncate(today, Calendar.DATE).getTime());
            Timestamp end = new Timestamp(DateUtils.addDays(DateUtils.truncate(today, Calendar.DATE), 1).getTime());
            params.add(begin);
            params.add(end);
        } else if ("curweek".equals(type)) {
            sqlext = " and a.smsdate >= ? and a.smsdate < ?";
            Timestamp begin = new Timestamp(DateUtils.addWeeks(DateUtils.truncate(today, Calendar.DAY_OF_WEEK), -1).getTime());
            Timestamp end = new Timestamp(DateUtils.truncate(today, Calendar.DAY_OF_WEEK).getTime());
            params.add(begin);
            params.add(end);
        } else if ("lastweek".equals(type)) {
            sqlext = " and a.smsdate >= ? and a.smsdate < ?";
            int dayofweek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
            Timestamp begin = new Timestamp(DateUtils.addDays(DateUtils.truncate(today, Calendar.DATE), -dayofweek - 7).getTime());
            Timestamp end = new Timestamp(DateUtils.addWeeks(begin, 1).getTime());
            params.add(begin);
            params.add(end);
        }
        if (status > -1) {
            sqlext += " and a.status = ? ";
            params.add(status);
        }
        System.out.println(params.toArray());
        return (Long) getHibernateTemplate().find("select count(*) from  TaskLog a , HealthFile b,SamTaxempcode  emp " +
                "where a.fileno = b.fileNo and b.inputPersonId  = emp.loginname and  emp.orgId = ?   " + sqlext, params.toArray()).get(0);
    }

    //TODO 查询未完成任务
    public Map queryUncompleteTask(String type, int status) throws Exception {
        Map ret = new HashMap();
        long taskcount = queryUncompleteTaskData(type, status);
        ret.put("taskcount", taskcount);
        ret.put("success", true);
        return ret;
    }

    //TODO 查询图表数据
    public Map queryChatData() throws Exception {
        Map ret = new HashMap();
        TaxempDetail user = cn.net.tongfang.framework.security.SecurityManager.currentOperator();
        ret.put("monthcompelete", queryUncompleteTaskData("curmonth", 2));
        ret.put("monthuncompelete", queryUncompleteTaskData("curmonth", 1));
        ret.put("daycompelete", queryUncompleteTaskData("curday", 2));
        ret.put("dayuncompelete", queryUncompleteTaskData("curday", 1));
        ret.put("lastweekcompelete", queryUncompleteTaskData("lastweek", 2));
        ret.put("lastweekuncompelete", queryUncompleteTaskData("lastweek", 1));

        Timestamp today = new Timestamp(new Date().getTime());
        Timestamp begin = new Timestamp(DateUtils.truncate(today, Calendar.MONTH).getTime());
        Timestamp end = new Timestamp(DateUtils.addMonths(DateUtils.truncate(today, Calendar.MONTH), 1).getTime());
        List<Object[]> uncompletedata = getHibernateTemplate().find("select day(a.smsdate), count(*) from  TaskLog a , HealthFile b,SamTaxempcode  emp " +
                "where a.fileno = b.fileNo and b.inputPersonId  = emp.loginname " +
                "and  emp.orgId = ?  and a.smsdate >= ? and a.smsdate < ? and a.status = 1 group by day(a.smsdate) ", new Object[]{user.getOrgId(), begin, end});
        List<Object[]> completedata = getHibernateTemplate().find("select day(a.smsdate), count(*) from  TaskLog a , HealthFile b,SamTaxempcode  emp " +
                "where a.fileno = b.fileNo and b.inputPersonId  = emp.loginname " +
                "and  emp.orgId = ?  and a.smsdate >= ? and a.smsdate < ? and a.status = 2 group by day(a.smsdate) ", new Object[]{user.getOrgId(), begin, end});
        int dayofmonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        List uncomplete = new ArrayList();
        List complete = new ArrayList();
        List days = new ArrayList();
        boolean flag = false;
        System.out.println(dayofmonth);
        for (int i = 0; i < dayofmonth; i++) {
            days.add((i + 1) + "日");
            flag = false;
            for (int j = 0; j < uncompletedata.size(); j++) {
                if ((Integer) (uncompletedata.get(j)[0]) == i + 1) {
                    uncomplete.add(uncompletedata.get(j)[1]);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                uncomplete.add(0);
            }
            flag = false;
            for (int j = 0; j < completedata.size(); j++) {
                if ((Integer) (uncompletedata.get(j)[0]) == i + 1) {
                    complete.add(completedata.get(j)[1]);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                complete.add(0);
            }
        }
        System.out.println(begin);
        System.out.println(end);
        List catdata = getHibernateTemplate().find("select cat.name, sum(case when a.status = 1 then 1 else 0 end )," +
                "sum(case when a.status = 2 then 1 else 0 end )  from  TaskLog a ,TaskCat  cat, HealthFile b,SamTaxempcode  emp " +
                "where a.fileno = b.fileNo and b.inputPersonId   = emp.loginname and a.parentid = cat.id  " +
                "and  emp.orgId = ?  and a.smsdate >= ? and a.smsdate < ?   group by cat.name ", new Object[]{user.getOrgId(), begin, end});
        ret.put("catdata", catdata);
        ret.put("uncomplete", uncomplete);
        ret.put("complete", complete);
        ret.put("days", days);
        ret.put("success", true);
        return ret;
    }

    //TODO 更新任务标志
    public Map updateTaskCompelte(String taskid) throws Exception {
        Map ret = new HashMap();
        System.out.println("=========taskid============" + taskid);
        getHibernateTemplate().bulkUpdate(" update TaskLog set sendtime=getdate() ,status=2  where id = ? ", taskid);
        ret.put("success", true);
        return ret;
    }

    public String getCurrentUser() throws Exception {
        return SecurityManager.currentOperator().getTaxempname();
    }


    public List getCurrentDistrict() throws Exception {
        try {
            List ret = new ArrayList();
            String key = SecurityManager.currentOperator().getDistrictId();
            String id = key;
            if ("00".equals(key.substring(key.length() - 2))) {
                id = key.substring(0, key.length() - 2);
            }
            String vlaue = (String) examUtil.getDistrictMap().get(id);
            Map distmap = examUtil.getDistrictMap();
            Map root = new HashMap();
            root.put("id", key);
            root.put("text", vlaue);
//            root.put("parent", "#");
            List child = getSubDistrict(key);
            if (child == null || child.size() == 0) {
                root.put("haschild", false);
                ret.add(root);
            } else {
                root.put("haschild", true);
                root.put("children", child);
                ret.add(root);
            }
            return ret;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    private List getSubDistrict(String distid) throws Exception {
        List ret = new ArrayList();
        Map distmap = examUtil.getDistrictMap();
        List<District> sublist = examUtil.getDistrict(distid);
        if (sublist != null) {
            for (District d : sublist) {
                Map item = getNodeFromDistrict(d);
                List child = getSubDistrict(d.getId());
                if (child == null || child.size() == 0) {
                    item.put("haschild", false);
                    ret.add(item);
                } else {
                    item.put("haschild", true);
                    item.put("children", child);
                    ret.add(item);
                }
            }
            return ret;
        } else {
            return null;
        }
    }

    private Map getNodeFromDistrict(District d) throws Exception {
        Map ret = new HashMap();
        ret.put("id", d.getId());
        ret.put("text", d.getName());
//        ret.put("parent",d.getParentId());
        return ret;
    }

    public CommonExamUtil getExamUtil() {
        return examUtil;
    }

    public void setExamUtil(CommonExamUtil examUtil) {
        this.examUtil = examUtil;
    }
}
