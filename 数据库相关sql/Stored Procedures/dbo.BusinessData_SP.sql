SET QUOTED_IDENTIFIER OFF
GO
SET ANSI_NULLS ON
GO

--插入业务数据表　0 = 健康体检记录 1 = 新生儿家庭访视 2 = 1岁以内儿童体检 3 = 1~2岁儿童体检
--4 = 3~6岁儿童体检 5 = 第一次产前随访 6 = 第2~5次产前随访 7 = 产后访视 8 = 产后42天
--9 = 高血压随访 10 = 糖尿病随访 11 = 重性精神病随访

Create Proc [dbo].[BusinessData_SP](
	@FileNo Nvarchar(30),
	@InputPerson Nvarchar(30)
) As
Begin
	Declare @sql varchar(Max),
			@Sex Nvarchar(20),
			@items Varchar(100)
	Set @items = 'abcdefghijklmnopqrstuvwxyz0123456789'
	Delete From BusinessDataGrid Where InputPersonID = @InputPerson
	Select @Sex = Sex From PersonalInfo Where FileNo = @FileNo
	Set @sql = 
		' Select ID,"'+ @items + '" As Items,ExamDate As Date,0 As Type Into #TmpBusinessData From MedicalExam Where FileNo = "' + @FileNo + '"'
	
	Set @sql = @sql + 
		' Insert Into #TmpBusinessData ' +
		' Select ID,"" As Items,VisitDate As Date,1 As Type From BabyVisit Where FileNo = "' + @FileNo + '"'
	
	Set @sql = @sql + 
		' Insert Into #TmpBusinessData ' +
		' Select ID,"" As Items,VisitDate As Date,2 As Type From ChildrenMediExam Where FileNo = "' + @FileNo + '" And DataType = 0 '

	Set @sql = @sql + 
		' Insert Into #TmpBusinessData ' +
		' Select ID,"" As Items,VisitDate As Date,3 As Type From ChildrenMediExam Where FileNo = "' + @FileNo + '" And DataType = 1 '
	
	Set @sql = @sql + 
		' Insert Into #TmpBusinessData ' +
		' Select ID,"" As Items,VisitDate As Date,4 As Type From ChildrenMediExam3_6 Where FileNo = "' + @FileNo + '"'
	
	if @Sex = '女'
	Begin
		Set @sql = @sql + 
			' Insert Into #TmpBusinessData ' +
			' Select ID,"" As Items,VisitDate As Date,5 As Type From FirstVistBeforeBorn Where FileNo = "' + @FileNo + '"'
		Set @sql = @sql + 
			' Insert Into #TmpBusinessData ' +
			' Select ID,Item As Items,VisitDate As Date,6 As Type From VisitBeforeBorn Where FileNo = "' + @FileNo + '"'
		Set @sql = @sql + 
			' Insert Into #TmpBusinessData ' +
			' Select ID,Item As Items,VisitDate As Date,7 As Type From VisitAfterBorn Where FileNo = "' + @FileNo + '" And RecordType = 0 '
		Set @sql = @sql + 
			' Insert Into #TmpBusinessData ' +
			' Select ID,"" As Items,VisitDate As Date,8 As Type From VisitAfterBorn Where FileNo = "' + @FileNo + '" And RecordType = 1 '
		
	End
	
	Set @sql = @sql + 
		' Insert Into #TmpBusinessData ' +
		' Select ID,"" As Items,VisitDate As Date,9 As Type From HypertensionVisit Where FileNo = "' + @FileNo + '"'
	
	Set @sql = @sql + 
		' Insert Into #TmpBusinessData ' +
		' Select ID,"" As Items,VisitDate As Date,10 As Type From DiabetesVisit Where FileNo = "' + @FileNo + '"'
	
	Set @sql = @sql + 
		' Insert Into #TmpBusinessData ' +
		' Select ID,"" As Items,VisitDate As Date,11 As Type From FuriousVisit Where FileNo = "' + @FileNo + '"'	

	Set @sql = @sql + 
		' Declare @Max Int,@i Int ' + 
		' Select @Max = Max(C) From (Select Count(*) C From #TmpBusinessData Group By [Type]) A ' +
		' Set @i = 1 ' + 
		' While @i <= @Max ' +
		'	Begin ' +
		'		Insert Into BusinessDataGrid(ID,RowID,InputPersonID) Values(Lower(Replace(NewID(),''-'','''')),@i,"' + @InputPerson + '")' +
		'		Set @i = @i + 1 ' +
		'	End '
	
	Set @sql = @sql +
		' Update BusinessDataGrid Set ' +
		'		MedicalExamID = A.ID, ' +
		'		MedicalExamDate = A.Date ' +
		'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
		'		From #TmpBusinessData Where Type = 0) A ' +
		'	Where BusinessDataGrid.RowID = A.RowID '

	Set @sql = @sql +
		' Update BusinessDataGrid Set ' +
		'		BabyVisitID = A.ID, ' +
		'		BabyVisitDate = A.Date ' +
		'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
		'		From #TmpBusinessData Where Type = 1) A ' +
		'	Where BusinessDataGrid.RowID = A.RowID '
	
	Set @sql = @sql +
		' Update BusinessDataGrid Set ' +
		'		ChildrenMediExam01ID = A.ID, ' +
		'		ChildrenMediExam01Date = A.Date ' +
		'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
		'		From #TmpBusinessData Where Type = 2) A ' +
		'	Where BusinessDataGrid.RowID = A.RowID '
	Set @sql = @sql +
		' Update BusinessDataGrid Set ' +
		'		ChildrenMediExam02ID = A.ID, ' +
		'		ChildrenMediExam02Date = A.Date ' +
		'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
		'		From #TmpBusinessData Where Type = 3) A ' +
		'	Where BusinessDataGrid.RowID = A.RowID '
	Set @sql = @sql +
		' Update BusinessDataGrid Set ' +
		'		ChildrenMediExam36ID = A.ID, ' +
		'		ChildrenMediExam36Date = A.Date ' +
		'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
		'		From #TmpBusinessData Where Type = 4) A ' +
		'	Where BusinessDataGrid.RowID = A.RowID '

	if @Sex = '女'
	Begin
		Set @sql = @sql +
			' Update BusinessDataGrid Set ' +
			'		FirstVistBeforeBornID = A.ID, ' +
			'		FirstVistBeforeBornDate = A.Date ' +
			'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
			'		From #TmpBusinessData Where Type = 5) A ' +
			'	Where BusinessDataGrid.RowID = A.RowID '

		Set @sql = @sql +
			' Update BusinessDataGrid Set ' +
			'		VisitBeforeBornID = A.ID, ' +
			'		VisitBeforeBornItem = A.Items, ' +
			'		VisitBeforeBornDate = A.Date ' +
			'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
			'		From #TmpBusinessData Where Type = 6) A ' +
			'	Where BusinessDataGrid.RowID = A.RowID '
		
		Set @sql = @sql +
			' Update BusinessDataGrid Set ' +
			'		VisitAfterBornID = A.ID, ' +
			'		VisitAfterBornItem = A.Items, ' +
			'		VisitAfterBornDate = A.Date ' +
			'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
			'		From #TmpBusinessData Where Type = 7) A ' +
			'	Where BusinessDataGrid.RowID = A.RowID '
		
		Set @sql = @sql +
			' Update BusinessDataGrid Set ' +
			'		VisitAfterBorn42ID = A.ID, ' +
			'		VisitAfterBorn42Date = A.Date ' +
			'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
			'		From #TmpBusinessData Where Type = 8) A ' +
			'	Where BusinessDataGrid.RowID = A.RowID '
	End
	
	Set @sql = @sql +
		' Update BusinessDataGrid Set ' +
		'		HypertensionVisitID = A.ID, ' +
		'		HypertensionVisitDate = A.Date ' +
		'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
		'		From #TmpBusinessData Where Type = 9) A ' +
		'	Where BusinessDataGrid.RowID = A.RowID '
	
	Set @sql = @sql +
		' Update BusinessDataGrid Set ' +
		'		DiabetesVisitID = A.ID, ' +
		'		DiabetesVisitDate = A.Date ' +
		'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
		'		From #TmpBusinessData Where Type = 10) A ' +
		'	Where BusinessDataGrid.RowID = A.RowID '	
	
	Set @sql = @sql +
		' Update BusinessDataGrid Set ' +
		'		FuriousVisitID = A.ID, ' +
		'		FuriousVisitDate = A.Date ' +
		'	From (Select Row_Number() over (Order By Date ASC) As RowID,*  ' +
		'		From #TmpBusinessData Where Type = 11) A ' +
		'	Where BusinessDataGrid.RowID = A.RowID '	

	Exec(@sql)
	Exec(' Select * From BusinessDataGrid Where InputPersonID = "' + @InputPerson + '" Order By RowID ' )
	--Print(@sql)
End
GO
