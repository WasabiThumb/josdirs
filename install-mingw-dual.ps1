# Installs mingw64 and mingw32 for the current user, and adds them to the PATH.
# Created with <3 by Wasabi

# Update these links here: https://github.com/niXman/mingw-builds-binaries/releases/latest
$Download64 = "https://github.com/niXman/mingw-builds-binaries/releases/download/13.2.0-rt_v11-rev0/x86_64-13.2.0-release-posix-seh-ucrt-rt_v11-rev0.7z"
$Download32 = "https://github.com/niXman/mingw-builds-binaries/releases/download/13.2.0-rt_v11-rev0/i686-13.2.0-release-posix-dwarf-ucrt-rt_v11-rev0.7z"

function Add-PathEntry {
    param (
        $To
    )
    $env:Path += ";$To"
    [Environment]::SetEnvironmentVariable(
        "Path",
        [Environment]::GetEnvironmentVariable("Path", [EnvironmentVariableTarget]::User) + ";$To",
        [EnvironmentVariableTarget]::User)
}

function Invoke-Main {
    $ProgressPreference = 'SilentlyContinue'

    Write-Output "Initializing 7-Zip Module..."
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    Register-PackageSource -provider NuGet -name nugetRepository -location https://www.nuget.org/api/v2
    Install-PackageProvider -Name NuGet -MinimumVersion 2.8.5.201 -Force -Scope CurrentUser
    Set-PSRepository -Name 'PSGallery' -SourceLocation "https://www.powershellgallery.com/api/v2" -InstallationPolicy Trusted
    Install-Module -Name 7Zip4PowerShell -Force -Scope CurrentUser

    Write-Output "Downloading mingw64..."
    Invoke-WebRequest "$Download64" -OutFile ".\amd64.7z"
    Write-Output "Extracting mingw64..."
    Expand-7Zip -ArchiveFileName "amd64.7z" -TargetPath ".\"
    Remove-Item -Path ".\amd64.7z" -Force

    Write-Output "Downloading mingw32..."
    Invoke-WebRequest "$Download32" -OutFile ".\i686.7z"
    Write-Output "Extracting mingw32..."
    Expand-7Zip -ArchiveFileName "i686.7z" -TargetPath ".\"
    Remove-Item -Path ".\i686.7z" -Force

    $ProgressPreference = 'Continue'

    $dest = "$env:APPDATA\mingw-dual"
    Write-Output "Writing to $dest"
    md -Force $dest | Out-Null
    Move-Item -Path ".\*" -Destination "$dest"

    Write-Output "Adding mingw64 to PATH..."
    Add-PathEntry -To "$dest\mingw64\bin"
    Write-Output "Adding mingw32 to PATH..."
    Add-PathEntry -To "$dest\mingw32\bin"
    Write-Output "Done!"
}

$origin = (Get-Item .).FullName
$work = Join-Path $Env:Temp $(New-Guid)
New-Item -Type Directory -Path $work | Out-Null
try {
    cd "$work"
    Invoke-Main
} finally {
    cd "$origin"
    Remove-Item -LiteralPath "$work" -Force -Recurse
}
