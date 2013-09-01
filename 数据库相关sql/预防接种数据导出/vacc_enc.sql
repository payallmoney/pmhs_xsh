drop FUNCTION [dbo].[vacc_enc]
go
create FUNCTION [dbo].[vacc_enc]
(@charcode AS varchar (2),
@keyword AS varchar (2),
@flag as int
)
RETURNS varchar(2)
--RETURNS int
AS
BEGIN
declare @split as integer
declare @hexkeyword as integer
declare @ret as int
declare @modnum as int
declare @flag1 as int
	select @hexkeyword = convert(int, convert(varbinary, '0x'+@keyword, 1))

	select @ret = @hexkeyword  + ascii(@charcode)
	if @ret>=0xff
		select @ret = @ret+1
	select @flag1 = ((@flag-1) % 8) +1
	if @flag1 <3 
		select @modnum = 48 ^ (@flag1 -1)
	else
		select @modnum = 48 ^ (@flag1 % 3)
	select @ret =(@ret) ^@modnum
		
		/*
		select @modnum = 48
		if @flag = 1 
			select @modnum = 48
		else if @flag = 2 
			select @modnum = 49
		else if @flag = 3 
			select @modnum = 48
		else if @flag = 4 
			select @modnum = 49
		else if @flag = 5 
			select @modnum = 50
		else if @flag = 6 
			select @modnum = 48
		else if @flag = 7 
			select @modnum = 49
		else if @flag = 8 
			select @modnum = 50
		else if @flag = 9
			select @modnum = 48
		else if @flag = 10 
			select @modnum = 49
		else if @flag = 11 
			select @modnum = 48
		else if @flag = 12 
			select @modnum = 49
		else if @flag = 13 
			select @modnum = 50
		else if @flag = 14 
			select @modnum = 48
		else if @flag = 15 
			select @modnum = 49
		else if @flag = 16 
			select @modnum = 50
		else if @flag = 17 
			select @modnum = 48
		else if @flag = 18 
			select @modnum = 49
		else if @flag = 19 
			select @modnum = 48
		else if @flag = 20 
			select @modnum = 49
		else if @flag = 21
			select @modnum = 50
		else if @flag = 22 
			select @modnum = 48
		select @ret =(@ret) ^@modnum
		*/
	select @ret = @ret%256
	return UPPER(right(sys.fn_varbintohexstr(CONVERT(VARBINARY(8),@ret)),2))
END
go
select dbo.vacc_enc('5','7F',10)
go

