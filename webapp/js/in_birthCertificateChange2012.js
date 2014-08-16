var services = {
	get : BirthCertificateMsgService.getBirthCertificateBefore2012,
	save : BirthCertificateMsgService.saveBirthCertificateBefore2012,
	tableName : 'BirthCertificateBefore2012'
};

var cfg = [ {
	id : "birthCertifiId",
	xtype : "input",
	setting : {
		size : 20
	}
}, {
	id : "name2012",
	xtype : "input",
	setting : {
		size : 20
	}
}, {
	id : "motherName2012",
	xtype : "input",
	setting : {
		size : 20
	}
}, {
	id : "fatherName2012",
	xtype : "input",
	setting : {
		size : 20
	}
}, {
	id : "originalBirthAddress",
	xtype : "input",
	setting : {
		size : 20
	}
}];
