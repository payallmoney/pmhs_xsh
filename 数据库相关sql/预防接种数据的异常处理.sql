DECLARE @fileno varchar(50), @rownumber varchar(50),@ColNum int ,@Number int;
DECLARE contact_cursor 
CURSOR FOR select fileno,rownumber ,ColNum ,Number  from VaccineImmuneInfo group by  fileno , rownumber ,ColNum ,Number having count(*) >1;
OPEN contact_cursor;
FETCH NEXT FROM contact_cursor INTO @fileno, @rownumber,@ColNum,@Number;
WHILE @@FETCH_STATUS = 0BEGIN
	--保留最近录入的记录
	Select * From VaccineImmuneInfo Where FileNo = @fileno And RowNumber = @rownumber And ColNum = @ColNum And Number = @Number And InputDate != 
	(Select MAX(InputDate) From VaccineImmuneInfo Where FileNo = @fileno And RowNumber = @rownumber And ColNum = @ColNum And Number = @Number)
	FETCH NEXT FROM contact_cursor INTO @fileno, @rownumber,@ColNum,@Number;
END
CLOSE contact_cursor;
DEALLOCATE contact_cursor;
GO