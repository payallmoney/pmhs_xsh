package cn.net.tongfang.web.service.chart;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.BirthCertificate;
import cn.net.tongfang.framework.security.vo.BusinessDataGrid;
import cn.net.tongfang.framework.security.vo.IntInpatient;
import cn.net.tongfang.framework.security.vo.IntOutpatient;
import cn.net.tongfang.framework.security.vo.SummaryStatistics01;
import cn.net.tongfang.framework.security.vo.SummaryStatisticsHivandSyphilis;
import cn.net.tongfang.framework.util.CommonConvertUtils;
import cn.net.tongfang.framework.util.EncryptionUtils;
import cn.net.tongfang.framework.util.service.vo.PagingParam;
import cn.net.tongfang.framework.util.service.vo.PagingResult;
import cn.net.tongfang.web.service.bo.BirthCertifiQry;
import cn.net.tongfang.web.service.bo.PatientQry;
import cn.net.tongfang.web.service.bo.PersonalInfoFBO;
import cn.net.tongfang.web.service.bo.SummaryQry;

public class SummaryChartService extends HibernateDaoSupport {

	public SummaryChartBean querySummaryChartStatistics(SummaryQry qry) {
		Query query = getSession().getNamedQuery("InputPersonProc");
		TaxempDetail user = SecurityManager.currentOperator();
		String str_where = "";
		if (qry.getContainLowerLevel().equals("0"))
			str_where = " And A.InputDate >= '"
					+ CommonConvertUtils.dateToStringWithDelimiter(qry
							.getStartDate())
					+ " 00:00:00' And A.InputDate <= '"
					+ CommonConvertUtils.dateToStringWithDelimiter(qry
							.getEndDate())
					+ " 23:59:59' And A.InputPersonID In (Select loginname From sam_taxempcode Where org_id = "
					+ qry.getOrgId() + " ) ";
		else if (qry.getContainLowerLevel().equals("1")) {
			str_where = " And A.InputDate >= '"
					+ CommonConvertUtils.dateToStringWithDelimiter(qry
							.getStartDate())
					+ " 00:00:00' And A.InputDate <= '"
					+ CommonConvertUtils.dateToStringWithDelimiter(qry
							.getEndDate())
					+ " 23:59:59' And A.InputPersonID in (Select loginname From sam_taxempcode Where org_id in "
					+ " (Select ID From Organization Where DistrictNumber Like "
					+ " (Select DistrictNumber + '%' From Organization Where ID = "
					+ qry.getOrgId() + "))) ";
		}
		query.setParameter(0, user.getUsername());
		query.setParameter(1, qry.getStatisticType());
		query.setParameter(2, str_where);
		query.setParameter(3, qry.getStatisticResult());
		query.setParameter(4, qry.getIsQryWipeOut());
		List list = query.list();
//		for(int i =0;i < list.size();i++){
//			SummaryStatistics01 vo = (SummaryStatistics01)list.get(i);
//			System.out.println("----" + vo.getInputPersonId() + "----") ;
//			
//		}
		SummaryChartBean scb = constructStackedXML(qry.getStatisticType(), qry
				.getStatisticResult(), list);
		return scb;
	}

