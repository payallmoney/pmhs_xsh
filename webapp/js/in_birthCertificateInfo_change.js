var loadTriggerParameters = [ "certifiId" ];
var services = {
	get : BirthCertificateMsgService.getChangeInfo,
	save : BirthCertificateMsgService.saveChange
};

var cfg = [ {
	id : "name",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 21
	}
}, {
	id : "borthWeekly",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 21
	}
}, {
	id : "sex",
	xtype : "list",
	setting : {
		ds : "1411"
	},
	required : [ true, "新生儿性别" ]
}, {
	id : "weight",
	xtype : "input",
	setting : {
		format : "num",
		maxlen : 10,
		size : 17
	},
	required : [ true, "体重" ]
},

{
	id : "borthOrganization",
	xtype : "input",
	setting : {
		maxlen : 20,
		size : 20
	},
	required : [ true, "接生单位" ]
}, {
	id : "widWife",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 20
	},
	required : [ true, "接生人员" ]
}, {
	id : "linkmanTel",
	xtype : "input",
	setting : {
		maxlen : 11,
		size : 20
	},
	required : [ true, "联系电话" ]
},

{
	id : "birthday",
	xtype : "datefield",
	setting : {
		defaultVal : new Date()
	}
// required : [true, "出生日期"]
}, {
	id : "province",
	xtype : "input",
	setting : {
		size : 16
	},
	required : [ true, "省" ]
}, {
	id : "city",
	xtype : "input",
	setting : {
		size : 16
	},
	required : [ true, "市" ]
}, {
	id : "county",
	xtype : "input",
	setting : {
		size : 16
	},
	required : [ true, "县" ]
}, {
	id : "township",
	xtype : "input",
	setting : {
		size : 28
	},
	required : [ true, "乡" ]
}, {
	id : "height",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 10
	},
	required : [ true, "身长" ]
}, {
	id : "motherName",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 14
	},
	required : [ true, "母亲姓名" ]
}, {
	id : "motherAge",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 10,
		format : 'int'
	},
	required : [ true, "年龄" ]
}, {
	id : "motherNationality",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 10
	},
	required : [ true, "国籍" ]
}, {
	id : "motherNation",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 17
	},
	required : [ true, "民族" ]
}, {
	id : "motherIdCard",
	xtype : "input",
	setting : {
		maxlen : 18,
		size : 17
	},
	required : [ true, "身份证号" ]
}, {
	id : "fatherName",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 10
	},
	required : [ true, "父亲姓名" ]
}, {
	id : "fatherAge",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 10,
		format : 'int'
	},
	required : [ true, "年龄" ]
}, {
	id : "fatherNationality",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 10
	},
	required : [ true, "国籍" ]
}, {
	id : "fatherNation",
	xtype : "input",
	setting : {
		maxlen : 10,
		size : 10,
		defaultVal : '汉'
	},
	required : [ true, "民族" ]
}, {
	id : "fatherIdCard",
	xtype : "input",
	setting : {
		maxlen : 18,
		size : 17
	},
	required : [ true, "身份证号" ]
}, {
	id : "borthAddressCategory",
	xtype : "input",
	setting : {
		size : 104
	}
}, {
	id : "certifiId",
	xtype : "input",
	setting : {
		maxlen : 20,
		size : 20,
		asLabel : true
	}
}, {
	id : "issuingDate",
	xtype : "datefield",
	setting : {
		defaultVal : new Date()
	}
// required : [true, "签发日期"]
}, {
	id : "issuingOrganization",
	xtype : "input",
	setting : {
		maxlen : 20,
		size : 20
	},
	required : [ true, "签证机构" ]
},

{
	id : "districtNum",
	xtype : "input",
	setting : {
		maxlen : 20,
		size : 20,
		searchDistrict : true
	},
	required : [ true, "所属行政区划编码" ]
}, {
	id : "motherAddress",
	xtype : "input",
	setting : {
		size : 104
	}
}, {
	id : "motherCardType",
	xtype : "list",
	setting : {
		ds : "2016",
		isDefaultVal : true,
		defaultVal : 0
	},
	requires : {
		valEq : "3",
		fields : [ "motherCardTypeOther" ]
	}
}, {
	id : "motherCardTypeOther",
	xtype : "input",
	setting : {
		disabled : true,
		size : 15
	}
}, {
	id : "fatherAddress",
	xtype : "input",
	setting : {
		size : 104
	}
}, {
	id : "fatherCardType",
	xtype : "list",
	setting : {
		ds : "2016",
		isDefaultVal : true,
		defaultVal : 0
	},
	requires : {
		valEq : "3",
		fields : [ "fatherCardTypeOther" ]
	}
}, {
	id : "fatherCardTypeOther",
	xtype : "input",
	setting : {
		disabled : true,
		size : 15
	}
}, {
	id : "issuingUsers",
	xtype : "input",
	setting : {
		size : 20
	}
}, {
	id : "sourceBirthCertifiId",
	xtype : "input",
	setting : {
		size : 20
	}
}, {
	id : "changeReasons",
	xtype : "input",
	setting : {
		size : 104
	}
} ];
