package com.tarena.allrun.util;

import com.tarena.allrun.view.LoginActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.sax.StartElementListener;
import android.widget.Toast;

public class NetworkUtil {

	
	public static void checkNetworkState(final Context context){
		
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activityNetworkInfo = manager.getActiveNetworkInfo();
		try {
			if (activityNetworkInfo == null) {
				AlertDialog.Builder builder = new Builder(context);
				builder.setMessage("网络已经关闭.");
				
				builder.setPositiveButton("打开", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						try {
							int version = android.os.Build.VERSION.SDK_INT;
							if(version > 10){
								Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								context.startActivity(intent);
								dialog.cancel();
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				});
				
				builder.setNegativeButton("取消", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
				builder.show();
			}else{
				Tools.showToast(context, "网络已连接");
			}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}

	}
}