	public SummaryChartBean constructStackedXML(String statisticType,
			String statisticResult, List<SummaryStatistics01> dataList) {
		SummaryChartBean scb = new SummaryChartBean();

		String org = statisticType.substring(0, 1);
		String inputPerson = statisticType.substring(1, 2);
		String date = statisticType.substring(2, 3);

		String healthfile = statisticResult.substring(0, 1);
		String children = statisticResult.substring(1, 2);
		String maternal = statisticResult.substring(2, 3);
		String chronicDisease = statisticResult.substring(3, 4);
		String vacciInfor = statisticResult.substring(4, 5);

		int colspanSize = 0;
		String strCategories = "<categories>";
		if (healthfile.equals("1")) {
			colspanSize = colspanSize + 2;
			strCategories += "<category label='农业人口档案数' />";
			strCategories += "<category label='城镇人口档案数' />";
		}
		if (children.equals("1")) {
			colspanSize = colspanSize + 5;
			strCategories += "<category label='新生儿家庭访视' />";
			strCategories += "<category label='1岁以内儿童体检' />";
			strCategories += "<category label='1~2岁儿童体检' />";
			strCategories += "<category label='3~6岁儿童体检' />";
			strCategories += "<category label='儿童体检总和' />";
		}
		if (maternal.equals("1")) {
			colspanSize = colspanSize + 5;
			strCategories += "<category label='第1次产前随访' />";
			strCategories += "<category label='第2~5次产前随访' />";
			strCategories += "<category label='产前随访总和' />";
			strCategories += "<category label='产后访视' />";
			strCategories += "<category label='产后42天体检' />";
		}
		if (chronicDisease.equals("1")) {
			colspanSize = colspanSize + 3;
			strCategories += "<category label='高血压随访' />";
			strCategories += "<category label='糖尿病随访' />";
			strCategories += "<category label='重性精神病随访' />";
		}
		if (vacciInfor.equals("1")) {
			colspanSize = colspanSize + 1;
			strCategories += "<category label='疫苗接种数' />";
		}
		strCategories += "</categories>";

		String datasetData = "";

		String stackedXml = "";

		String selectData = "";
		if (inputPerson.equals("1")) {
			selectData = "<select onchange=\"subChart('1',this.value)\">";
		}
		if (org.equals("1")) {
			selectData = "<select onchange=\"subChart('2',this.value)\">";
		}
		if (date.equals("1")) {
			selectData = "<select onchange=\"subChart('3',this.value)\">";
		}
		if (date.equals("2")) {
			selectData = "<select onchange=\"subChart('4',this.value)\">";
		}
		if (date.equals("3")) {
			selectData = "<select onchange=\"subChart('5',this.value)\">";
		}
		selectData += "<option value ='0'></option>";
		if(dataList.size() <= 1){
			scb.setXmlData("");
			scb.setSelectData("");
			return scb;
		}
		for (int i = 0; i < dataList.size() - 1; i++) {
			SummaryStatistics01 vo = dataList.get(i);

			if (inputPerson.equals("1")) {
				stackedXml = "<chart caption='操作人员汇总统计' numberPrefix='' formatNumberScale='0' chartLeftMargin='50' chartRightMargin='50' useRoundEdges='1'>";
				datasetData += "<dataset seriesName='" + vo.getUserName()
						+ "'>";
				selectData += "<option value ='" + vo.getInputPersonId() + "'>"
						+ vo.getUserName() + "</option>";
				
			}

			if (org.equals("1")) {
				stackedXml = "<chart caption='医疗机构汇总统计' numberPrefix='' formatNumberScale='0' chartLeftMargin='50' chartRightMargin='50' useRoundEdges='1'>";
				datasetData += "<dataset seriesName='" + vo.getOrgName() + "'>";
				selectData += "<option value ='" + vo.getOrgId() + "'>"
						+ vo.getOrgName() + "</option>";
			}

			if (date.equals("1")) {
				stackedXml = "<chart caption='按年汇总统计' numberPrefix='' formatNumberScale='0' chartLeftMargin='50' chartRightMargin='50' useRoundEdges='1'>";
				datasetData += "<dataset seriesName='" + vo.getGroupDate()
						+ "'>";
				selectData += "<option value ='" + vo.getGroupDate() + "'>"
						+ vo.getGroupDate() + "</option>";
			}

			if (date.equals("2")) {
				stackedXml = "<chart caption='按月汇总统计' numberPrefix='' formatNumberScale='0' chartLeftMargin='50' chartRightMargin='50' useRoundEdges='1'>";
				datasetData += "<dataset seriesName='" + vo.getGroupDate()
						+ "'>";
				selectData += "<option value ='" + vo.getGroupDate() + "'>"
						+ vo.getGroupDate() + "</option>";
			}

			if (date.equals("3")) {
				stackedXml = "<chart caption='按日汇总统计' numberPrefix='' formatNumberScale='0' chartLeftMargin='50' chartRightMargin='50' useRoundEdges='1'>";
				datasetData += "<dataset seriesName='" + vo.getGroupDate()
						+ "'>";
				selectData += "<option value ='" + vo.getGroupDate() + "'>"
						+ vo.getGroupDate() + "</option>";
			}

			if (healthfile.equals("1")) {
				datasetData += "<set value='" + vo.getVhealthCount() + "' />";
				datasetData += "<set value='" + vo.getChealthCount() + "' />";
			}
			if (children.equals("1")) {
				datasetData += "<set value='" + vo.getBabyVisitCount() + "' />";
				datasetData += "<set value='" + vo.getChildren01count()
						+ "' />";
				datasetData += "<set value='" + vo.getChildren02count()
						+ "' />";
				datasetData += "<set value='" + vo.getChildren36count()
						+ "' />";
				datasetData += "<set value='" + vo.getBabyAllVisitCount()
						+ "' />";
			}
			if (maternal.equals("1")) {
				datasetData += "<set value='"
						+ vo.getFirstVistBeforeBornCount() + "' />";
				datasetData += "<set value='" + vo.getVisitBeforeBornCount()
						+ "' />";
				datasetData += "<set value='" + vo.getPrenatalVisitCount()
						+ "' />";
				datasetData += "<set value='" + vo.getVisitAfterBornCount()
						+ "' />";
				datasetData += "<set value='" + vo.getVisitAfterBorn42count()
						+ "' />";
			}
			if (chronicDisease.equals("1")) {
				datasetData += "<set value='" + vo.getHypertensionVisitCount()
						+ "' />";
				datasetData += "<set value='" + vo.getDiabetesVisitCount()
						+ "' />";
				datasetData += "<set value='" + vo.getFuriousVisitCount()
						+ "' />";
			}
			if (vacciInfor.equals("1")) {
				datasetData += "<set value='" + vo.getVacciInfoCount() + "' />";
			}
			datasetData += "</dataset>";

		}
		selectData += "</select>";

		// Assemble the entire XML now
		stackedXml += strCategories + datasetData + "</chart>";
		scb.setXmlData(stackedXml);
		scb.setSelectData(selectData);
//		System.out.println(stackedXml);
		return scb;

	}

