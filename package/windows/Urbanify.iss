;This file will be executed next to the application bundle image
;I.e. current directory will contain folder Urbanify with application files
[Setup]
AppId={{appRef}}
AppName=Urbanify
AppVersion=1.0
AppVerName=Urbanify 1.0
AppPublisher=Urbanteam
AppComments=Urbanify
AppCopyright=Copyright (C) 2016
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={pf}\Urbanify
DisableStartupPrompt=Yes
DisableDirPage=No
DisableProgramGroupPage=Yes
DisableReadyPage=No
DisableFinishedPage=No
DisableWelcomePage=No
DefaultGroupName=Urbanteam
;Optional License
LicenseFile=C:\Users\Kristjan\OneDrive\scripts\java\piletisystem2\package\windows\License.txt
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=Urbanify-1.0
Compression=lzma
SolidCompression=yes
PrivilegesRequired=admin
SetupIconFile=Urbanify\Urbanify.ico
UninstallDisplayIcon={app}\Urbanify.ico
UninstallDisplayName=Urbanify
WizardImageStretch=No
WizardSmallImageFile=Urbanify-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=x64


[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "Urbanify\Urbanify.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "Urbanify\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\Urbanify"; Filename: "{app}\Urbanify.exe"; IconFilename: "{app}\Urbanify.ico"; Check: returnTrue()
Name: "{commondesktop}\Urbanify"; Filename: "{app}\Urbanify.exe";  IconFilename: "{app}\Urbanify.ico"; Check: returnTrue()


[Run]
Filename: "{app}\Urbanify.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\Urbanify.exe"; Description: "{cm:LaunchProgram,Urbanify}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\Urbanify.exe"; Parameters: "-install -svcName ""Urbanify"" -svcDesc ""Urbanify"" -mainExe ""Urbanify.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\Urbanify.exe "; Parameters: "-uninstall -svcName Urbanify -stopOnUninstall"; Check: returnFalse()

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
