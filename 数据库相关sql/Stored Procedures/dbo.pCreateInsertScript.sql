SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

-- ============================================= 
-- Author: 华岭 
-- Create date: 2008-10-28 
-- Description: 将表数据生成Insert脚本 
-- Demo : exec pCreateInsertScript 'BexmCodeType','dictypeid = 61' 
-- ============================================= 
Create proc [dbo].[pCreateInsertScript] (@tablename varchar(256),@con nvarchar(400)) 
as 
begin 
set nocount on 
declare @sqlstr varchar(4000) 
declare @sqlstr1 varchar(4000) 
declare @sqlstr2 varchar(4000) 
select @sqlstr='select ''insert '+@tablename 
select @sqlstr1='' 
select @sqlstr2='(' 
select @sqlstr1='values (''+' 
select @sqlstr1=@sqlstr1+col+'+'',''+' ,@sqlstr2=@sqlstr2+name +',' from (select case 
when a.xtype =173 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar('+convert(varchar(4),a.length*2+2)+'),'+a.name +')'+' end' 
when a.xtype =104 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar(1),'+a.name +')'+' end' 
when a.xtype =175 then 'case when '+a.name+' is null then ''NULL'' else '+'''''''''+'+'replace('+a.name+','''''''','''''''''''')' + '+'''''''''+' end' 
when a.xtype =61 then 'case when '+a.name+' is null then ''NULL'' else '+'''''''''+'+'convert(varchar(23),'+a.name +',121)'+ '+'''''''''+' end' 
when a.xtype =106 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar('+convert(varchar(4),a.xprec+2)+'),'+a.name +')'+' end' 
when a.xtype =62 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar(23),'+a.name +',2)'+' end' 
when a.xtype =56 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar(11),'+a.name +')'+' end' 
when a.xtype =60 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar(22),'+a.name +')'+' end' 
when a.xtype =239 then 'case when '+a.name+' is null then ''NULL'' else '+'''''''''+'+'replace('+a.name+','''''''','''''''''''')' + '+'''''''''+' end' 
when a.xtype =108 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar('+convert(varchar(4),a.xprec+2)+'),'+a.name +')'+' end' 
when a.xtype =231 then 'case when '+a.name+' is null then ''NULL'' else '+'''''''''+'+'replace('+a.name+','''''''','''''''''''')' + '+'''''''''+' end' 
when a.xtype =59 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar(23),'+a.name +',2)'+' end' 
when a.xtype =58 then 'case when '+a.name+' is null then ''NULL'' else '+'''''''''+'+'convert(varchar(23),'+a.name +',121)'+ '+'''''''''+' end' 
when a.xtype =52 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar(12),'+a.name +')'+' end' 
when a.xtype =122 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar(22),'+a.name +')'+' end' 
when a.xtype =127 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar(6),'+a.name +')'+' end' 
when a.xtype =48 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar(6),'+a.name +')'+' end' 
when a.xtype =165 then 'case when '+a.name+' is null then ''NULL'' else '+'convert(varchar('+convert(varchar(4),a.length*2+2)+'),'+a.name +')'+' end' 
when a.xtype =167 then 'case when '+a.name+' is null then ''NULL'' else '+'''''''''+'+'replace('+a.name+','''''''','''''''''''')' + '+'''''''''+' end' 
else '''NULL''' 
end as col,a.colid,a.name 
from syscolumns a where a.id = object_id(@tablename) 
and a.xtype <>189 and a.xtype <>34 and a.xtype <>35 and a.xtype <>36 
)t order by colid 
select @sqlstr=@sqlstr+left(@sqlstr2,len(@sqlstr2)-1)+') '+left(@sqlstr1,len(@sqlstr1)-3)+')'' from '+@tablename + ' where 1=1 ' --and ' + isnull(@con,'') 
print @sqlstr 
exec( @sqlstr) 
set nocount off 
end


GO