	public String querySummaryChartStatisticsBySub(SummaryQry qry, String subId) {
		Query query = getSession().getNamedQuery("InputPersonProc");
		TaxempDetail user = SecurityManager.currentOperator();
		String str_where = "";
		if (qry.getContainLowerLevel().equals("0"))
			str_where = " And A.InputDate >= '"
					+ CommonConvertUtils.dateToStringWithDelimiter(qry
							.getStartDate())
					+ " 00:00:00' And A.InputDate <= '"
					+ CommonConvertUtils.dateToStringWithDelimiter(qry
							.getEndDate())
					+ " 23:59:59' And A.InputPersonID In (Select loginname From sam_taxempcode Where org_id = "
					+ qry.getOrgId() + " ) ";
		else if (qry.getContainLowerLevel().equals("1")) {
			str_where = " And A.InputDate >= '"
					+ CommonConvertUtils.dateToStringWithDelimiter(qry
							.getStartDate())
					+ " 00:00:00' And A.InputDate <= '"
					+ CommonConvertUtils.dateToStringWithDelimiter(qry
							.getEndDate())
					+ " 23:59:59' And A.InputPersonID in (Select loginname From sam_taxempcode Where org_id in "
					+ " (Select ID From Organization Where DistrictNumber Like "
					+ " (Select DistrictNumber + '%' From Organization Where ID = "
					+ qry.getOrgId() + "))) ";
		}
		query.setParameter(0, user.getUsername());
		query.setParameter(1, qry.getStatisticType());
		query.setParameter(2, str_where);
		query.setParameter(3, qry.getStatisticResult());
		query.setParameter(4, qry.getIsQryWipeOut());
		List list = query.list();
		for(int i =0;i < list.size();i++){
			SummaryStatistics01 vo = (SummaryStatistics01)list.get(i);
			
		}
		String scb = constructColumnXML(qry.getStatisticType(), qry
				.getStatisticResult(), list, subId);
		return scb;
	}

