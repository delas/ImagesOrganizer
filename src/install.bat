@echo off
set /p id="Enter complete path of CreateFolderStrucureHere.bat: "
reg add "HKEY_CURRENT_USER\Software\Classes\Directory\Background\shell\ImagesOrganizer - Create tree structure here\command" /d "%id% ""%%V"""
