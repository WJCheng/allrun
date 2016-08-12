package com.tarena.allrun.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class CommUtils {
	/**
	 * 与服务器通信，需要的MD5 加密认证方法
	 */

	static public long getTimeStamp() {
		Date nowDate = new Date();
		return nowDate.getTime();
	}

	static public String getTimeStampMD5(long longStamp) {

		String selfSN = MD5Util.encodeString(Long
				.toString((longStamp + 199101)));
	

		return selfSN;
	}
	
	static public String getTimeStampMD5Temp(long longStamp) {

		String selfSN = MD5Util.encodeString(Long
				.toString((longStamp + 0)));

		return selfSN;
	}

}
