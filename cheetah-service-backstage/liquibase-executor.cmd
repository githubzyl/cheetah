@echo off
set dir=%~dp0
%dir:~0,2%
cd "%dir%"
call mvn liquibase:update
pause

