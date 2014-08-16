(function(){
	RelatedInfoForChild = {
		get : getInfo
	}
	function getInfo(fileNo){
		BabyVisitService.getRelatedInfoForChild(fileNo,function(data){
			console.dir(data);
			if(data != null && data.length > 0){
				var fields = ['mname','fname','gestationalWeeks','address','mbirthday','fbirthday'];
				if(data.length == fields.length){
					for(var i = 0;i<data.length;i++){
						console.log(data[i]);
						$('#' + fields[i] + ' input').val(data[i]);
					}
				}
			}
		});
	}
})();