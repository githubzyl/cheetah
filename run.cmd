@echo off
set dir=%~dp0
%dir:~0,2%
echo ��ʼ���
cd %dir%
call mvn clean package -DskipTests
echo ������
echo ����eureka����
start cmd /k "cd/d %dir%/cheetah-module-eureka/target&&java -jar cheetah-module-eureka-1.0.0-SNAPSHOT.jar" 
echo ����config����
start cmd /k "cd/d %dir%/cheetah-module-config/target&&java -jar cheetah-module-config-1.0.0-SNAPSHOT.jar"
echo ����zuul����
start cmd /k "cd/d %dir%/cheetah-module-zuul/target&&java -jar cheetah-module-zuul-1.0.0-SNAPSHOT.jar"
echo ����admin����
start cmd /k "cd/d %dir%/cheetah-module-admin/target&&java -jar cheetah-module-admin-1.0.0-SNAPSHOT.jar"
echo �������
pause
