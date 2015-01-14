import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Test {

	public static void main(String[] args) {
//		Gson gs = new Gson();
//		HealthFile a = new HealthFile();
//		PersonalInfo info = new PersonalInfo();
//		HealthFileMaternalBO data = new HealthFileMaternalBO();
//		String infostr = "{\"id\":\"40288168467701fe0146771ef46d000c    \",\"fileNo\":\"530102001001000136\",\"fileNoSub\":\"1000136\",\"sex\":\"女\",\"birthday\":\"Nov 4, 2011 12:00:00 AM\",\"idnumber\":\"429004198311041730\",\"workUnit\":\"无\",\"tel\":\"\",\"linkman\":\"\",\"linkmanTel\":\"\",\"folk\":\"汉族\",\"folkOther\":\"\",\"bloodTypeRh\":\"不详\",\"occupation\":\"农、林、牧、渔、水利业生产人员\",\"paymentModeOther\":\"\",\"allergiesOther\":\"\",\"fhistoryOther\":\"\",\"mhistoryOther\":\"\",\"bhistoryOther\":\"\",\"fmHistoryOther\":\"\",\"geneticHistory\":\"无\",\"geneticHistoryOther\":\"\",\"disabilityStatusOther\":\"\",\"inputDate\":\"Jun 8, 2014 12:17:35 AM\",\"farmStatus\":\"是\",\"kitchen\":\"无\",\"bunkers\":\"柴火\",\"drinkingWater\":\"自来水\",\"toilet\":\"简易棚厕\",\"poultry\":\"单设\",\"townStatus\":\"否\",\"bornStatus\":\"否\"}";
//		String filestr = "{\"fileNo\":\"530102001001000136\",\"name\":\"22222\",\"address\":\"11\",\"residenceAddress\":\"1\",\"tel\":\"333\",\"township\":\"华山街道办事处\",\"village\":\"洪化桥社区居委会\",\"buildUnit\":\"昆明市卫生局\",\"buildPerson\":\"李雯霞\",\"doctor\":\"张敏\",\"buildDate\":\"Jun 9, 2014 12:00:00 AM\",\"districtNumber\":\"530102001001        \",\"namePng\":\"nmpng\",\"inputPersonId\":\"222\",\"inputDate\":\"Jun 8, 2014 12:17:35 AM\",\"lastModifyDate\":\"Jun 9, 2014 10:07:18 AM\",\"modifyPerson\":\"1111\",\"barCode\":\"aaa\",\"isOverCount\":1,\"isModifyOrNew\":1,\"paperFileNo\":\"1\",\"status\":0,\"nation\":\"中国\"}";
//		String datastr = "{\"fileNo\":\"530102001001000136\",\"name\":\"22222\",\"birthday\":\"Nov 4, 2011 12:00:00 AM\",\"tel\":\"333\",\"firstAidTel\":\"\",\"addressProvence\":\"云南省\",\"addressCity\":\"昆明市\",\"addressCounty\":\"\",\"addressTownship\":\"华山街道办事处\",\"addressVillage\":\"洪化桥社区居委会\",\"addressGroup\":\"\",\"residenceProvence\":\"云南省\",\"residenceCity\":\"昆明市\",\"residenceCounty\":\"\",\"residenceTownship\":\"华山街道办事处\",\"residenceVillage\":\"洪化桥社区居委会\",\"residenceGroup\":\"\",\"highRiskCode\":\"\",\"buildUnit\":\"昆明市卫生局\",\"buildDate\":\"Jun 9, 2014 12:00:00 AM\",\"nationality\":\"中国\",\"nationalityOther\":\"\",\"idnumber\":\"429004198311041730\",\"workUnit\":\"无\",\"folk\":\"汉族\",\"folkOther\":\"\",\"occupation\":\"农、林、牧、渔、水利业生产人员\",\"recuperateProvence\":\"\",\"recuperateCity\":\"\",\"recuperateCounty\":\"\",\"recuperateTownship\":\"\",\"recuperateVillage\":\"\",\"recuperateGroup\":\"\",\"husbandName\":\"\",\"husbandTel\":\"\",\"husbandOccupation\":\"\",\"husbandOccupationOther\":\"\",\"husbandWorkUnit\":\"\",\"gravidity\":1,\"isClosed\":\"0\",\"barCode\":\"\"}";
//		info = gs.fromJson(infostr, PersonalInfo.class);
//		a = gs.fromJson(filestr, HealthFile.class);
//		a.setPersonalInfo(info);
//		data = gs.fromJson(datastr, HealthFileMaternalBO.class);
//		System.out.println("============"+gs.toJson(a));
//		System.out.println("=======data====="+gs.toJson(data));
//		BeanUtils.copyProperties(data, a); 
//		System.out.println("============"+gs.toJson(a));
		String a = "abc#123dddd";
		System.out.println("============"+a.replaceAll("#", ""));
	}

	public static String camelcasify(String in) {
		StringBuilder sb = new StringBuilder();
		boolean capitalizeNext = false;
		for (char c : in.toCharArray()) {
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
		return sb.toString();
	}

	public static int s_enc(char charcode, int flag) {
		int split = 0x10b - charcode - Integer.parseInt("" + charcode, 16);
		if (flag >= split) {
			flag += 1;
		}
		int ret = (((charcode + flag) ^ 1) ^ '1');
		ret = ret % 256;
		return ret;
	}

	public static int s_enc2(char charcode, int flag) {
		int m = ((flag / 16 * 2) % 8) * 16 + 1 + (charcode ^ flag ^ 1);
		if (m >= 0xff) {
			m = m + 1;
		}
		m = m ^ '1';
		m = m % 256;
		return m;
	}

	public static int s_enc1(char charcode, int flag) {
		int m = ((flag / 16 * 2) % 8) * 16 + 1 + (charcode ^ flag ^ 1);
		if (m >= 0xff) {
			m = m + 1;
		}
		m = m ^ '1';
		m = m % 256;
		return m;
	}

	public static int s_denc1(int flag, int result) {
		int a = result ^ '1';
		int b = ((flag / 16 * 2) % 8) * 16 + 1;
		if (a < b) {
			a = 0xff + a;
		}
		a = ((a - b) ^ 1 ^ flag) - 0x30;
		return a;
	}

	// public static int s_denc(int flag){
	// int split = 0x10b - charcode - Integer.parseInt(""+charcode, 16);
	// if(flag >=split){
	// flag +=1;
	// }
	// int ret = (((charcode+flag)^1) ^'1');
	// ret= ret %256;
	// return ret;
	// }

	public static int testI_2(char charcode, int flag) {
		// charcode = '|' 124 7c
		int m = 0;
		m = (flag + 12 + (flag) / 0x80) / 0x10 % 4;
		if (flag > 0x43 && flag < 0x74) {
			flag++;
		} else if (flag > 0x81) {
			flag++;
		}
		int ret = ((charcode + 1 + (flag)) ^ 0x31) + m * 0x20;
		ret = ret % 256;
		return ret;
	}

	public static int testA_2(char charcode, int flag) {
		int m = ((flag + 18)) / 0x10 % 4;
		// if(flag >0x43 && flag <0x74){
		// flag++;
		// }else if (flag > 0x81){
		// flag++;
		// }
		int ret = (((charcode) + (flag ^ 1)) ^ '1') + m * 0x20;
		ret = ret % 256;
		return ret;
	}

	// public static int testB_2(char charcode,int flag,int result){
	// // int split = 0xff;
	// // int split1 = 0x80;
	// //// flag = flag +0xa;
	// // flag = split ^ flag;
	// //// if(flag >= split1){
	// //// flag = flag - split1;
	// //// }else{
	// //// flag = split -flag;
	// //// }
	// // int test =( (flag) ^'1')-0x30;
	// // int ret =test;
	// //// if(test > split){
	// //// ret = test - split;
	// //// }else{
	// //// ret = split -test;
	// // }
	// // return s_enc(charcode,flag);
	// // return ((charcode^(flag)^1) )^'1';
	// // return charcode^flag^1;
	// // return result^'1';
	// int a = (result^'1' )- (charcode^flag^1);
	// // if(a < 0){
	// a = a+ 0xff;
	// // }
	// a= a- (((flag /16 *2 )%8) *16 + 1);
	// // a = ((((flag /16 *2 )%8) *16 + 1) +(charcode^flag^1))^'1' ;
	//
	// // int split = 0x10b - charcode - Integer.parseInt(""+charcode, 16);
	// // if(flag >=split){
	// // flag +=1;
	// // }
	// int m = ((flag /16 *2 )%8) *16 +1 +(charcode^flag^1);
	// if(m>=0xff){
	// m = m+1;
	// }
	// m = m ^'1';
	// m = m %256;
	// return m;
	// }

	public static int testB_2(char charcode, int flag, int result) {
		return s_denc1(flag, result);
	}

	public static char denc1(int flag, int value) {
		int mod = 0xb;
		int right = value % 16;
		int left = value / 16;
		int all = 0;
		all = (left ^ mod) * 16 + right;
		int charcode = all - flag + 128;
		return (char) charcode;
	}

	public static int enc1(char charcode, int flag) {
		int mod = 0xb;
		if (flag >= (charcode ^ 0xff)) {
			flag = flag + 1;
		}
		int all = flag + charcode % 64 - 64;
		if (all < 0) {
			all = all + 256;
		}
		int right = all % 16;
		int left = all / 16;

		int ret = (left ^ mod) * 16 + right;
		return ret;
	}

	public static void testB_1() {
		int[][] strs = getdata(DATA_B);
		for (int row = 0; row < strs.length; row++) {
			boolean flag = Test.enc1('B', strs[row][0]) == strs[row][1];
			if (flag) {
				System.out.println(Integer.toHexString(strs[row][0]) + " "
						+ Test.enc1('B', strs[row][0]) + " "
						+ Integer.toHexString(strs[row][1]) + " "
						+ Test.denc1('B', strs[row][2]) + " "
						+ Integer.toHexString(strs[row][2]));
			} else {
				System.out.println(Integer.toHexString(strs[row][0]) + " "
						+ Test.enc1('B', strs[row][0]) + " "
						+ Integer.toHexString(strs[row][1])
						+ "################");
			}
		}
	}

	public static void testA_1() {
		int[][] strs = getdata(DATA_A);
		for (int row = 0; row < strs.length; row++) {
			boolean flag = Test.enc1('A', strs[row][0]) == strs[row][1];
			if (flag) {
				System.out.println(Integer.toHexString(strs[row][0]) + " "
						+ Test.enc1('A', strs[row][0]) + " "
						+ Integer.toHexString(strs[row][1]));
			} else {
				System.out.println(Integer.toHexString(strs[row][0]) + " "
						+ Test.enc1('A', strs[row][0]) + " "
						+ Integer.toHexString(strs[row][1])
						+ "################");
			}
		}
	}

	public static void testI_1() {
		int[][] strs = getdata(DATA_I);
		for (int row = 0; row < strs.length; row++) {
			boolean flag = Test.enc1('|', strs[row][0]) == strs[row][1];
			if (flag) {
				System.out.println(Integer.toHexString(strs[row][0]) + " "
						+ Test.enc1('|', strs[row][0]) + " "
						+ Integer.toHexString(strs[row][1]));
			} else {
				System.out.println(Integer.toHexString(strs[row][0]) + " "
						+ Test.enc1('|', strs[row][0]) + " "
						+ Integer.toHexString(strs[row][1])
						+ "################");
			}
		}
	}

	public static void testI_2() {
		int[][] strs_1 = getdata(DATA_I);
		for (int row = 0; row < strs_1.length; row++) {
			boolean flag = testI_2('|', strs_1[row][0]) == strs_1[row][2];
			if (flag) {
				System.out.println(Integer.toHexString(strs_1[row][0]) + " "
						+ Integer.toHexString(testI_2('|', strs_1[row][0]))
						+ " " + Integer.toHexString(strs_1[row][2]));
			} else {
				System.out.println(Integer.toHexString(strs_1[row][0]) + " "
						+ Integer.toHexString(testI_2('|', strs_1[row][0]))
						+ " " + Integer.toHexString(strs_1[row][2])
						+ "################");
			}
		}
	}

	public static void testA_2() {
		int[][] strs_2 = getdata(DATA_A);
		String[] strs = DATA_A;
		for (int row = 0; row < strs_2.length; row++) {
			boolean flag = Test.testA_2('A', strs_2[row][0]) == strs_2[row][2];
			if (flag) {
				System.out
						.println(strs[row]
								+ " "
								+ Integer.toHexString(strs_2[row][0])
								+ " "
								+ Integer.toHexString(Test.testA_2('A',
										strs_2[row][0])) + " "
								+ Integer.toHexString(strs_2[row][2]));
			} else {
				System.out
						.println(strs[row]
								+ " "
								+ Integer.toHexString(strs_2[row][0])
								+ " "
								+ Integer.toHexString(Test.testA_2('A',
										strs_2[row][0])) + " "
								+ Integer.toHexString(strs_2[row][2])
								+ "################");
			}
		}
	}

	public static void testB_2() {
		int[][] strs_3 = getdata(DATA_B);
		String[] strs = DATA_B;
		int last = 0;
		for (int row = 0; row < strs_3.length; row++) {
			// boolean flag =
			// testB_2('1',strs_3[row][1],strs_3[row][2])==strs_3[row][2];
			// int a =testB_2('1',strs_3[row][1],strs_3[row][2]);
			// s_enc1
			boolean flag = s_denc1(strs_3[row][1], strs_3[row][2]) == strs_3[row][2];
			int a = s_denc1(strs_3[row][1], strs_3[row][2]);
			int b = a - last;
			last = 1;
			// if(flag){
			// System.out.println(strs[row]+" "+Integer.toHexString(strs_3[row][1])+" "+Integer.toHexString(a)
			// +" " +Integer.toHexString( b) +" " +Integer.toHexString( a-b));
			// }else{
			// System.out.println(strs[row]+" "+Integer.toHexString(strs_3[row][1])+" "+Integer.toHexString(a)
			// +" " +Integer.toHexString(b) +" " +Integer.toHexString(
			// a-b)+" "+"################");
			// }

			if (flag) {
				System.out.println(strs[row] + " "
						+ Integer.toHexString(strs_3[row][1]) + " "
						+ Integer.toHexString(a));
			} else {
				System.out.println(strs[row] + " "
						+ Integer.toHexString(strs_3[row][1]) + " "
						+ Integer.toHexString(a) + " " + "################");
			}
		}
	}

	public static int[][] getdata(String[] data) {
		int[][] ret = new int[data.length][3];
		for (int i = 0; i < data.length; i++) {
			ret[i][0] = Integer.parseInt(data[i].substring(0, 2), 16);
			ret[i][1] = Integer.parseInt(data[i].substring(2, 4), 16);
			ret[i][2] = Integer.parseInt(data[i].substring(4), 16);
		}
		return ret;
	}

	public static String[] DATA_A = { "007193", "017292", "027395", "037494",
			"047597", "057696", "067799", "077898", "08799B", "097A9A",
			"0A7B9D", "0B7C9C", "0C7D9F", "0D7E9E", "0E7F81", "0F60A0",
			"1061A3", "1162A2", "1263A5", "1364A4", "1465A7", "1566A6",
			"1667A9", "1768A8", "1869AB", "196AAA", "1A6BAD", "1B6CAC",
			"1C6DAF", "1D6EAE", "1E6F91", "1F50B0", "2051B3", "2152B2",
			"2253B5", "2354B4", "2455B7", "2556B6", "2657B9", "2758B8",
			"2859BB", "295ABA", "2A5BBD", "2B5CBC", "2C5DBF", "2D5EBE",
			"2E5FA1", "2F4040", "304143", "314242", "324345", "334444",
			"344547", "354646", "364749", "374848", "38494B", "394A4A",
			"3A4B4D", "3B4C4C", "3C4D4F", "3D4E4E", "3E4FB1", "3FB0D0",
			"40B1D3", "41B2D2", "42B3D5", "43B4D4", "44B5D7", "45B6D6",
			"46B7D9", "47B8D8", "48B9DB", "49BADA", "4ABBDD", "4BBCDC",
			"4CBDDF", "4DBEDE", "4EBFC1", "4FA0E0", "50A1E3", "51A2E2",
			"52A3E5", "53A4E4", "54A5E7", "55A6E6", "56A7E9", "57A8E8",
			"58A9EB", "59AAEA", "5AABED", "5BACEC", "5CADEF", "5DAEEE",
			"5EAFD1", "5F90F0", "6091F3", "6192F2", "6293F5", "6394F4",
			"6495F7", "6596F6", "6697F9", "6798F8", "6899FB", "699AFA",
			"6A9BFD", "6B9CFC", "6C9DFF", "6D9EFE", "6E9FE1", "6F8080",
			"708183", "718282", "728385", "738484", "748587", "758686",
			"768789", "778888", "78898B", "798A8A", "7A8B8D", "7B8C8C",
			"7C8D8F", "7D8E8E", "7E8FF1", "7FF013", "80F112", "81F215",
			"82F314", "83F417", "84F516", "85F619", "86F718", "87F81B",
			"88F91A", "89FA1D", "8AFB1C", "8BFC1F", "8CFD1E", "8DFE01",
			"8EFF00", "8FE023", "90E122", "91E225", "92E324", "93E427",
			"94E526", "95E629", "96E728", "97E82B", "98E92A", "99EA2D",
			"9AEB2C", "9BEC2F", "9CED2E", "9DEE11", "9EEF10", "9FD033",
			"A0D132", "A1D235", "A2D334", "A3D437", "A4D536", "A5D639",
			"A6D738", "A7D83B", "A8D93A", "A9DA3D", "AADB3C", "ABDC3F",
			"ACDD3E", "ADDE21", "AEDF20", "AFC0C0", "B0C1C3", "B1C2C2",
			"B2C3C5", "B3C4C4", "B4C5C7", "B5C6C6", "B6C7C9", "B7C8C8",
			"B8C9CB", "B9CACA", "BACBCD", "BBCCCC", "BCCDCF", "BDCE31",
			"BE3050", "BF3153", "C03252", "C13355", "C23454", "C33557",
			"C43656", "C53759", "C63858", "C7395B", "C83A5A", "C93B5D",
			"CA3C5C", "CB3D5F", "CC3E5E", "CD3F41", "CE2060", "CF2163",
			"D02262", "D12365", "D22464", "D32567", "D42666", "D52769",
			"D62868", "D7296B", "D82A6A", "D92B6D", "DA2C6C", "DB2D6F",
			"DC2E6E", "DD2F51", "DE1070", "DF1173", "E01272", "E11375",
			"E21474", "E31577", "E41676", "E51779", "E61878", "E7197B",
			"E81A7A", "E91B7D", "EA1C7C", "EB1D7F", "EC1E7E", "ED1F61",
			"EE0000", "EF0103", "F00202", "F10305", "F20404", "F30507",
			"F40606", "F50709", "F60808", "F7090B", "F80A0A", "F90B0D",
			"FA0C0C", "FB0D0F", "FC0E0E", "FD0F71", "FE7090", "FF7193" };
	public static String[] DATA_B = { "007292", "017395", "027494", "037597",
			"047696", "057799", "067898", "07799B", "087A9A", "097B9D",
			"0A7C9C", "0B7D9F", "0C7E9E", "0D7F81", "0E60A0", "0F61A3",
			"1062A2", "1163A5", "1264A4", "1365A7", "1466A6", "1567A9",
			"1668A8", "1769AB", "186AAA", "196BAD", "1A6CAC", "1B6DAF",
			"1C6EAE", "1D6F91", "1E50B0", "1F51B3", "2052B2", "2153B5",
			"2254B4", "2355B7", "2456B6", "2557B9", "2658B8", "2759BB",
			"285ABA", "295BBD", "2A5CBC", "2B5DBF", "2C5EBE", "2D5FA1",
			"2E4040", "2F4143", "304242", "314345", "324444", "334547",
			"344646", "354749", "364848", "37494B", "384A4A", "394B4D",
			"3A4C4C", "3B4D4F", "3C4E4E", "3D4FB1", "3EB0D0", "3FB1D3",
			"40B2D2", "41B3D5", "42B4D4", "43B5D7", "44B6D6", "45B7D9",
			"46B8D8", "47B9DB", "48BADA", "49BBDD", "4ABCDC", "4BBDDF",
			"4CBEDE", "4DBFC1", "4EA0E0", "4FA1E3", "50A2E2", "51A3E5",
			"52A4E4", "53A5E7", "54A6E6", "55A7E9", "56A8E8", "57A9EB",
			"58AAEA", "59ABED", "5AACEC", "5BADEF", "5CAEEE", "5DAFD1",
			"5E90F0", "5F91F3", "6092F2", "6193F5", "6294F4", "6395F7",
			"6496F6", "6597F9", "6698F8", "6799FB", "689AFA", "699BFD",
			"6A9CFC", "6B9DFF", "6C9EFE", "6D9FE1", "6E8080", "6F8183",
			"708282", "718385", "728484", "738587", "748686", "758789",
			"768888", "77898B", "788A8A", "798B8D", "7A8C8C", "7B8D8F",
			"7C8E8E", "7D8FF1", "7EF013", "7FF112", "80F215", "81F314",
			"82F417", "83F516", "84F619", "85F718", "86F81B", "87F91A",
			"88FA1D", "89FB1C", "8AFC1F", "8BFD1E", "8CFE01", "8DFF00",
			"8EE023", "8FE122", "90E225", "91E324", "92E427", "93E526",
			"94E629", "95E728", "96E82B", "97E92A", "98EA2D", "99EB2C",
			"9AEC2F", "9BED2E", "9CEE11", "9DEF10", "9ED033", "9FD132",
			"A0D235", "A1D334", "A2D437", "A3D536", "A4D639", "A5D738",
			"A6D83B", "A7D93A", "A8DA3D", "A9DB3C", "AADC3F", "ABDD3E",
			"ACDE21", "ADDF20", "AEC0C0", "AFC1C3", "B0C2C2", "B1C3C5",
			"B2C4C4", "B3C5C7", "B4C6C6", "B5C7C9", "B6C8C8", "B7C9CB",
			"B8CACA", "B9CBCD", "BACCCC", "BBCDCF", "BCCE31", "BD3050",
			"BE3153", "BF3252", "C03355", "C13454", "C23557", "C33656",
			"C43759", "C53858", "C6395B", "C73A5A", "C83B5D", "C93C5C",
			"CA3D5F", "CB3E5E", "CC3F41", "CD2060", "CE2163", "CF2262",
			"D02365", "D12464", "D22567", "D32666", "D42769", "D52868",
			"D6296B", "D72A6A", "D82B6D", "D92C6C", "DA2D6F", "DB2E6E",
			"DC2F51", "DD1070", "DE1173", "DF1272", "E01375", "E11474",
			"E21577", "E31676", "E41779", "E51878", "E6197B", "E71A7A",
			"E81B7D", "E91C7C", "EA1D7F", "EB1E7E", "EC1F61", "ED0000",
			"EE0103", "EF0202", "F00305", "F10404", "F20507", "F30606",
			"F40709", "F50808", "F6090B", "F70A0A", "F80B0D", "F90C0C",
			"FA0D0F", "FB0E0E", "FC0F71", "FD7090", "FE7193", "FF7292" };

	public static String[] DATA_GT = { // >
	"000C0C", "010D0F", "020E0E", "030F71", "047090", "057193", "067292",
			"077395", "087494", "097597", "0B7799", "0C7898", "0D799B",
			"0E7A9A", "0F7B9D", "107C9C", "117D9F", "127E9E", "137F81",
			"1460A0", "1561A3", "1662A2", "1763A5", "1864A4", "1965A7",
			"1A66A6", "1B67A9", "1C68A8", "1D69AB", "1E6AAA", "1F6BAD",
			"206CAC", "216DAF", "226EAE", "236F91", "2450B0", "2551B3",
			"2652B2", "2753B5", "2854B4", "2955B7", "2A56B6", "2B57B9",
			"2C58B8", "2D59BB", "2E5ABA", "2F5BBD", "305CBC", "315DBF",
			"325EBE", "335FA1", "344040", "354143", "364242", "374345",
			"384444", "394547", "3A4646", "3B4749", "3C4848", "3D494B",
			"3E4A4A", "3F4B4D", "404C4C", "414D4F", "424E4E", "434FB1",
			"44B0D0", "45B1D3", "46B2D2", "47B3D5", "48B4D4", "49B5D7",
			"4AB6D6", "4BB7D9", "4CB8D8", "4DB9DB", "4EBADA", "4FBBDD",
			"50BCDC", "51BDDF", "52BEDE", "53BFC1", "54A0E0", "55A1E3",
			"56A2E2", "57A3E5", "58A4E4", "59A5E7", "5AA6E6", "5BA7E9",
			"5CA8E8", "5DA9EB", "5EAAEA", "5FABED", "60ACEC", "61ADEF",
			"62AEEE", "63AFD1", "6490F0", "6591F3", "6692F2", "6793F5",
			"6894F4", "6995F7", "6A96F6", "6B97F9", "6C98F8", "6D99FB",
			"6E9AFA", "6F9BFD", "709CFC", "719DFF", "729EFE", "739FE1",
			"748080", "758183", "768282", "778385", "788484", "798587",
			"7A8686", "7B8789", "7C8888", "7D898B", "7E8A8A", "7F8B8D",
			"808C8C", "818D8F", "828E8E", "838FF1", "84F013", "85F112",
			"86F215", "87F314", "88F417", "89F516", "8AF619", "8BF718",
			"8CF81B", "8DF91A", "8EFA1D", "8FFB1C", "90FC1F", "91FD1E",
			"92FE01", "93FF00", "94E023", "95E122", "96E225", "97E324",
			"98E427", "99E526", "9AE629", "9BE728", "9CE82B", "9DE92A",
			"9EEA2D", "9FEB2C", "A0EC2F", "A1ED2E", "A2EE11", "A3EF10",
			"A4D033", "A5D132", "A6D235", "A7D334", "A8D437", "A9D536",
			"AAD639", "ABD738", "ACD83B", "ADD93A", "AEDA3D", "AFDB3C",
			"B0DC3F", "B1DD3E", "B2DE21", "B3DF20", "B4C0C0", "B5C1C3",
			"B6C2C2", "B7C3C5", "B8C4C4", "B9C5C7", "BAC6C6", "BBC7C9",
			"BCC8C8", "BDC9CB", "BECACA", "BFCBCD", "C0CCCC", "C1CDCF",
			"C2CE31", "C33050", "C43153", "C53252", "C63355", "C73454",
			"C83557", "C93656", "CA3759", "CB3858", "CC395B", "CD3A5A",
			"CE3B5D", "CF3C5C", "D03D5F", "D13E5E", "D23F41", "D32060",
			"D42163", "D52262", "D62365", "D72464", "D82567", "D92666",
			"DA2769", "DB2868", "DC296B", "DD2A6A", "DE2B6D", "DF2C6C",
			"E02D6F", "E12E6E", "E22F51", "E31070", "E41173", "E51272",
			"E61375", "E71474", "E81577", "E91676", "EA1779", "EB1878",
			"EC197B", "ED1A7A", "EE1B7D", "EF1C7C", "F01D7F", "F11E7E",
			"F21F61", "F30000", "F40103", "F50202", "F60305", "F70404",
			"F80507", "F90606", "FA0709", "FB0808", "FC090B", "FD0A0A",
			"FE0B0D", "FF0C0C" };
	public static String[] DATA_I = { "004C4C", "014D4F", "024E4E", "034FB1",
			"04B0D0", "05B1D3", "06B2D2", "07B3D5", "08B4D4", "09B5D7",
			"0AB6D6", "0BB7D9", "0CB8D8", "0DB9DB", "0EBADA", "0FBBDD",
			"10BCDC", "11BDDF", "12BEDE", "13BFC1", "14A0E0", "15A1E3",
			"16A2E2", "17A3E5", "18A4E4", "19A5E7", "1AA6E6", "1BA7E9",
			"1CA8E8", "1DA9EB", "1EAAEA", "1FABED", "20ACEC", "21ADEF",
			"22AEEE", "23AFD1", "2490F0", "2591F3", "2692F2", "2793F5",
			"2894F4", "2995F7", "2A96F6", "2B97F9", "2C98F8", "2D99FB",
			"2E9AFA", "2F9BFD", "309CFC", "319DFF", "329EFE", "339FE1",
			"348080", "358183", "368282", "378385", "388484", "398587",
			"3A8686", "3B8789", "3C8888", "3D898B", "3E8A8A", "3F8B8D",
			"408C8C", "418D8F", "428E8E", "438FF1", "44F013", "45F112",
			"46F215", "47F314", "48F417", "49F516", "4AF619", "4BF718",
			"4CF81B", "4DF91A", "4EFA1D", "4FFB1C", "50FC1F", "51FD1E",
			"52FE01", "53FF00", "54E023", "55E122", "56E225", "57E324",
			"58E427", "59E526", "5AE629", "5BE728", "5CE82B", "5DE92A",
			"5EEA2D", "5FEB2C", "60EC2F", "61ED2E", "62EE11", "63EF10",
			"64D033", "65D132", "66D235", "67D334", "68D437", "69D536",
			"6AD639", "6BD738", "6CD83B", "6DD93A", "6EDA3D", "6FDB3C",
			"70DC3F", "71DD3E", "72DE21", "73DF20", "74C0C0", "75C1C3",
			"76C2C2", "77C3C5", "78C4C4", "79C5C7", "7AC6C6", "7BC7C9",
			"7CC8C8", "7DC9CB", "7ECACA", "7FCBCD", "80CCCC", "81CDCF",
			"82CE31", "833050", "843153", "853252", "863355", "873454",
			"883557", "893656", "8A3759", "8B3858", "8C395B", "8D3A5A",
			"8E3B5D", "8F3C5C", "903D5F", "913E5E", "923F41", "932060",
			"942163", "952262", "962365", "972464", "982567", "992666",
			"9A2769", "9B2868", "9C296B", "9D2A6A", "9E2B6D", "9F2C6C",
			"A02D6F", "A12E6E", "A22F51", "A31070", "A41173", "A51272",
			"A61375", "A71474", "A81577", "A91676", "AA1779", "AB1878",
			"AC197B", "AD1A7A", "AE1B7D", "AF1C7C", "B01D7F", "B11E7E",
			"B21F61", "B30000", "B40103", "B50202", "B60305", "B70404",
			"B80507", "B90606", "BA0709", "BB0808", "BC090B", "BD0A0A",
			"BE0B0D", "BF0C0C", "C00D0F", "C10E0E", "C20F71", "C37090",
			"C47193", "C57292", "C67395", "C77494", "C87597", "C97696",
			"CA7799", "CB7898", "CC799B", "CD7A9A", "CE7B9D", "CF7C9C",
			"D07D9F", "D17E9E", "D27F81", "D360A0", "D461A3", "D562A2",
			"D663A5", "D764A4", "D865A7", "D966A6", "DA67A9", "DB68A8",
			"DC69AB", "DD6AAA", "DE6BAD", "DF6CAC", "E06DAF", "E16EAE",
			"E26F91", "E350B0", "E451B3", "E552B2", "E653B5", "E754B4",
			"E855B7", "E956B6", "EA57B9", "EB58B8", "EC59BB", "ED5ABA",
			"EE5BBD", "EF5CBC", "F05DBF", "F15EBE", "F25FA1", "F34040",
			"F44143", "F54242", "F64345", "F74444", "F84547", "F94646",
			"FA4749", "FB4848", "FC494B", "FD4A4A", "FE4B4D", "FF4C4C" };

	public static String[] DATA_C = { "007395", "017494", "027597", "037696",
			"047799", "057898", "06799B", "077A9A", "087B9D", "097C9C",
			"0A7D9F", "0B7E9E", "0C7F81", "0D60A0", "0E61A3", "0F62A2",
			"1063A5", "1164A4", "1265A7", "1366A6", "1467A9", "1568A8",
			"1669AB", "176AAA", "186BAD", "196CAC", "1A6DAF", "1B6EAE",
			"1C6F91", "1D50B0", "1E51B3", "1F52B2", "2053B5", "2154B4",
			"2255B7", "2356B6", "2457B9", "2558B8", "2659BB", "275ABA",
			"285BBD", "295CBC", "2A5DBF", "2B5EBE", "2C5FA1", "2D4040",
			"2E4143", "2F4242", "304345", "314444", "324547", "334646",
			"344749", "354848", "36494B", "374A4A", "384B4D", "394C4C",
			"3A4D4F", "3B4E4E", "3C4FB1", "3DB0D0", "3EB1D3", "3FB2D2",
			"40B3D5", "41B4D4", "42B5D7", "43B6D6", "44B7D9", "45B8D8",
			"46B9DB", "47BADA", "48BBDD", "49BCDC", "4ABDDF", "4BBEDE",
			"4CBFC1", "4DA0E0", "4EA1E3", "4FA2E2", "50A3E5", "51A4E4",
			"52A5E7", "53A6E6", "54A7E9", "55A8E8", "56A9EB", "57AAEA",
			"58ABED", "59ACEC", "5AADEF", "5BAEEE", "5CAFD1", "5D90F0",
			"5E91F3", "5F92F2", "6093F5", "6194F4", "6295F7", "6396F6",
			"6497F9", "6598F8", "6699FB", "679AFA", "689BFD", "699CFC",
			"6A9DFF", "6B9EFE", "6C9FE1", "6D8080", "6E8183", "6F8282",
			"708385", "718484", "728587", "738686", "748789", "758888",
			"76898B", "778A8A", "788B8D", "798C8C", "7A8D8F", "7B8E8E",
			"7C8FF1", "7DF013", "7EF112", "7FF215", "80F314", "81F417",
			"82F516", "83F619", "84F718", "85F81B", "86F91A", "87FA1D",
			"88FB1C", "89FC1F", "8AFD1E", "8BFE01", "8CFF00", "8DE023",
			"8EE122", "8FE225", "90E324", "91E427", "92E526", "93E629",
			"94E728", "95E82B", "96E92A", "97EA2D", "98EB2C", "99EC2F",
			"9AED2E", "9BEE11", "9CEF10", "9DD033", "9ED132", "9FD235",
			"A0D334", "A1D437", "A2D536", "A3D639", "A4D738", "A5D83B",
			"A6D93A", "A7DA3D", "A8DB3C", "A9DC3F", "AADD3E", "ABDE21",
			"ACDF20", "ADC0C0", "AEC1C3", "AFC2C2", "B0C3C5", "B1C4C4",
			"B2C5C7", "B3C6C6", "B4C7C9", "B5C8C8", "B6C9CB", "B7CACA",
			"B8CBCD", "B9CCCC", "BACDCF", "BBCE31", "BC3050", "BD3153",
			"BE3252", "BF3355", "C03454", "C13557", "C23656", "C33759",
			"C43858", "C5395B", "C63A5A", "C73B5D", "C83C5C", "C93D5F",
			"CA3E5E", "CB3F41", "CC2060", "CD2163", "CE2262", "CF2365",
			"D02464", "D12567", "D22666", "D32769", "D42868", "D5296B",
			"D62A6A", "D72B6D", "D82C6C", "D92D6F", "DA2E6E", "DB2F51",
			"DC1070", "DD1173", "DE1272", "DF1375", "E01474", "E11577",
			"E21676", "E31779", "E41878", "E5197B", "E61A7A", "E71B7D",
			"E81C7C", "E91D7F", "EA1E7E", "EB1F61", "EC0000", "ED0103",
			"EE0202", "EF0305", "F00404", "F10507", "F20606", "F30709",
			"F40808", "F5090B", "F60A0A", "F70B0D", "F80C0C", "F90D0F",
			"FA0E0E", "FB0F71", "FC7090", "FD7193", "FE7292", "FF7395" };
}
