@echo off
set dir=%~dp0
%dir:~0,2%
echo 开始打包
cd %dir%
call mvn clean package -DskipTests
echo 打包完毕
echo 启动eureka服务
start cmd /k "cd/d %dir%/cheetah-module-eureka/target&&java -jar cheetah-module-eureka-1.0.0-SNAPSHOT.jar" 
echo 启动config服务
start cmd /k "cd/d %dir%/cheetah-module-config/target&&java -jar cheetah-module-config-1.0.0-SNAPSHOT.jar"
echo 启动zuul服务
start cmd /k "cd/d %dir%/cheetah-module-zuul/target&&java -jar cheetah-module-zuul-1.0.0-SNAPSHOT.jar"
echo 启动admin服务
start cmd /k "cd/d %dir%/cheetah-module-admin/target&&java -jar cheetah-module-admin-1.0.0-SNAPSHOT.jar"
echo 运行完毕
pause
