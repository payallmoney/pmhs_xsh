d:
cd D:\workspace\pmhs_sd\resin
set RESIN_HOME=D:\resin-4.0.32
%RESIN_HOME%\resin.exe console  -conf %cd%\conf\resin.xml  -Xms512m -Xmx512m -XX:MaxNewSize=128m -XX:MaxPermSize=128m

cmd /k
