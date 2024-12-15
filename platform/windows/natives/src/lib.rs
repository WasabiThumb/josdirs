use jni::JNIEnv;
use jni::objects::{JObject, JString};
use windows_sys::{
    core::*,
    Win32::Foundation::*,
    Win32::UI::Shell::*,
    Win32::System::Com::CoTaskMemFree
};

/*
fn get_folder_path(id: u32) -> Option<String> {
    let mut buf: Vec<u16> = vec![0u16; MAX_PATH as usize];
    let buf_ptr = buf.as_mut_ptr();
    let result: i32 = unsafe {
        SHGetFolderPathW(
            0usize as HWND,
            id as i32,
            0usize as HANDLE,
            SHGFP_TYPE_CURRENT as u32,
            buf_ptr
        )
    };
    if result != S_OK {
        return None;
    }
    let slice = unsafe {
        let mut len: usize = 0;
        for i in 0..MAX_PATH as usize {
            if *buf_ptr.wrapping_add(i) == 0u16 {
                len = i;
                break;
            }
        }
        std::slice::from_raw_parts(buf_ptr, len)
    };
    Some(String::from_utf16_lossy(slice))
}
 */

fn get_known_folder_path(id: GUID) -> Option<String> {
    let mut out: *mut u16 = std::ptr::null_mut();
    let result = unsafe {
        SHGetKnownFolderPath(
            &id,
            KF_FLAG_DONT_VERIFY as u32,
            0usize as HANDLE,
            &mut out
        )
    };
    if result != S_OK {
        unsafe {
            CoTaskMemFree(out as *const _);
        }
        return None;
    }
    let slice = unsafe {
        let mut len: usize = 0;
        loop {
            if *out.wrapping_add(len) == 0u16 {
                break;
            }
            len += 1;
        }
        std::slice::from_raw_parts(out, len)
    };
    let str = String::from_utf16_lossy(slice);
    unsafe {
        CoTaskMemFree(out as *const _);
    }
    Some(str)
}

fn opt_str_to_java(env: JNIEnv, str: Option<String>) -> JString {
    if let Some(s) = str {
        return env.new_string(s).unwrap()
    }
    JString::from(JObject::null())
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_windows_jni_JNI_homeDir(env: JNIEnv) -> JString {
    opt_str_to_java(env, get_known_folder_path(FOLDERID_Profile))
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_windows_jni_JNI_localAppDataDir(env: JNIEnv) -> JString {
    opt_str_to_java(env, get_known_folder_path(FOLDERID_LocalAppData))
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_windows_jni_JNI_roamingAppDataDir(env: JNIEnv) -> JString {
    opt_str_to_java(env, get_known_folder_path(FOLDERID_RoamingAppData))
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_windows_jni_JNI_desktopDir(env: JNIEnv) -> JString {
    opt_str_to_java(env, get_known_folder_path(FOLDERID_Desktop))
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_windows_jni_JNI_documentsDir(env: JNIEnv) -> JString {
    opt_str_to_java(env, get_known_folder_path(FOLDERID_Documents))
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_windows_jni_JNI_downloadsDir(env: JNIEnv) -> JString {
    opt_str_to_java(env, get_known_folder_path(FOLDERID_Downloads))
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_windows_jni_JNI_musicDir(env: JNIEnv) -> JString {
    opt_str_to_java(env, get_known_folder_path(FOLDERID_Music))
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_windows_jni_JNI_picturesDir(env: JNIEnv) -> JString {
    opt_str_to_java(env, get_known_folder_path(FOLDERID_Pictures))
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_windows_jni_JNI_videosDir(env: JNIEnv) -> JString {
    opt_str_to_java(env, get_known_folder_path(FOLDERID_Videos))
}
