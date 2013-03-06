SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO
CREATE FUNCTION [dbo].[denc]
( @str AS nvarchar (2000)
)
RETURNS nvarchar(2000)
AS
BEGIN
declare @onechar varchar(2)
declare @encchar varchar(2)
declare @returnstr varchar(2000)
declare @len INT
declare @countlen INT
select @len = len(@str)
select @countlen = 1
select @returnstr = ''

while @countlen <= @len 
BEGIN
	select @onechar = substring(@str, @countlen, 1)
	if @onechar <> '%'
		BEGIN
			select @encchar = null
			select @encchar = enccode  from dbo.cod_enc where encvalue = @onechar
			if @encchar is null 
			BEGIN
				select @encchar =   nchar(Unicode(@onechar) ^ Unicode('c'))
			end
			select @returnstr = @returnstr +@encchar 
		end
	ELSE	
		select @returnstr = @returnstr +@onechar
	select @countlen = @countlen +1
END
RETURN @returnstr 
END
GO
