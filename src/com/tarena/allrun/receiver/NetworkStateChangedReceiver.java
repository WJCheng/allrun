package com.tarena.allrun.receiver;

import com.tarena.allrun.TApplication;
import com.tarena.allrun.util.ExceptionUtil;
import com.tarena.allrun.util.Tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStateChangedReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		try {
			ConnectivityManager manager = 
					(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
			
			if (activeNetworkInfo == null) {
				TApplication.networkType = TApplication.NETWORKTYPE_NONE;
				Tools.showToast(context, "�û��ر�������");
			}else{
				NetworkInfo mobileNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if(mobileNetworkInfo != null && mobileNetworkInfo.isConnected()){
					TApplication.networkType = TApplication.NETWORKTYPE_MOBILE;
					Tools.showToast(context, "�û���mobile����");
				}
				
				NetworkInfo wifiNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if(wifiNetworkInfo != null && wifiNetworkInfo.isConnected()){
					TApplication.networkType = TApplication.NETWORKTYPE_WIFI;
					Tools.showToast(context, "�û���wifi����");
				}
			}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
		
	}

}
