@echo off
SET mypath=%~dp0
java -cp %mypath%/ImagesOrganizer.jar imgorg.bins.CreateFolderStructureHere %1
