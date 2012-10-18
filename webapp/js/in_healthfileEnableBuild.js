var services = {
	get : healthfileMaternalService.get,
	save : healthfileMaternalService.save,
	propValidate : healthfileMaternalService.hasAllThese,
	tableName : 'HealthFileMaternal'
};

var cfg = [ {
	id : "name",
	xtype : "input",
	setting : {
		maxlen : 30
	// showOnly : true
	// readonly : true
	}
}, {
	id : "fileNo",
	xtype : "input",
	setting : {
		// showOnly : true,
		readonly : true
	// asLabel : true
	}
}, {
	id : "birthday",
	xtype : "input",
	setting : {
		format : 'date'
	}
}, {
	id : "tel",
	xtype : "input",
	setting : {
		maxlen : 11
	}
}, {
	id : "firstAidTel",
	xtype : "input",
	setting : {
		maxlen : 11
	}
}, {
	id : "addressProvence",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "addressCity",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "addressCounty",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "addressTownship",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "addressVillage",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "addressGroup",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "residenceProvence",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "residenceCity",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "residenceCounty",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "residenceTownship",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "residenceVillage",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "residenceGroup",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "highRiskCode",
	xtype : "input"
}, {
	id : "buildUnit",
	xtype : "input",
	setting : {
		size : 30
	}
}, {
	id : "buildDate",
	xtype : "input",
	setting : {
		format : 'date',
		defaultVal : new Date()
	}
}, {
	id : "idnumber",
	xtype : "input",
	setting : {
		maxlen : 18
	}
}, {
	id : "workUnit",
	xtype : "input",
	setting : {
		size : 30
	}
}, {
	id : "distance",
	xtype : "input",
	setting : {
		size : 15,
		format : 'num'
	}
}, {
	id : "recuperateProvence",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "recuperateCity",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "recuperateCounty",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "recuperateTownship",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "recuperateVillage",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "recuperateGroup",
	xtype : "input",
	setting : {
		size : 10
	}
}, {
	id : "husbandName",
	xtype : "input"
}, {
	id : "husbandBirthday",
	xtype : "input",
	setting : {
		format : 'date'
	}
}, {
	id : "husbandTel",
	xtype : "input",
	setting : {
		maxlen : 11
	}
}, {
	id : "husbandOccupation",
	xtype : "input"
}, {
	id : "husbandOccupationOther",
	xtype : "input"
}, {
	id : "husbandWorkUnit",
	xtype : "input",
	setting : {
		size : 30
	}
}, {
	id : "gravidity",
	xtype : "input",
	setting : {
		format : 'int'
	},
	required : [true, "孕次"]
}, {
	id : "isClosed",
	xtype : "input"
}, {
	id : "areaOfResidence",
	xtype : "list",
	setting : {
		ds : "2010",
		newlineStep : 2
	}
}, {
	id : "nationality",
	xtype : "list",
	setting : {
		ds : "2011",
		isDefaultVal : true,
		defaultVal : 0
	},
	requires : {
		valEq : "2",
		fields : [ "nationalityOther" ]
	}
// 
}, {
	id : "nationalityOther",
	xtype : "input",
	setting : {
		disabled : true,
		size : 15
	}
}, {
	id : "education",
	xtype : "list",
	setting : {
		ds : "2012"
	}
}, {
	id : "husbandEducation",
	xtype : "list",
	setting : {
		ds : "2012"
	}
}, {
	id : "folk",
	xtype : "input",
	setting : {
		readonly : true,
		size : 15
	}
}, {
	id : "folkOther",
	xtype : "input",
	setting : {
		readonly : true,
		size : 15
	}
}, {
	id : "occupation",
	xtype : "input",
	setting : {
		readonly : true,
		size : 40
	}
} ];