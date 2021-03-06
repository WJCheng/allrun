package com.tarena.allrun.util;

import android.util.Log;

import com.tarena.allrun.TApplication;

/**
 * 统一处理日志
 */
public class LogUtil {
	public static void i(String tag, Object msg) {
		if (TApplication.isRelease) {
			return;
		}
		Log.i(tag, String.valueOf(msg));
	}

}
