SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO



/**
* @flag = 0 儿保@flag = 1 妇保@flag = 2 预防接种卡
**/
Create Proc [dbo].[Proc_ExecData](
	@flag Nvarchar(10) = '',
    @ids Nvarchar(36) = '',
	@optType Nvarchar(10) = '0',
	@fileNo Nvarchar(30) = ''
)As
Begin
	Declare @opt Nvarchar(1000),@optCol Nvarchar(Max),@sql Nvarchar(Max)
	Set @opt = ''
	Set @optCol = ''
	Set @sql = ''
	
	If @optType = '0'
	Begin
		Set @opt = ' Update '
		Set @optCol = ' Set FileNo = ''' + @fileNo + ''''
	End
	Else If @optType = '1'
	Begin
		Set @opt = ' Delete From '
	End
	If @flag = '0'
	Begin
		Set @sql = @opt + ' BabyVisit ' + @optCol + ' Where ForeignId = ''' + @ids + '''' + 
				@opt + ' ChildrenMediExam ' + @optCol + ' Where ForeignId = ''' + @ids + ''' And DataType = 0 ' +
				@opt + ' ChildrenMediExam ' + @optCol + ' Where ForeignId = ''' + @ids + ''' And DataType = 1 ' +
				@opt + ' ChildrenMediExam3_6 ' + @optCol + ' Where ForeignId = ''' + @ids + '''' 
	End
	Else If @flag = '1'
	Begin
		Set @sql = @opt + ' FirstVistBeforeBorn ' + @optCol + ' Where ForeignId = ''' + @ids + '''' +
				@opt + ' VisitBeforeBorn ' + @optCol + ' Where ForeignId = ''' + @ids + '''' + 
				@opt + ' VisitAfterBorn ' + @optCol + ' Where ForeignId = ''' + @ids + ''' And RecordType = 0 ' +
				@opt + ' VisitAfterBorn ' + @optCol + ' Where ForeignId = ''' + @ids + ''' And RecordType = 1 '
	End 
	Else If @flag = '2'
	Begin
		Set @sql =@opt + ' VaccineImmuneInfo ' + @optCol + ' Where FileNo In (Select VFileNo From VaccineImmune Where ID = ''' + @ids + ''') ' +
				@opt + ' VaccineImmuneHistoryStaticData ' + @optCol + ' Where FileNo In (Select VFileNo From VaccineImmune Where ID = ''' + @ids + ''')'
	End
	Exec(@sql)
	Print @sql
End

GO
