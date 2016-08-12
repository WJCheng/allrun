package com.tarena.allrun.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

public class Tools {

	private static ProgressDialog pDialog = null;
	private static Toast toast = null;
	
	public static void showProgressDialog(Activity activity, String msg){
		
		if(pDialog == null){
			pDialog = new ProgressDialog(activity);
			pDialog.setMessage(msg);
			pDialog.setCanceledOnTouchOutside(false);
			pDialog.show();
		}
	}
	
	public static void closeProgressDialog(){
		if(pDialog != null){
			pDialog.cancel();
			pDialog = null;
			System.gc();
		}
	}
	
	public static void showToast(Context context,String msg){
		
		if (toast == null) {
			toast = Toast.makeText(context, "", Toast.LENGTH_SHORT); 
			toast.setText(msg);
			toast.show();
		}else{
			toast.setText(msg);
			toast.show();
		}
//		toast.cancel();
	}
	public static void cancelToast(){
		
		if (toast != null) {
			toast.cancel();
		}
	}
	
//	public void OnBackPress(){
//		cancelToast
//	}
	
	
	public static void showViewInfo(Context context,String msg){
		Toast toast = new Toast(context);
		View view = new View(context);
		view.setBackgroundColor(Color.RED);
//		toast.setText(msg);
		toast.setView(view);
		toast.show();
	}
}
