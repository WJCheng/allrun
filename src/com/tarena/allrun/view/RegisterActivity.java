package com.tarena.allrun.view;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jivesoftware.smack.AccountManager;

import com.tarena.allrun.R;
import com.tarena.allrun.R.id;
import com.tarena.allrun.R.layout;
import com.tarena.allrun.R.menu;
import com.tarena.allrun.biz.RegistBiz;
import com.tarena.allrun.entity.UserEntity;
import com.tarena.allrun.util.Const;
import com.tarena.allrun.util.ImageCompress;
import com.tarena.allrun.util.ImageCompress.CompressOptions;
import com.tarena.allrun.util.Tools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{

	private EditText etUser;
	private EditText etPwd;
	private EditText etConfirm;
	private EditText etName;
	private Button btRegist;
	private MyReceiver receiver;
	private IntentFilter filter;
	private ImageView ivPic;
	private Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		setViews();
		setListeners();
		
		receiver = new MyReceiver();
		filter = new IntentFilter();
		filter.addAction(Const.ACTION_REGISTER);
		this.registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.unregisterReceiver(receiver);
	}
	
	private void setViews() {
		// TODO Auto-generated method stub
		etUser = (EditText)this.findViewById(R.id.et_regist_user);
		etPwd = (EditText)this.findViewById(R.id.et_regist_password);
		etConfirm = (EditText)this.findViewById(R.id.et_regist_confirm_password);
		etName = (EditText)this.findViewById(R.id.et_regist_name);
		
		btRegist = (Button)this.findViewById(R.id.btRegist);
		ivPic = (ImageView)this.findViewById(R.id.ivPic);
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		btRegist.setOnClickListener(this);
		ivPic.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubr
		switch(v.getId()){
		case R.id.btRegist:
			sendRegistInfo();
			break;
		case R.id.ivPic:
			setPic();
			break;
		}

	}

	private void setPic() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 200);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 200) {
			
//			bitmap = MediaStore.Images.Media.getBitmap(
			//					getContentResolver(), data.getData());
			ImageCompress.CompressOptions options = new ImageCompress.CompressOptions();
			options.maxWidth = 40;
			options.maxHeight = 40;
			options.uri = data.getData();
			
			ImageCompress compress = new ImageCompress();
			bitmap = compress.compressFromUri(this, options);
			
			ivPic.setImageBitmap(bitmap);
		}
	}

	private void sendRegistInfo() {

		btRegist.setEnabled(false);
		Tools.showProgressDialog(this, "正在注册中...");
		
		UserEntity user = new UserEntity();
		if(		etUser.getText().toString().equals("") ||
				etPwd.getText().toString().equals("")||
				etConfirm.getText().toString().equals("")||
				etName.getText().toString().equals("")){
			Toast.makeText(this, "输入格式错误", Toast.LENGTH_SHORT).show();
			return ;
		}
		user.setUser(etUser.getText().toString());
		user.setPwd(etPwd.getText().toString());
		user.setcPwd(etConfirm.getText().toString());
		user.setName(etName.getText().toString());

		Intent intent = new Intent(RegisterActivity.this, RegistBiz.class);
//		intent.setAction(Const.ACTION_REGISTER);
		intent.putExtra(Const.KEY_DATA, user);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] imageData = byteArrayOutputStream.toByteArray();
		intent.putExtra("imageData", imageData);
		
		this.startService(intent);
	}

	class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (!intent.getAction().equals(Const.ACTION_REGISTER)) {
				return;
			}
			int status = intent.getIntExtra(Const.KEY_DATA, -1);
			if (status == Const.STATUS_OK) {
				Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
			}
			if(status == Const.STATUS_REGISTER_FAILURE){
				Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
				btRegist.setEnabled(true);
			}
			Tools.closeProgressDialog();
		}
		
	}
}
