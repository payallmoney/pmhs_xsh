SET QUOTED_IDENTIFIER OFF
GO
SET ANSI_NULLS ON
GO

Create Proc [dbo].[VaccineImmun_SP](
	@districtNumber varchar(30),
	@where_str varchar(max) = ''
)As
Begin
	Declare @sql Varchar(Max)
	Set @sql = 
		' Select * From HealthFile a ' + 
		' Inner Join PersonalInfo b On a.FileNo = b.FileNo ' +
		' Left Join VaccineImmune c On a.FileNo = c.VFileNo ' + 
		' Where SubString(a.DistrictNumber,1,' + Cast(Len(@districtNumber) As Varchar(10)) + ')="' + @districtNumber + '" ' +
		@where_str
	Exec(@sql)
End 
GO
