(function(){
	printObj = {
		CreateFullBill : CreateFullBill,
		printPreview : printPreview,
		printProtect : printProtect,
		multiPrintProtect : multiPrintProtect,
		medicalExamPrintPreview : medicalExamPrintPreview,
		womanExamPrintProtect : womanExamPrintProtect,
		childExamPrintProtect : childExamPrintProtect,
		printBirthMonthReport : printBirthMonthReport,
		printHTML:printHTML,
		printHTML1:printHTML1,
		printUrl : printUrl,
		VacciImmunePrintProtect : VacciImmunePrintProtect
	}
	var LODOP;//预定义
	//检查控件是否安装
	function CheckActiveX(){
		LODOP=getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));
		if((LODOP!=null)&&(typeof(LODOP.VERSION)!="undefined")){
			return true;
		}else{
			alert('没有安装Lodop打印控件，请先安装。安装完成后请刷新页面后再打印！');
			BirthCertificateMsgService.getURL(3,function(data){
				window.location.href = data + '/install_lodop.exe';
			});
			return false;
		}
	}
	//打印维护
	function printProtect(jsonPrint,type){
		if(CheckActiveX()){
			if(type == 0){//0代表出生证明打印
				CreateFullBill(jsonPrint);
			}
			LODOP.PRINT_SETUP();	
		}
	}
	
	//打印预览
	function printPreview(jsonPrint,type){
		if(CheckActiveX()){
			if(type == 0){//0代表出生证明打印
				CreateFullBill(jsonPrint);
			}else if(type == 1){//1代表预防接种证打印
				PrintVacciImmune(jsonPrint);
			}else if(type === -1){//-1通用打印
				PrintCommon(jsonPrint);
			}else if(type === -2){//-1通用打印
				PrintCommon1(jsonPrint);
			}else if(type === -3){//-1通用打印 封面打印,字体大
                PrintCover(jsonPrint);
            }
			//打印预览
			LODOP.PREVIEW();	
			//打印维护			//LODOP.PRINT_SETUP();
		}
	}
	//多页打印预览
	function multiPrintProtect(html,title,date){
		if(CheckActiveX()){
			MultiPagePrint(html,title,date);
			LODOP.PREVIEW();	
		}
	}
	
	//通用打印
	function PrintCommon(jsonPrint){
		LODOP.PRINT_INITA(jsonPrint.title.intTop,jsonPrint.title.intLeft,jsonPrint.title.intWidth,jsonPrint.title.intHeight,jsonPrint.title.strContent);
		LODOP.SET_PRINT_STYLE("FontColor","#0000FF");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",16);
		for(var i = 0 ; i< jsonPrint.data.length ;i++){
			item = jsonPrint.data[i];
			LODOP.ADD_PRINT_TEXT(item.intTop,item.intLeft,item.intWidth,item.intHeight,item.strContent);
		}	
	}
	
	//通用打印
	function PrintCommon1(jsonPrint){
		LODOP.PRINT_INITA(jsonPrint.title.intTop,jsonPrint.title.intLeft,jsonPrint.title.intWidth,jsonPrint.title.intHeight,jsonPrint.title.strContent);
		LODOP.SET_PRINT_STYLE("FontColor","#0000FF");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",16);
		for(var i = 0 ; i< jsonPrint.data.length ;i++){
			item = jsonPrint.data[i];
			LODOP.ADD_PRINT_TEXT(item[0],item[1],item[2],item[3],item[4]);
		}	
	}
	
	//通用打印
    function PrintCover(jsonPrint){
        LODOP.PRINT_INITA(jsonPrint.title.intTop,jsonPrint.title.intLeft,jsonPrint.title.intWidth,jsonPrint.title.intHeight,jsonPrint.title.strContent);
        LODOP.SET_PRINT_STYLE("FontColor","#0000FF");
        
        for(var i = 0 ; i< jsonPrint.data.length ;i++){
            item = jsonPrint.data[i];
            LODOP.ADD_PRINT_TEXT(item[0],item[1],item[2],item[3],item[4]);
            LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
            LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        }   
    }
	//预防接种证打印
	function PrintVacciImmune(jsonPrint){
		LODOP.PRINT_INITA("0.26cm","0.26cm","20.8cm","14cm","预防接种证");
		LODOP.SET_PRINT_STYLE("FontColor","#0000FF");
		LODOP.ADD_PRINT_TEXT("1.19cm","13.89cm","6.0cm","0.53cm",jsonPrint.fileNo);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",16);
		LODOP.ADD_PRINT_TEXT("2.78cm","14.66cm","1.88cm","0.53cm",jsonPrint.name);
		LODOP.ADD_PRINT_TEXT("2.91cm","18.2cm","0.56cm","0.53cm",jsonPrint.male);
		LODOP.ADD_PRINT_TEXT("2.91cm","18.86cm","0.64cm","0.53cm",jsonPrint.female);
		LODOP.ADD_PRINT_TEXT("3.7cm","11.69cm","0.93cm","0.53cm",jsonPrint.year);
		LODOP.ADD_PRINT_TEXT("3.7cm","12.75cm","0.66cm","0.53cm",jsonPrint.month);
		LODOP.ADD_PRINT_TEXT("3.7cm","13.55cm","0.66cm","0.53cm",jsonPrint.day);
		LODOP.ADD_PRINT_TEXT("3.7cm","14.37cm","0.66cm","0.53cm",jsonPrint.hours);
		LODOP.ADD_PRINT_TEXT("3.7cm","15.13cm","0.79cm","0.53cm",jsonPrint.minutes);
		var weight = (parseInt(jsonPrint.weight) / 1000 ).toFixed(2);
		LODOP.ADD_PRINT_TEXT("3.7cm","17.7cm","1.59cm","0.53cm",""+weight);
		LODOP.ADD_PRINT_TEXT("4.55cm","11.67cm","2.78cm","0.53cm",jsonPrint.county);
		LODOP.ADD_PRINT_TEXT("4.52cm","16.46cm","2.35cm","0.53cm",jsonPrint.township);
		LODOP.ADD_PRINT_TEXT("5.4cm","11.64cm","2.75cm","0.53cm",jsonPrint.village);
		LODOP.ADD_PRINT_TEXT("6.19cm","11.64cm","7.06cm","0.53cm",jsonPrint.address);
		LODOP.ADD_PRINT_TEXT("7.04cm","12.22cm","1.9cm","0.53cm",jsonPrint.fname);
		LODOP.ADD_PRINT_TEXT("7.04cm","16.64cm","1.93cm","0.53cm",jsonPrint.mname);
		LODOP.ADD_PRINT_TEXT("7.88cm","12.17cm","3.25cm","0.53cm",jsonPrint.fworkUnit);
		LODOP.ADD_PRINT_TEXT("7.88cm","16.59cm","3.44cm","0.53cm",jsonPrint.mworkUnit);
		LODOP.ADD_PRINT_TEXT("8.73cm","12.2cm","2.65cm","0.53cm",jsonPrint.ftel);
		LODOP.ADD_PRINT_TEXT("8.73cm","16.59cm","3.15cm","0.53cm",jsonPrint.mtel);
		LODOP.ADD_PRINT_TEXT("9.6cm","13.36cm","0.95cm","0.53cm",jsonPrint.moveYear);
		LODOP.ADD_PRINT_TEXT("9.6cm","14.66cm","0.58cm","0.53cm",jsonPrint.moveMonth);
		LODOP.ADD_PRINT_TEXT("9.6cm","15.56cm","0.66cm","0.53cm",jsonPrint.moveDay);
		LODOP.ADD_PRINT_TEXT("10.45cm","13.36cm","2.65cm","0.53cm",jsonPrint.moveAddress);
		LODOP.ADD_PRINT_TEXT("11.46cm","11.88cm","0.87cm","0.53cm",jsonPrint.cardYear);
		LODOP.ADD_PRINT_TEXT("11.46cm","12.99cm","0.61cm","0.53cm",jsonPrint.cardMonth);
		LODOP.ADD_PRINT_TEXT("11.46cm","13.68cm","0.66cm","0.53cm",jsonPrint.cardDay);
		LODOP.ADD_PRINT_TEXT("11.48cm","16.56cm","2.88cm","0.53cm",jsonPrint.buildCardPerson);
		LODOP.ADD_PRINT_TEXT("12.33cm","13.34cm","1.83cm","0.53cm",jsonPrint.unitTel);
		LODOP.ADD_PRINT_TEXT("12.33cm","17.38cm","2.88cm","0.53cm",jsonPrint.unit);
	}
	
	//出生医学证明月报表
	function printBirthMonthReport(html,title,date){
		if(CheckActiveX()){
			BirthMonthReportPagePrint(html,title,date);
			LODOP.PREVIEW();	
		}
	}
	//出生医学证明月报表
	function BirthMonthReportPagePrint(html,title,date){  
		LODOP.PRINT_INITA(10,10,754,453,title);
		LODOP.ADD_PRINT_TEXT(30,622,104,22,"第#页/共&页");
		LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
		LODOP.SET_PRINT_STYLEA(0,"Horient",1);
		LODOP.ADD_PRINT_TABLE(57,9,714,250,html);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		LODOP.ADD_PRINT_LINE(53,23,52,725,0,1);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.ADD_PRINT_TEXT(30,33,300,22,date);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	}; 
	
	//
	function printUrl(url){
		if(CheckActiveX()){
			LODOP.PRINT_INIT("");
			LODOP.ADD_PRINT_URL(30,20,746,"100%",url);
			LODOP.SET_PRINT_STYLEA(1,"HOrient",3);
			LODOP.SET_PRINT_STYLEA(1,"VOrient",3);
			LODOP.PREVIEW();	
		}
	}
	
	//儿童体检记录打印
	function childExamPrintProtect(html,title,visitDate,fileNo){
		if(CheckActiveX()){
			ChildPagePrint(html,title,visitDate,fileNo);
			LODOP.PREVIEW();	
			
		}
	}
	
	function printHTML(html,title,width,height){
		if(CheckActiveX()){
			LODOP.PRINT_INITA(10,10,width,height,title);
			//LODOP.SET_PRINT_PAGESIZE (1, 0, 0,"A4");
			LODOP.SET_PRINT_MODE("AUTO_CLOSE_PREWINDOW",true);
//			LODOP.ADD_PRINT_TABLE(0,0,"20cm",height,html);
			LODOP.SET_PRINT_STYLE("FontSize",12);
			LODOP.SET_PRINT_STYLE("FontName",'宋体');
			LODOP.ADD_PRINT_HTML(0,0,'20cm',height,html);
			LODOP.PREVIEW();	
//			LODOP.PRINT_SETUP();
		}
	}
	
	//儿童体检记录打印
	function printHTML1(html,title,width,height,intOrient){
		if(CheckActiveX()){
			LODOP.PRINT_INITA(0,0,height,width,title);
			LODOP.SET_PRINT_MODE("AUTO_CLOSE_PREWINDOW",true);
			//LODOP.SET_PRINT_PAGESIZE (0, width, height,"LodopCustomPage");
			LODOP.SET_PRINT_STYLE("FontSize",12);
			LODOP.SET_PRINT_STYLE("FontName",'宋体');
			LODOP.ADD_PRINT_HTML(0,0,height,width,html);
			LODOP.SET_PRINT_MODE('RESELECT_PAGESIZE',1);
			LODOP.SET_PRINT_MODE('RESELECT_ORIENT',1);
			LODOP.SET_PRINT_MODE('RESELECT_PRINTER',1);
			LODOP.SET_PRINT_MODE('RESELECT_COPIES',1);
			LODOP.PREVIEW();	
			//LODOP.PRINT_SETUP();
		}
	}
	//儿童体检记录打印
	function ChildPagePrint(html,title,visitDate,fileNo){
		LODOP.PRINT_INITA(10,10,754,453,title);
		LODOP.ADD_PRINT_TABLE(77,12,"20cm",358,html);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		LODOP.ADD_PRINT_TEXT(50,75,160,25,fileNo);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
		LODOP.ADD_PRINT_TEXT(9,220,264,65,"儿   童   体   检\n施甸县妇幼保健院");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",16);
		LODOP.SET_PRINT_STYLEA(0,"FontColor","#10A2D7");
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXT(49,24,63,25,"编号：");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
		LODOP.SET_PRINT_STYLEA(0,"FontColor","#10A2D7");
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXT(49,480,95,25,"体检日期：");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
		LODOP.SET_PRINT_STYLEA(0,"FontColor","#10A2D7");
		LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXT(50,562,100,20,visitDate);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
	}
	
	//孕产妇体检记录打印
	function womanExamPrintProtect(html,title){
		if(CheckActiveX()){
			WomanPagePrint(html,title);
			LODOP.PREVIEW();	
		}
	}
	//预防接种登记打印
	function VacciImmunePrintProtect(html,title){
		if(CheckActiveX()){
			VacciImmunePrint(html,title);
			LODOP.PREVIEW();	
//			LODOP.PRINT_SETUP();
		}
	}
	//孕产妇体检记录打印
	function VacciImmunePrint(html,title){
		LODOP.PRINT_INITA(10,10,754,453,title);
		LODOP.ADD_PRINT_TABLE(58,12,"20.8cm",390,html);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
	}
	//孕产妇体检记录打印
	function WomanPagePrint(html,title){
		LODOP.PRINT_INITA(10,10,754,453,title);
		LODOP.ADD_PRINT_TEXT(30,622,104,22,"第#页/共&页");
		LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
		LODOP.SET_PRINT_STYLEA(0,"Horient",1);
		LODOP.ADD_PRINT_TABLE(58,12,"20cm",390,html);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		LODOP.ADD_PRINT_LINE(53,23,52,725,0,1);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.ADD_PRINT_TEXT(30,33,260,22,title);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	}
	
	//体检记录打印预览
	function medicalExamPrintPreview(html){
		if(CheckActiveX()){
			medicalExamPrint(html);
//			LODOP.PREVIEW();
			LODOP.PRINT_SETUP();
		}
	}
	
	//体检记录打印报表
	function medicalExamPrint(html){
		LODOP.PRINT_INITA(10,10,754,453,"体检记录");
		LODOP.ADD_PRINT_TEXT(30,622,104,22,"第#页/共&页");
		LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
		LODOP.SET_PRINT_STYLEA(0,"Horient",1);
		LODOP.ADD_PRINT_URL(57,9,714,364,html);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		LODOP.ADD_PRINT_LINE(53,23,52,725,0,1);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
//		LODOP.ADD_PRINT_TEXT(30,33,260,22,date);
	}
	//统计打印报表
	function MultiPagePrint(html,title,date){  
		LODOP.PRINT_INITA(10,10,754,453,title);
		LODOP.ADD_PRINT_TEXT(30,622,104,22,"第#页/共&页");
		LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
		LODOP.SET_PRINT_STYLEA(0,"Horient",1);
		LODOP.ADD_PRINT_TABLE(57,9,714,200,html);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		LODOP.ADD_PRINT_LINE(53,23,52,725,0,1);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.ADD_PRINT_TEXT(30,33,260,22,date);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	};    
	
	//打印报表
	function CreateFullBill(jsonPrint) {	
		LODOP.PRINT_INITA("0.82cm","0.66cm","21.0cm","29.7cm","出生证明打印");
		LODOP.SET_PRINT_STYLE("FontColor","#0000FF");
		//出生医学证明
		LODOP.ADD_PRINT_TEXT("2.86cm","3.74cm","2.22cm","0.53cm",jsonPrint.fullNamfOfbaby);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("2.86cm","8.54cm","0.56cm","0.53cm",jsonPrint.sex);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("2.86cm","11.60cm","1.16cm","0.53cm",jsonPrint.years);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("2.86cm","13.56cm","0.66cm","0.53cm",jsonPrint.months);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("2.86cm","14.75cm","0.66cm","0.53cm",jsonPrint.day);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("2.86cm","15.90cm","0.66cm","0.53cm",jsonPrint.hour);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("2.86cm","17.15cm","0.66cm","0.53cm",jsonPrint.minute);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("3.65cm","3.74cm","1.26cm","0.53cm",jsonPrint.weeks);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("3.65cm","9.2cm","1.32cm","0.53cm",jsonPrint.weight);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("3.65cm","14.94cm","1.32cm","0.53cm",jsonPrint.height);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("4.4cm","3.24cm","1.59cm","0.53cm",jsonPrint.province);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("4.4cm","5.91cm","1.59cm","0.53cm",jsonPrint.city);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("4.4cm","8.3cm","1.59cm","0.53cm",jsonPrint.county);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("4.4cm","12.78cm","4.59cm","0.53cm",jsonPrint.borthAddressCategory);		
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("5.78cm","3.24cm","2.65cm","0.53cm",jsonPrint.motherName);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		if(jsonPrint.motherAge == 0 ){
            LODOP.ADD_PRINT_TEXT("5.78cm","6.30cm","1.22cm","0.53cm","不详");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}else{
    		LODOP.ADD_PRINT_TEXT("5.78cm","6.30cm","1.22cm","0.53cm",jsonPrint.motherAge);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}
		LODOP.ADD_PRINT_TEXT("5.78cm","8.3cm","0.95cm","0.53cm",jsonPrint.motherNationality);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("5.78cm","11.84cm","0.98cm","0.53cm",jsonPrint.motherNation);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("5.78cm","13.52cm","5cm","0.53cm",jsonPrint.motherAddress);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("6.66cm","5.41cm","0.95cm","0.53cm",jsonPrint.motherCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("6.66cm","6.76cm","0.95cm","0.53cm",jsonPrint.motherhz);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("6.66cm","8.3cm","2.00cm","0.53cm",jsonPrint.motherOther);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("6.66cm","12.68cm","4.50cm","0.53cm",jsonPrint.motherIdCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("7.86cm","3.24cm","2.65cm","0.53cm",jsonPrint.fatherName);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		if(jsonPrint.fatherAge == 0 ){
            LODOP.ADD_PRINT_TEXT("7.86cm","6.30cm","1.27cm","0.53cm","不详");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}else{
    		LODOP.ADD_PRINT_TEXT("7.86cm","6.30cm","1.27cm","0.53cm",jsonPrint.fatherAge);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}
		LODOP.ADD_PRINT_TEXT("7.86cm","8.3cm","1.14cm","0.53cm",jsonPrint.fatherNationality);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("7.86cm","11.84cm","1.06cm","0.53cm",jsonPrint.fatherNation);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("7.86cm","13.52cm","5cm","0.53cm",jsonPrint.fatherAddress);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("8.66cm","5.41cm","0.95cm","0.53cm",jsonPrint.fatherCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("8.66cm","6.76cm","0.95cm","0.53cm",jsonPrint.fatherhz);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("8.66cm","8.3cm","2.00cm","0.53cm",jsonPrint.fatherOther);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("8.66cm","12.68cm","4.50cm","0.53cm",jsonPrint.fatherIdCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("10.43cm","3.75cm","5.00cm","0.53cm",jsonPrint.issuingOrganization);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("11.20cm","3.32cm","1.4cm","0.53cm",jsonPrint.issueYear);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("11.20cm","5.66cm","0.66cm","0.53cm",jsonPrint.issueMonth);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("11.20cm","6.86cm","1.03cm","0.53cm",jsonPrint.issueDay);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		//LODOP.ADD_PRINT_TEXT("9.3cm","11.64cm","3cm","0.53cm",jsonPrint.certifiId);
		//LODOP.SET_PRINT_STYLEA(0,"FontSize",14);
        
		//出生医学证明副页
		LODOP.ADD_PRINT_TEXT("15.03cm","3.24cm","2.22cm","0.53cm",jsonPrint.fullNamfOfbaby);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("15.03cm","7.57cm","0.56cm","0.53cm",jsonPrint.sex);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("15.03cm","11.44cm","1.16cm","0.53cm",jsonPrint.years);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("15.03cm","13.44cm","0.66cm","0.53cm",jsonPrint.months);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("15.03cm","14.65cm","0.66cm","0.53cm",jsonPrint.day);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("15.03cm","16.40cm","0.66cm","0.53cm",jsonPrint.hour);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("15.03cm","17.80cm","0.66cm","0.53cm",jsonPrint.minute);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("15.53cm","3.24cm","0.66cm","0.53cm",jsonPrint.weeks);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("15.53cm","7.57cm","1.32cm","0.53cm",jsonPrint.weight);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("15.53cm","12.64cm","1.32cm","0.53cm",jsonPrint.height);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("16.05cm","3.24cm","1.59cm","0.53cm",jsonPrint.province);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("16.05cm","5.91cm","1.59cm","0.53cm",jsonPrint.city);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("16.05cm","8.3cm","1.59cm","0.53cm",jsonPrint.county);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("16.05cm","12.78cm","4.59cm","0.53cm",jsonPrint.borthAddressCategory);		
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("16.90cm","3.04cm","2.65cm","0.53cm",jsonPrint.motherName);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		if(jsonPrint.motherAge == 0 ){
            LODOP.ADD_PRINT_TEXT("16.90cm","5.76cm","1.22cm","0.53cm","不详");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}else{
    		LODOP.ADD_PRINT_TEXT("16.90cm","5.76cm","1.22cm","0.53cm",jsonPrint.motherAge);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}
		LODOP.ADD_PRINT_TEXT("16.90cm","7.57cm","0.95cm","0.53cm",jsonPrint.motherNationality);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("16.90cm","9.55cm","0.98cm","0.53cm",jsonPrint.motherNation);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("16.90cm","11.72cm","5cm","0.53cm",jsonPrint.motherAddress);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("17.34cm","5.29cm","0.95cm","0.53cm",jsonPrint.motherCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("17.34cm","6.75cm","0.95cm","0.53cm",jsonPrint.motherhz);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("17.34cm","10.48cm","2.00cm","0.53cm",jsonPrint.motherOther);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("17.34cm","13.54cm","4.50cm","0.53cm",jsonPrint.motherIdCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("18.18cm","3.04cm","2.65cm","0.53cm",jsonPrint.fatherName);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		if(jsonPrint.fatherAge == 0 ){
            LODOP.ADD_PRINT_TEXT("18.18cm","5.76cm","1.27cm","0.53cm","不详");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}else{
    		LODOP.ADD_PRINT_TEXT("18.18cm","5.76cm","1.27cm","0.53cm",jsonPrint.fatherAge);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}
		LODOP.ADD_PRINT_TEXT("18.18cm","7.57cm","1.14cm","0.53cm",jsonPrint.fatherNationality);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("18.18cm","9.55cm","1.06cm","0.53cm",jsonPrint.fatherNation);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("18.18cm","11.72cm","5cm","0.53cm",jsonPrint.fatherAddress);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("18.68cm","5.29cm","0.95cm","0.53cm",jsonPrint.fatherCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("18.68cm","6.75cm","0.95cm","0.53cm",jsonPrint.fatherhz);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("18.68cm","10.48cm","2.00cm","0.53cm",jsonPrint.fatherOther);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("18.68cm","13.54cm","4.50cm","0.53cm",jsonPrint.fatherIdCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("19.55cm","3.60cm","5.00cm","0.53cm",jsonPrint.issuingOrganization);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("20.20cm","2.85cm","1.4cm","0.53cm",jsonPrint.issueYear);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("20.20cm","4.30cm","0.66cm","0.53cm",jsonPrint.issueMonth);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("20.20cm","5.56cm","1.03cm","0.53cm",jsonPrint.issueDay);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		//LODOP.ADD_PRINT_TEXT("18.25cm","11.64cm","3cm","0.53cm",jsonPrint.certifiId);
		//LODOP.SET_PRINT_STYLEA(0,"FontSize",14);
		
	
		//出生医学证明存根
		LODOP.ADD_PRINT_TEXT("22.44cm","3.24cm","2.22cm","0.53cm",jsonPrint.fullNamfOfbaby);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("22.44cm","7.57cm","0.56cm","0.53cm",jsonPrint.sex);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("22.44cm","11.44cm","1.16cm","0.53cm",jsonPrint.years);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("22.44cm","13.44cm","0.66cm","0.53cm",jsonPrint.months);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("22.44cm","14.65cm","0.66cm","0.53cm",jsonPrint.day);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("22.44cm","16.40cm","0.66cm","0.53cm",jsonPrint.hour);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("22.44cm","17.80cm","0.66cm","0.53cm",jsonPrint.minute);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("22.89cm","3.24cm","0.66cm","0.53cm",jsonPrint.weeks);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("22.89cm","7.57cm","1.32cm","0.53cm",jsonPrint.weight);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("22.89cm","12.64cm","1.32cm","0.53cm",jsonPrint.height);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("23.38cm","3.24cm","1.59cm","0.53cm",jsonPrint.province);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("23.38cm","5.91cm","1.59cm","0.53cm",jsonPrint.city);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("23.38cm","8.3cm","1.59cm","0.53cm",jsonPrint.county);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("23.38cm","12.78cm","4.59cm","0.53cm",jsonPrint.borthAddressCategory);		
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("24.18cm","3.04cm","2.65cm","0.53cm",jsonPrint.motherName);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		if(jsonPrint.motherAge == 0 ){
            LODOP.ADD_PRINT_TEXT("24.18cm","5.76cm","1.22cm","0.53cm","不详");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}else{
    		LODOP.ADD_PRINT_TEXT("24.18cm","5.76cm","1.22cm","0.53cm",jsonPrint.motherAge);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}
		LODOP.ADD_PRINT_TEXT("24.18cm","7.57cm","0.95cm","0.53cm",jsonPrint.motherNationality);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("24.18cm","9.55cm","0.98cm","0.53cm",jsonPrint.motherNation);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("24.18cm","11.72cm","5cm","0.53cm",jsonPrint.motherAddress);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("24.68cm","5.29cm","0.95cm","0.53cm",jsonPrint.motherCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("24.68cm","6.75cm","0.95cm","0.53cm",jsonPrint.motherhz);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("24.68cm","10.48cm","2.00cm","0.53cm",jsonPrint.motherOther);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("24.68cm","13.54cm","4.50cm","0.53cm",jsonPrint.motherIdCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("25.38cm","3.04cm","2.65cm","0.53cm",jsonPrint.fatherName);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		if(jsonPrint.fatherAge == 0 ){
            LODOP.ADD_PRINT_TEXT("25.38cm","5.76cm","1.27cm","0.53cm","不详");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}else{
    		LODOP.ADD_PRINT_TEXT("25.38cm","5.76cm","1.27cm","0.53cm",jsonPrint.fatherAge);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		}
		LODOP.ADD_PRINT_TEXT("25.38cm","7.57cm","1.14cm","0.53cm",jsonPrint.fatherNationality);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("25.38cm","9.55cm","1.06cm","0.53cm",jsonPrint.fatherNation);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("25.38cm","11.72cm","5cm","0.53cm",jsonPrint.fatherAddress);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("25.88cm","5.29cm","0.95cm","0.53cm",jsonPrint.fatherCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("25.88cm","6.75cm","0.95cm","0.53cm",jsonPrint.fatherhz);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("25.88cm","10.48cm","2.00cm","0.53cm",jsonPrint.fatherOther);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("25.88cm","13.54cm","4.50cm","0.53cm",jsonPrint.fatherIdCard);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("26.62cm","3.04cm","5.00cm","0.53cm",jsonPrint.widwife);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("27.08cm","3.04cm","5.00cm","0.53cm",jsonPrint.issuingUsers);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("27.08cm","8.56cm","1.4cm","0.53cm",jsonPrint.issueYear);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("27.08cm","10.50cm","0.66cm","0.53cm",jsonPrint.issueMonth);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("27.08cm","11.50cm","1.03cm","0.53cm",jsonPrint.issueDay);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXT("27.50cm","3.30cm","5.00cm","0.53cm",jsonPrint.issuingOrganization);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		//LODOP.ADD_PRINT_TEXT("28.2cm","11.64cm","3cm","0.53cm",jsonPrint.certifiId);
		//LODOP.SET_PRINT_STYLEA(0,"FontSize",14);
	};	
})();