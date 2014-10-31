package com.xiang.tuanshopping.activity;

import java.util.HashMap;
import java.util.Map;

import com.xiang.tuanshopping.R;
import com.xiang.tuanshopping.util.UserUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static final String path = "http://192.168.1.66:8080/TuanShoppingServer/mypack/userAction_login";
	private EditText et_username;
	private EditText et_userpwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		et_username = (EditText) findViewById(R.id.et_username);
		et_userpwd = (EditText) findViewById(R.id.et_userpwd);
		this.changeBack();
	}

	/**
	 * �ڻ�ý���ʱ�ı�༭�򱳾�
	 * 
	 * @param btn
	 */
	public void changeBack() {
		et_username.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					et_username
							.setBackgroundResource(R.drawable.bg_edit_selected);
				} else {
					et_username
							.setBackgroundResource(R.drawable.bg_edit_unselected);
				}

			}
		});
		et_userpwd.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					et_userpwd
							.setBackgroundResource(R.drawable.bg_edit_selected);
				} else {
					et_userpwd
							.setBackgroundResource(R.drawable.bg_edit_unselected);
				}
			}
		});
	}

	// �жϵ�¼�Ƿ�ɹ�
	public void onLogin(View view) {
		if (view.getId() == R.id.btn_login) {
			Log.d("geek", "��¼");
			// �õ�����༭�������
			String name = et_username.getText().toString();
			String pwd = et_userpwd.getText().toString();
			Log.d("geek", "�û�����" + name + "���룺" + pwd);
			Map<String, String> data = new HashMap<String, String>();
			data.put("user.username", name);
			data.put("user.userpwd", pwd);
			Log.d("geek", "data=" + data);
			String result = "failure";
			result = UserUtils.loginResult(path, data);
			Log.d("geek", "���ؽ����" + result);
			if (name == null || (name == null && pwd == null)) {
				// �����Ի������ݣ���������Email���ֻ��š���
				Toast.makeText(this, "������", Toast.LENGTH_LONG).show();
			} else if (pwd == null) {
				// �����Ի���(���ݣ�"����������")
				Toast.makeText(this, "������", Toast.LENGTH_LONG).show();
			} else {
				if (result.equals("success")) {// ��¼�ɹ�,��ת��
					Toast.makeText(this, result, Toast.LENGTH_LONG).show();
					Intent intent = new Intent(this,MineActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(this, "��¼ʧ��", Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	public void onReturnmine(View btn) {
		if (btn.getId() == R.id.ib_return) {
			Intent intent = new Intent(this, MineActivity.class);
			startActivity(intent);
		}
	}
}
