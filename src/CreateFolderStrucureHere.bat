@echo off
SET mypath=%~dp0
java -cp "%mypath%/ImagesOrganizer.jar" imgorg.bin.CreateFolderStructureHere %1
