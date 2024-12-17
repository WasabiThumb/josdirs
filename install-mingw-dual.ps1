# Installs mingw64 and mingw32 and adds them to the PATH.
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
        [Environment]::GetEnvironmentVariable("Path", [EnvironmentVariableTarget]::Machine) + ";$To",
        [EnvironmentVariableTarget]::Machine)
}

function Invoke-Main {
    $ProgressPreference = 'SilentlyContinue'

    Write-Output "Downloading mingw64..."
    Invoke-WebRequest "$Download64" -OutFile ".\amd64.7z"
    Write-Output "Extracting mingw64..."
    & "${env:ProgramFiles}\7-Zip\7z.exe" x ".\amd64.7z" "-o.\" -y
    Remove-Item -Path ".\amd64.7z" -Force

    Write-Output "Downloading mingw32..."
    Invoke-WebRequest "$Download32" -OutFile ".\i686.7z"
    Write-Output "Extracting mingw32..."
    & "${env:ProgramFiles}\7-Zip\7z.exe" x ".\i686.7z" "-o.\" -y
    Remove-Item -Path ".\i686.7z" -Force

    $ProgressPreference = 'Continue'

    $dest = "$env:ProgramData\mingw-dual"
    Write-Output "Writing to $dest"
    md -Force $dest | Out-Null
    Move-Item -Path ".\*" -Destination "$dest"

    tree "$dest" /F

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
