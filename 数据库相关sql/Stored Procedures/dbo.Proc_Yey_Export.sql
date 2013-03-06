SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROC [dbo].[Proc_Yey_Export](
	@WorkUnit NVARCHAR(1000) = ''
)AS
BEGIN
	SELECT @WorkUnit As Class,A.FileNo,A.Name,B.Sex,B.Birthday,B.WorkUnit INTO #Tmp FROM HealthFile A,PersonalInfo B WHERE A.FileNo = B.FileNo AND B.WorkUnit = @WorkUnit
	SELECT tmp.WorkUnit,tmp.Name,tmp.Sex,tmp.Birthday,A.Height,A.Weight,A.HeightScore,A.WeightScore,
		B.Evaluate,(B.leftEyes + '/' + B.rightEyes) AS Eyes,A.Caries,B.Pleura,B.Heart,
		B.Lung,A.Hemoglobin,B.Illness
	FROM #Tmp tmp LEFT JOIN ChildrenMediExam3_6 A ON tmp.FileNo = A.FileNo LEFT JOIN WomanPhysicalItems B ON A.ID = B.ID
END
GO