	public String constructColumnXML(String statisticType,
			String statisticResult, List<SummaryStatistics01> dataList,
			String subId) {

		String org = statisticType.substring(0, 1);
		String inputPerson = statisticType.substring(1, 2);
		String date = statisticType.substring(2, 3);

		String healthfile = statisticResult.substring(0, 1);
		String children = statisticResult.substring(1, 2);
		String maternal = statisticResult.substring(2, 3);
		String chronicDisease = statisticResult.substring(3, 4);
		String vacciInfor = statisticResult.substring(4, 5);

		String datasetData = "";

		String stackedXml = "";

		List<SummaryStatistics01> filterList = new ArrayList();
		for (int i = 0; i < dataList.size() - 1; i++) {
			SummaryStatistics01 vo = dataList.get(i);
			if (inputPerson.equals("1")) {
				if (vo.getInputPersonId().equals(subId)) {
					filterList.add(vo);
				}
			}
			if (org.equals("1")) {
				if (vo.getOrgId().toString().equals(subId)) {
					filterList.add(vo);
				}
			}
			if (date.equals("1")) {
				if (vo.getGroupDate().equals(subId)) {
					filterList.add(vo);
				}
			}
//			System.out.println("-----" + vo.getGroupDate() + "------" + subId + "------");
			if (date.equals("2")) {
				if (vo.getGroupDate().equals(subId)) {
					filterList.add(vo);
				}
			}
			if (date.equals("3")) {
				if (vo.getGroupDate().equals(subId)) {
					filterList.add(vo);
				}
			}
		}

		for (int i = 0; i < filterList.size(); i++) {
			SummaryStatistics01 vo = filterList.get(i);

			if (inputPerson.equals("1")) {
				stackedXml = "<chart caption='按操作人员统计' numberPrefix='' useRoundEdges='1' bgColor='FFFFFF,FFFFFF' showBorder='0'>";
			}

			if (org.equals("1")) {
				stackedXml = "<chart caption='按医疗机构统计' numberPrefix='' useRoundEdges='1' bgColor='FFFFFF,FFFFFF' showBorder='0'>";
			}

			if (date.equals("1")) {
				stackedXml = "<chart caption='按年统计' numberPrefix='' useRoundEdges='1' bgColor='FFFFFF,FFFFFF' showBorder='0'>";
			}

			if (date.equals("2")) {
				stackedXml = "<chart caption='按月统计' numberPrefix='' useRoundEdges='1' bgColor='FFFFFF,FFFFFF' showBorder='0'>";
			}

			if (date.equals("3")) {
				stackedXml = "<chart caption='按日统计' numberPrefix='' useRoundEdges='1' bgColor='FFFFFF,FFFFFF' showBorder='0'>";
			}

			if (healthfile.equals("1")) {
				datasetData += "<set label='农业人口档案数' value='"
						+ vo.getVhealthCount() + "' />";
				datasetData += "<set label='城镇人口档案数' value='"
						+ vo.getChealthCount() + "' />";
			}
			if (children.equals("1")) {
				datasetData += "<set label='新生儿家庭访视' value='"
						+ vo.getBabyVisitCount() + "' />";
				datasetData += "<set label='1岁以内儿童体检' value='"
						+ vo.getChildren01count() + "' />";
				datasetData += "<set label='1~2岁儿童体检' value='"
						+ vo.getChildren02count() + "' />";
				datasetData += "<set label='3~6岁儿童体检' value='"
						+ vo.getChildren36count() + "' />";
				datasetData += "<set label='儿童体检总和' value='"
						+ vo.getBabyAllVisitCount() + "' />";
			}
			if (maternal.equals("1")) {
				datasetData += "<set label='第1次产前随访' value='"
						+ vo.getFirstVistBeforeBornCount() + "' />";
				datasetData += "<set label='第2~5次产前随访' value='"
						+ vo.getVisitBeforeBornCount() + "' />";
				datasetData += "<set label='产前随访总和' value='"
						+ vo.getPrenatalVisitCount() + "' />";
				datasetData += "<set label='产后访视' value='"
						+ vo.getVisitAfterBornCount() + "' />";
				datasetData += "<set label='产后42天体检' value='"
						+ vo.getVisitAfterBorn42count() + "' />";
			}
			if (chronicDisease.equals("1")) {
				datasetData += "<set label='高血压随访' value='"
						+ vo.getHypertensionVisitCount() + "' />";
				datasetData += "<set label='糖尿病随访' value='"
						+ vo.getDiabetesVisitCount() + "' />";
				datasetData += "<set label='重性精神病随访' value='"
						+ vo.getFuriousVisitCount() + "' />";
			}
			if (vacciInfor.equals("1")) {
				datasetData += "<set label='疫苗接种数' value='"
						+ vo.getVacciInfoCount() + "' />";
			}

		}

		// Assemble the entire XML now
		stackedXml += datasetData + "</chart>";
//		System.out.println(stackedXml);
		return stackedXml;

	}

}