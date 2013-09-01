drop FUNCTION [dbo].[vacc_enc1]
go
create FUNCTION [dbo].[vacc_enc1]
(@charcode AS varchar (100),
@keyword AS varchar (2)
)
RETURNS varchar(100)
AS
BEGIN
declare @strlen as int
declare @i as int
declare @num as int
declare @flag as varchar(2)
declare @ret as varchar(100)

select @strlen = len(@charcode)
select @i = 1
select @num = 1

select @flag = @keyword
select @ret = @keyword

while @i <= @strlen 
begin
	select @flag = dbo.vacc_enc(substring(@charcode,@i,1),@flag,@i)
	select @ret = @ret+@flag
	select @num = @num+1
	--if @num =0 
	--	select @num = 1
	--else 
	--	select @num = 0
	select @i = @i+1
end
return @ret
END
go
select dbo.vacc_enc1('A1A2A3A4A5','93')
go

