/*

   Copyright 2024 Wasabi Codes

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

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
