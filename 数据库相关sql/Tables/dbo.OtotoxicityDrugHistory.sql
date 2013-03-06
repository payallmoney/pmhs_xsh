CREATE TABLE [dbo].[OtotoxicityDrugHistory]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[BabyBarrierRegID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DrugName] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[DrugDose] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Treatment] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[OtotoxicityDrugHistory] ADD CONSTRAINT [PK__Ototoxic__3214EC2762108194] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
