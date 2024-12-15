extern crate libc;

use jni::JNIEnv;

use std::mem;
use std::ptr;
use std::ffi::CStr;
use jni::objects::{JObject, JString};

fn unix_uid() -> u32 {
    unsafe {
        libc::getuid()
    }
}

fn unix_home_dir(uid: u32) -> Option<String> {
    unsafe {
        let mut result = ptr::null_mut();
        let amt = match libc::sysconf(libc::_SC_GETPW_R_SIZE_MAX) {
            n if n < 0 => 512usize,
            n => n as usize,
        };
        let mut buf = Vec::with_capacity(amt);
        let mut passwd: libc::passwd = mem::zeroed();

        match libc::getpwuid_r(uid, &mut passwd, buf.as_mut_ptr(),
                               buf.capacity() as libc::size_t,
                               &mut result) {
            0 if !result.is_null() => {
                let ptr = passwd.pw_dir as *const _;
                let home = CStr::from_ptr(ptr).to_str().unwrap().to_owned();
                Some(home)
            },
            _ => None
        }
    }
}

#[no_mangle]
pub extern "system" fn Java_io_github_wasabithumb_josdirs_platform_unix_jni_JNI_homePath(env: JNIEnv) -> JString {
    let option = unix_home_dir(unix_uid());
    if let Some(home) = option {
        return env.new_string(home).unwrap();
    }
    JString::from(JObject::null())
}
