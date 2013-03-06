CREATE TABLE [dbo].[HealthFile]
(
[FileNo] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Name] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Address] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[ResidenceAddress] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[TEL] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Township] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[Village] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[BuildUnit] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[BuildPerson] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[BuildDate] [datetime] NULL,
[DistrictNumber] [char] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Name_Png] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[LastModifyDate] [datetime] NULL,
[ModifyPerson] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[BarCode] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[IsOverCount] [smallint] NULL CONSTRAINT [DF__HealthFil__IsOve__60924D76] DEFAULT ((0)),
[IsModifyOrNew] [smallint] NULL CONSTRAINT [DF__HealthFil__IsMod__618671AF] DEFAULT ((0)),
[PaperFileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Status] [int] NOT NULL CONSTRAINT [DF__HealthFil__Statu__347EC10E] DEFAULT ((0))
) ON [PRIMARY]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO
CREATE TRIGGER [dbo].[tel_HealthFile] on [dbo].[HealthFile]  FOR  INSERT ,  UPDATE AS 
 begin 
if exists (select 1 from deleted) 
 begin 
 update Sms_PersonTel set tel = b.TEL from Sms_PersonTel a , inserted b , deleted c where a.fileNo = b.fileNo and a.fileNo =c.fileNo  and b.TEL <> c.TEL and len(b.TEL)=11 and left(b.TEL,1) <>'0'  
 insert into Sms_PersonTel  select b.fileNo,b.fileNo,'0',hf.name, b.TEL,'HealthFile.TEL',-1  from HealthFile hf ,inserted b where len(b.TEL)= 11  and left(b.TEL,1) <>'0' and hf.fileNo=b.fileNo  and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	 
end 
 else 
 begin 
 
 update Sms_PersonTel set tel = b.TEL from Sms_PersonTel a , inserted b  where a.fileNo = b.fileNo  and len(b.TEL)=11 and left(b.TEL,1) <>'0'  
 insert into Sms_PersonTel  select b.fileNo,b.fileNo,'0',hf.name, b.TEL,'HealthFile.TEL',-1  from HealthFile hf ,inserted b where len(b.TEL)= 11  and left(b.TEL,1) <>'0' and hf.fileNo=b.fileNo  and not exists(select 1 from Sms_PersonTel sp where sp.fileno = b.fileNo)	 
end 
 end
GO
ALTER TABLE [dbo].[HealthFile] ADD CONSTRAINT [pk_HealthFile] PRIMARY KEY CLUSTERED  ([FileNo]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [pmhs_healthFile] ON [dbo].[HealthFile] ([InputPersonID], [InputDate], [Name], [BarCode]) ON [PRIMARY]
GO
