;This file will be executed next to the application bundle image
;I.e. current directory will contain folder Piletikassa with application files
[Setup]
AppId={{appRef}}
AppName=Piletikassa
AppVersion=1.0
AppVerName=Piletikassa 1.0
AppPublisher=Urbanteam
AppComments=Piletikassa
AppCopyright=Copyright (C) 2016
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={pf}\Piletikassa
DisableStartupPrompt=Yes
DisableDirPage=No
DisableProgramGroupPage=Yes
DisableReadyPage=No
DisableFinishedPage=No
DisableWelcomePage=No
DefaultGroupName=Piletikassa
;Optional License
LicenseFile=C:\Users\Kristjan\OneDrive\scripts\java\piletisystem2\package\windows\License.txt
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=Piletikassa-1.0
Compression=lzma
SolidCompression=yes
PrivilegesRequired=admin
SetupIconFile=Piletikassa\Piletikassa.ico
UninstallDisplayIcon={app}\Piletikassa.ico
UninstallDisplayName=Piletikassa
WizardImageStretch=No
WizardSmallImageFile=Piletikassa-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=x64


[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "Piletikassa\Piletikassa.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "Piletikassa\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\Piletikassa"; Filename: "{app}\Piletikassa.exe"; IconFilename: "{app}\Piletikassa.ico"; Check: returnTrue()
Name: "{commondesktop}\Piletikassa"; Filename: "{app}\Piletikassa.exe";  IconFilename: "{app}\Piletikassa.ico"; Check: returnTrue()


[Run]
Filename: "{app}\Piletikassa.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\Piletikassa.exe"; Description: "{cm:LaunchProgram,Piletikassa}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\Piletikassa.exe"; Parameters: "-install -svcName ""Piletikassa"" -svcDesc ""Piletikassa"" -mainExe ""Piletikassa.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\Piletikassa.exe "; Parameters: "-uninstall -svcName Piletikassa -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
