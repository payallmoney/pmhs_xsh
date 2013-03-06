CREATE TABLE [dbo].[PersonalInfo]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL,
[FileNoSub] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Sex] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Birthday] [datetime] NULL,
[IDNumber] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[WorkUnit] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[TEL] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Linkman] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[LinkmanTEL] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[ResideType] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Folk] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[FolkOther] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[BloodTypeABO] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[BloodTypeRH] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Education] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Occupation] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[MaritalStatus] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[PaymentModeOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[AllergiesOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[fHistoryOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[mHistoryOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[bHistoryOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[fmHistoryOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[GeneticHistory] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[GeneticHistoryOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[DisabilityStatusOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[farmStatus] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[HomeID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[Kitchen] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Bunkers] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[DrinkingWater] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Toilet] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Poultry] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[TownStatus] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[bornStatus] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO
CREATE TRIGGER [dbo].[tel_PersonalInfo] on [dbo].[PersonalInfo]  FOR  INSERT ,  UPDATE AS 
 begin 
if exists (select 1 from deleted) 
 begin 
 update Sms_PersonTel set tel = b.LinkmanTEL from Sms_PersonTel a , inserted b , deleted c where a.fileNo = b.fileNo and a.fileNo =c.fileNo  and b.LinkmanTEL <> c.LinkmanTEL and len(b.LinkmanTEL)=11 and left(b.LinkmanTEL,1) <>'0'  
 insert into Sms_PersonTel  select b.fileNo,b.fileNo,'0',hf.name, b.LinkmanTEL,'HealthFile.TEL',-1  from HealthFile hf ,inserted b where len(b.LinkmanTEL)= 11  and left(b.LinkmanTEL,1) <>'0' and hf.fileNo=b.fileNo  and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	
 update Sms_PersonTel set tel = b.TEL from Sms_PersonTel a , inserted b , deleted c where a.fileNo = b.fileNo and a.fileNo =c.fileNo  and b.TEL <> c.TEL and len(b.TEL)=11 and left(b.TEL,1) <>'0'  
 insert into Sms_PersonTel  select b.fileNo,b.fileNo,'0',hf.name, b.TEL,'HealthFile.TEL',-1  from HealthFile hf ,inserted b where len(b.TEL)= 11  and left(b.TEL,1) <>'0' and hf.fileNo=b.fileNo  and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	 
end 
 else 
 begin 
 
 update Sms_PersonTel set tel = b.LinkmanTEL from Sms_PersonTel a , inserted b  where a.fileNo = b.fileNo  and len(b.LinkmanTEL)=11 and left(b.LinkmanTEL,1) <>'0'  
 insert into Sms_PersonTel  select b.fileNo,b.fileNo,'0',hf.name, b.LinkmanTEL,'HealthFile.TEL',-1  from HealthFile hf ,inserted b where len(b.LinkmanTEL)= 11  and left(b.LinkmanTEL,1) <>'0' and hf.fileNo=b.fileNo  and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	 
 update Sms_PersonTel set tel = b.TEL from Sms_PersonTel a , inserted b  where a.fileNo = b.fileNo  and len(b.TEL)=11 and left(b.TEL,1) <>'0'  
 insert into Sms_PersonTel  select b.fileNo,b.fileNo,'0',hf.name, b.TEL,'HealthFile.TEL',-1  from HealthFile hf ,inserted b where len(b.TEL)= 11  and left(b.TEL,1) <>'0' and hf.fileNo=b.fileNo  and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	 
end 
 end
GO
ALTER TABLE [dbo].[PersonalInfo] ADD CONSTRAINT [pk_PersonalInfo] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [pmhs_personalInfo] ON [dbo].[PersonalInfo] ([FileNo], [InputPersonID], [InputDate], [Birthday]) ON [PRIMARY]
GO
