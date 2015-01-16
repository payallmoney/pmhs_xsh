package cn.net.tongfang.web.port.shangdong;

import java.io.IOException;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.net.tongfang.web.port.BaseInter;
import cn.net.tongfang.web.util.FileNoGen;

import com.google.gson.Gson;

public class ShangDongInterfaceTestData {
	public static Map<String, String> testMap = new HashMap();
	static {
		testMap.put(
				"healthfile",
				"{"
						+ "    \"respCode\":\"0\","
						+ "    \"respMsg\":\"操作成功\","
						+ "    \"sign\":\"c2460e4b7ebb3641797d29b6097f45a1\","
						+ "\"baseInfos\":["
						+ "        {"
						+ "            \"phone\":\"13855555555\","
						+ "            \"pillallergic\":\"2\","
						+ "            \"cityId\":118,"
						+ "            \"cityCode\":\"\","
						+ "            \"addr\":\"测试住址\","
						+ "            \"orgDistrictId\":\"\","
						+ "            \"doctor\":\"责任医生\","
						+ "            \"maritalstatus\":\"2\","
						+ "            \"lifeEnvironment\":{"
						+ "                \"id\":200407,"
						+ "                \"drinkwater\":\"1\","
						+ "                \"livestockrail\":\"1\","
						+ "                \"toilet\":\"1\","
						+ "                \"fueltype\":\"1\","
						+ "                \"blowmeasure\":\"3\","
						+ "                \"archiveid\":\"37010200500100284\""
						+ "            },"
						+ "            \"createunit\":118,"
						+ "            \"houseRelation\":\"1\","
						+ "            \"orgProvinceCode\":\"37\","
						+ "            \"orgTownId\":\"\","
						+ "            \"workunit\":\"工作单位\","
						+ "            \"createdDate\":\"2014-03-05 16:33:35\","
						+ "            \"diseasenditionEx\":\"眼里残疾\","
						+ "            \"orgDistinctCode\":\"370102\","
						+ "            \"diseaseEx\":\"遗传病史\","
						+ "            \"job\":\"2\","
						+ "            \"idcard\":\"111111199902021234\","
						+ "            \"townId\":165,"
						+ "            \"bloodtype\":\"1\","
						+ "            \"nation\":\"1\","
						+ "            \"distinctCode\":\"\","
						+ "            \"createdBy\":7,"
						+ "            \"email\":\"test@qq.com\","
						+ "            \"livetype\":\"1\","
						+ "            \"orgProvinceId\":15,"
						+ "            \"contactphone\":\"13888888888\","
						+ "            \"culture\":\"3\","
						+ "            \"provinceId\":15,"
						+ "            \"exposure\":\"2\","
						+ "            \"birthday\":\"2014-03-06 11:08:01\","
						+ "            \"sex\":\"1\","
						+ "            \"minority\":\"少数民族\","
						+ "            \"villageCode\":\"\","
						+ "            \"provinceCode\":\"37\","
						+ "            \"pillallergicOther\":\"药物过敏史其他\","
						+ "            \"lastUpdateBy\":7,"
						+ "            \"orgCityCode\":\"3701\","
						+ "            \"rh\":\"2\","
						+ "            \"orgTownCode\":\"370102005\","
						+ "            \"disease\":\"2\","
						+ "            \"id\":1161790,"
						+ "            \"isDel\":\"N\","
						+ "            \"districtId\":917,"
						+ "            \"medicalpaytype\":\"2\","
						+ "            \"fyArchiveid\":\"\","
						+ "            \"archiveid\":\"37010200500100284\","
						+ "            \"customername\":\"居民姓名修改xml\","
						+ "            \"houseRealOther\":\"与户主关系其他\","
						+ "            \"orgCityId\":118,"
						+ "            \"lastUpdateDate\":\"2014-03-06 11:08:01\","
						+ "            \"townCode\":\"\","
						+ "            \"illnessHistoryInfos\":["
						+ "                {"
						+ "                    \"id\":38708,"
						+ "                    \"therioma\":\"恶性肿瘤名称\","
						+ "                    \"illnessname\":\"1\","
						+ "                    \"jobillness\":\"职业病\","
						+ "                    \"illnesstype\":\"1\","
						+ "                    \"diagnosetime\":\"2014-03-06 11:08:01\","
						+ "                    \"illnessOther\":\"既往史疾病其他\","
						+ "                    \"illnessnameOther\":\"既往史名称\","
						+ "                    \"archiveid\":\"37010200500100284\""
						+ "                },"
						+ "                {"
						+ "                    \"id\":38708,"
						+ "                    \"therioma\":\"恶性肿瘤名称\","
						+ "                    \"illnessname\":\"2\","
						+ "                    \"jobillness\":\"职业病\","
						+ "                    \"illnesstype\":\"2\","
						+ "                    \"diagnosetime\":\"2014-03-06 11:08:01\","
						+ "                    \"illnessOther\":\"既往史疾病其他\","
						+ "                    \"illnessnameOther\":\"既往史名称\","
						+ "                    \"archiveid\":\"37010200500100284\""
						+ "                },"
						+ "                {"
						+ "                    \"id\":38708,"
						+ "                    \"therioma\":\"恶性肿瘤名称\","
						+ "                    \"illnessname\":\"3\","
						+ "                    \"jobillness\":\"职业病\","
						+ "                    \"illnesstype\":\"3\","
						+ "                    \"diagnosetime\":\"2014-03-06 11:08:01\","
						+ "                    \"illnessOther\":\"既往史疾病其他\","
						+ "                    \"illnessnameOther\":\"既往史名称\","
						+ "                    \"archiveid\":\"37010200500100284\""
						+ "                },"
						+ "                {"
						+ "                    \"id\":38708,"
						+ "                    \"therioma\":\"恶性肿瘤名称\","
						+ "                    \"illnessname\":\"4\","
						+ "                    \"jobillness\":\"职业病\","
						+ "                    \"illnesstype\":\"4\","
						+ "                    \"diagnosetime\":\"2014-03-06 11:08:01\","
						+ "                    \"illnessOther\":\"既往史疾病其他\","
						+ "                    \"illnessnameOther\":\"既往史名称\","
						+ "                    \"archiveid\":\"37010200500100284\""
						+ "                }"
						+ "            ],"
						+ "            \"villageId\":162,"
						+ "            \"familyHistory\":{"
						+ "                \"familytype\":\"1\","
						+ "                \"id\":5837,"
						+ "                \"brothersisterhistoryOther\":\"兄弟其他\","
						+ "                \"childrenhistory\":\"2\","
						+ "                \"brothersisterhistory\":\"2\","
						+ "                \"childrenhistoryOther\":\"子女其他\","
						+ "                \"motherhistoryOther\":\"母亲其他\","
						+ "                \"fatherhistoryOther\":\"父亲其他\","
						+ "                \"motherhistory\":\"1\","
						+ "                \"fatherhistory\":\"1\","
						+ "                \"archiveid\":\"37010200500100284\""
						+ "            },"
						+ "            \"orgVillageCode\":\"370102005001\","
						+ "            \"customerid\":\"\","
						+ "            \"contactname\":\"联系人姓名\","
						+ "            \"householdAddr\":\"户籍地址\","
						+ "            \"medicalpaytypeOther\":\"医疗费用支付方式其它\","
						+ "            \"populationType\":\"1\","
						+ "            \"diseasendition\":\"2\","
						+ "            \"orgVillageId\":\"\"" + "        }"
						+ "    ]" + "}");
		testMap.put(
				"healthexam","{ "+
						"    \"sign\": \"ca9b2f53fe60d86a6668cfa4a5d710df\", "+
						"    \"respCode\": \"0\", "+
						"    \"pagePara\": { "+
						"        \"total\": 3, "+
						"        \"recordStart\": 0, "+
						"        \"pageSize\": 20, "+
						"        \"recordEnd\": 20, "+
						"        \"pageIndex\": 1"+
						"    }, "+
						"    \"respMsg\": \"查询体检信息列表成功！\", "+
						"    \"customerBaseInfos\": [ "+
						"        { "+
						"            \"medications\": [ "+
						"                { "+
						"                    \"id\": 39600, "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"starttime\": \"主要用药情况用药时间1\", "+
						"                    \"pilldependence\": \"2\", "+
						"                    \"usenum\": \"主要用药情况用量1\", "+
						"                    \"medicinalname\": \"主要用药情况药物1\", "+
						"                    \"useage\": \"主要用药情况用法1\""+
						"                }, "+
						"                { "+
						"                    \"id\": 39602, "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"starttime\": \"主要用药情况用药时间2\", "+
						"                    \"pilldependence\": \"2\", "+
						"                    \"usenum\": \"主要用药情况用量2\", "+
						"                    \"medicinalname\": \"主要用药情况药物2\", "+
						"                    \"useage\": \"主要用药情况用法2\""+
						"                }, "+
						"                { "+
						"                    \"id\": 39604, "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"starttime\": \"主要用药情况用药时间3\", "+
						"                    \"pilldependence\": \"2\", "+
						"                    \"usenum\": \"主要用药情况用量3\", "+
						"                    \"medicinalname\": \"主要用药情况药物3\", "+
						"                    \"useage\": \"主要用药情况用法3\""+
						"                }, "+
						"                { "+
						"                    \"id\": 39606, "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"starttime\": \"主要用药情况用药时间4\", "+
						"                    \"pilldependence\": \"2\", "+
						"                    \"usenum\": \"主要用药情况用量4\", "+
						"                    \"medicinalname\": \"主要用药情况药物4\", "+
						"                    \"useage\": \"主要用药情况用法4\""+
						"                }, "+
						"                { "+
						"                    \"id\": 39608, "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"starttime\": \"主要用药情况用药时间5\", "+
						"                    \"pilldependence\": \"2\", "+
						"                    \"usenum\": \"主要用药情况用量5\", "+
						"                    \"medicinalname\": \"主要用药情况药物5\", "+
						"                    \"useage\": \"主要用药情况用法5\""+
						"                }, "+
						"                { "+
						"                    \"id\": 39610, "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"starttime\": \"主要用药情况用药时间6\", "+
						"                    \"pilldependence\": \"2\", "+
						"                    \"usenum\": \"主要用药情况用量6\", "+
						"                    \"medicinalname\": \"主要用药情况药物6\", "+
						"                    \"useage\": \"主要用药情况用法6\""+
						"                }"+
						"            ], "+
						"            \"other\": \"其他\", "+
						"            \"assessmentGuide\": { "+
						"                \"id\": 484094, "+
						"                \"other\": \"危险因素控制qita\", "+
						"                \"physicalid\": \"176030\", "+
						"                \"aim\": \"70\", "+
						"                \"dangercontrol\": \"1,2,3,4,5,6,7\", "+
						"                \"exception3\": \"健康评价异常3\", "+
						"                \"exception4\": \"健康评价异常4\", "+
						"                \"exception1\": \"健康评价异常1\", "+
						"                \"advice\": \"建议接种疫苗\", "+
						"                \"exception2\": \"健康评价异常2\", "+
						"                \"healthzhidao\": \"1,2,3\", "+
						"                \"isnormal\": \"2\""+
						"            }, "+
						"            \"mediPhysDist\": { "+
						"                \"faint\": \"2\", "+
						"                \"id\": 336225, "+
						"                \"bloodstasis\": \"2\", "+
						"                \"yin\": \"2\", "+
						"                \"physicalid\": \"176030\", "+
						"                \"yang\": \"2\", "+
						"                \"muggy\": \"2\", "+
						"                \"mild\": \"2\", "+
						"                \"qiconstraint\": \"2\", "+
						"                \"characteristic\": \"2\", "+
						"                \"phlegmdamp\": \"2\""+
						"            },  "+
						"            \"lifeStyle\": { "+
						"                \"exerciseexistense\": \"锻炼方式\", "+
						"                \"physical\": \"物理因素\", "+
						"                \"other\": \"触史其它\", "+
						"                \"physicalprotect\": \"2\", "+
						"                \"isdrinkforbiddon\": \"2\", "+
						"                \"fangsheprotectEx\": \"防护措施防护\", "+
						"                \"dustprotectEx\": \"粉尘防护\", "+
						"                \"id\": 336323, "+
						"                \"eathobby\": \"3,4,5,6\", "+
						"                \"chemprotectEx\": \"化学物质防护\", "+
						"                \"drinkthisyear\": \"1\", "+
						"                \"chem\": \"化学物质\", "+
						"                \"drinktype\": \"1,2,3,4,5\", "+
						"                \"smokedaynum\": 3, "+
						"                \"excisepersisttime\": 3, "+
						"                \"otherprotectEx\": \"触史其它防护\", "+
						"                \"daydrinkvolume\": 1, "+
						"                \"exercisetime\": 60, "+
						"                \"forbiddonage\": 27, "+
						"                \"smokecondition\": \"2\", "+
						"                \"physicalprotectEx\": \"物理因素防护\", "+
						"                \"fangsheprotect\": \"2\", "+
						"                \"drinkstartage\": 18, "+
						"                \"careerharmfactorhistory\": \"2\", "+
						"                \"fangshe\": \"放射物质\", "+
						"                \"otherprotect\": \"2\", "+
						"                \"smokeagestart\": 18, "+
						"                \"physicalid\": \"176030\", "+
						"                \"drinktypeOther\": \"饮酒种类其他\", "+
						"                \"dust\": \"粉尘\", "+
						"                \"exerciserate\": \"2\", "+
						"                \"chemprotect\": \"2\", "+
						"                \"drinkrate\": \"2\", "+
						"                \"smokeageforbiddon\": 33, "+
						"                \"worktype\": \"工种\", "+
						"                \"worktime\": 5, "+
						"                \"dustprotect\": \"2\""+
						"            }, "+
						"            \"doctor\": \"责任医生\", "+
						"            \"id\": 176031, "+
						"            \"isDel\": \"N\", "+
						"            \"checkdate\": \"2014-03-06 15:46:31\", "+
						"            \"assistCheck\": { "+
						"                \"albumin\": 10, "+
						"                \"hbsag\": \"2\", "+
						"                \"cb\": 4, "+
						"                \"scr\": 80, "+
						"                \"other\": \"辅助检查其他\", "+
						"                \"fpgdl\": \"\", "+
						"                \"tg\": 1.4, "+
						"                \"glu\": \"140\", "+
						"                \"bchaoEx\": \"B超异常\", "+
						"                \"ecgEx\": \"心电图异常\", "+
						"                \"id\": 335828, "+
						"                \"chestxEx\": \"胸部X线片异常\", "+
						"                \"bp\": 40, "+
						"                \"tc\": 4, "+
						"                \"glt\": \"\", "+
						"                \"ket\": \"100\", "+
						"                \"bloodOther\": \"血常规其他\", "+
						"                \"plt\": 200, "+
						"                \"wbc\": 5, "+
						"                \"urineOther\": \"尿常规其他\", "+
						"                \"bld\": \"无\", "+
						"                \"cervixEx\": \"宫颈涂片异常\", "+
						"                \"hbalc\": 6, "+
						"                \"ecg\": \"2\", "+
						"                \"chestx\": \"2\", "+
						"                \"hb\": 130, "+
						"                \"sgpt\": 30, "+
						"                \"tbil\": 10, "+
						"                \"fob\": \"2\", "+
						"                \"lowCho\": 3, "+
						"                \"physicalid\": \"176030\", "+
						"                \"fpgl\": 4.5, "+
						"                \"bun\": 4, "+
						"                \"pc\": 4, "+
						"                \"pro\": \"60\", "+
						"                \"hype\": 140, "+
						"                \"cervix\": \"2\", "+
						"                \"got\": 30, "+
						"                \"heiCho\": 5, "+
						"                \"bchao\": \"2\""+
						"            }, "+
						"            \"physicalExam\": { "+
						"                \"breathsoundsEx\": \"呼吸音\", "+
						"                \"anus\": \"5\", "+
						"                \"other\": \"查体其他\", "+
						"                \"breathsounds\": \"2\", "+
						"                \"presspainEx\": \"压痛\", "+
						"                \"liverEx\": \"肝大\", "+
						"                \"eyeround\": \"2\", "+
						"                \"attachEx\": \"附件异常\", "+
						"                \"vaginaEx\": \"阴道异常\", "+
						"                \"voiced\": \"2\", "+
						"                \"skin\": \"7\", "+
						"                \"sclereEx\": \"巩膜其他\", "+
						"                \"id\": 335758, "+
						"                \"lymphEx\": \"淋巴结其他\", "+
						"                \"enclosedmassEx\": \"包块\", "+
						"                \"vagina\": \"2\", "+
						"                \"spleenEx\": \"脾大\", "+
						"                \"heartrate\": \"75\", "+
						"                \"lymph\": \"4\", "+
						"                \"presspain\": \"2\", "+
						"                \"barrelchest\": \"2\", "+
						"                \"footback\": \"2\", "+
						"                \"noiseEx\": \"杂音\", "+
						"                \"skinEx\": \"皮肤其他\", "+
						"                \"eyeroundEx\": \"眼底异常\", "+
						"                \"breastEx\": \"乳腺其他\", "+
						"                \"vulvaEx\": \"外阴异常\", "+
						"                \"rale\": \"4\", "+
						"                \"enclosedmass\": \"2\", "+
						"                \"heartrhythm\": \"2\", "+
						"                \"voicedEx\": \"移动性浊音\", "+
						"                \"anusEx\": \"肛门指诊其他\", "+
						"                \"sclera\": \"4\", "+
						"                \"cervixuteriEx\": \"宫颈异常\", "+
						"                \"noise\": \"2\", "+
						"                \"breast\": \"5\", "+
						"                \"corpusEx\": \"宫体异常\", "+
						"                \"vulva\": \"2\", "+
						"                \"corpus\": \"2\", "+
						"                \"physicalid\": \"176030\", "+
						"                \"edema\": \"4\", "+
						"                \"cervixuteri\": \"2\", "+
						"                \"attach\": \"2\", "+
						"                \"liver\": \"2\", "+
						"                \"raleEx\": \"啰音其他\", "+
						"                \"spleen\": \"2\""+
						"            }, "+
						"            \"healthQuestion\": { "+
						"                \"nerveDisOther\": \"神经系统疾病\", "+
						"                \"vesselOther\": \"血管疾病 其他\", "+
						"                \"brainOther\": \"脑血管疾病 其他\", "+
						"                \"vesselDis\": \"2,3,4\", "+
						"                \"eyeOther\": \"眼部疾病 其他\", "+
						"                \"renalDis\": \"2,3,4,5,6\", "+
						"                \"renalOther\": \"肾脏疾病 其他\", "+
						"                \"elseDis\": \"2\", "+
						"                \"nerveDis\": \"2\", "+
						"                \"id\": 335756, "+
						"                \"heartOther\": \"心脏疾病 其他\", "+
						"                \"brainDis\": \"2,3,4,5,6\", "+
						"                \"elseDisOther\": \"其它系统疾病\", "+
						"                \"physicalid\": \"176030\", "+
						"                \"heartDis\": \"2,3,4,5,6,7\", "+
						"                \"eyeDis\": \"2,3,4,5\""+
						"            }, "+
						"            \"createdDate\": \"2014-03-06 15:46:31\", "+
						"            \"visceraFunction\": { "+
						"                \"id\": 335967, "+
						"                \"toothresides\": \"2\", "+
						"                \"physicalid\": \"176030\", "+
						"                \"righteyecorrect\": 4.5, "+
						"                \"lefteyecorrect\": 4.5, "+
						"                \"lips\": \"1\", "+
						"                \"leftview\": 5, "+
						"                \"pharyngeal\": \"2\", "+
						"                \"listen\": \"1\", "+
						"                \"rightview\": 5, "+
						"                \"sportfunction\": \"1\""+
						"            }, "+
						"            \"archiveid\": \"37108100000000001\", "+
						"            \"familyBEDHistorys\": [ "+
						"                { "+
						"                    \"id\": 5979, "+
						"                    \"illcasenums\": \"654\", "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"inhospitaldate\": \"2014-03-06 15:46:31\", "+
						"                    \"hospitalname\": \"家庭病床史医疗机构（2）\", "+
						"                    \"reasons\": \"家庭病床史原因（2)\", "+
						"                    \"outhospitaldate\": \"2014-03-06 15:46:31\""+
						"                }, "+
						"                { "+
						"                    \"id\": 5977, "+
						"                    \"illcasenums\": \"456\", "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"inhospitaldate\": \"2014-03-06 15:46:31\", "+
						"                    \"hospitalname\": \"家庭病床史医疗机构（1）\", "+
						"                    \"reasons\": \"家庭病床史原因（1)\", "+
						"                    \"outhospitaldate\": \"2014-03-06 15:46:31\""+
						"                }"+
						"            ], "+
						"            \"idcard\": \"371451201212065221\", "+
						"            \"hospitalHistorys\": [ "+
						"                { "+
						"                    \"id\": 5205, "+
						"                    \"illcasenum\": \"321\", "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"inhospitaldate\": \"2014-03-06 15:46:31\", "+
						"                    \"reason\": \"住院史原因（2）\", "+
						"                    \"hospitalname\": \"住院史医疗机构（2）\", "+
						"                    \"outhospitaldate\": \"2014-03-06 15:46:31\""+
						"                }, "+
						"                { "+
						"                    \"id\": 5204, "+
						"                    \"illcasenum\": \"123\", "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"inhospitaldate\": \"2014-03-06 15:46:31\", "+
						"                    \"reason\": \"住院史原因（1）\", "+
						"                    \"hospitalname\": \"住院史医疗机构（1）\", "+
						"                    \"outhospitaldate\": \"2014-03-06 15:46:31\""+
						"                }"+
						"            ], "+
						"            \"generalCondition\": {"+
						"                \"tem\": 35, "+
						"                \"weight\": 75, "+
						"                \"rightpre\": 121, "+
						"                \"bmi\": 117.19, "+
						"                \"id\": 335916, "+
						"                \"physicalid\": \"176030\", "+
						"                \"breathRate\": 60, "+
						"                \"height\": 80, "+
						"                \"waistline\": 80, "+
						"                \"leftpre\": 120, "+
						"                \"rightheight\": 81, "+
						"                \"leftheight\": 80, "+
						"                \"pulserate\": 75"+
						"            }, "+
						"            \"physicalid\": \"176030\", "+
						"            \"createdBy\": 472, "+
						"            \"inoculationHistorys\": ["+
						"                {"+
						"                    \"id\": 6921, "+
						"                    \"pillname\": \"非免疫规划预防接种史药物3\", "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"inoculationdate\": \"2014-03-06 15:46:31\", "+
						"                    \"inoculationhistory\": \"非免疫规划预防接种史jigou3\", "+
						"                }, "+
						"                {"+
						"                    \"id\": 6919, "+
						"                    \"pillname\": \"非免疫规划预防接种史药物1\", "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"inoculationdate\": \"2014-03-06 15:46:31\", "+
						"                    \"inoculationhistory\": \"非免疫规划预防接种史jigou1\", "+
						"                }, "+
						"                {"+
						"                    \"id\": 6920, "+
						"                    \"pillname\": \"非免疫规划预防接种史药物2\", "+
						"                    \"physicalid\": \"176030\", "+
						"                    \"inoculationdate\": \"2014-03-06 15:46:31\", "+
						"                    \"inoculationhistory\": \"非免疫规划预防接种史jigou2\", "+
						"                }"+
						"            ], "+
						"            \"symptom\": \"2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25\""+
						"        }"+
						"    ]"+
						"}");
	}
}
