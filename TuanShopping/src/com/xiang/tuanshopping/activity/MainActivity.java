package com.xiang.tuanshopping.activity;

import com.xiang.tuanshopping.R;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.TabActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity {

	private TabHost tabhost;
	private TabHost.TabSpec first;
	private TabHost.TabSpec second;
	private TabHost.TabSpec third;
	private TabHost.TabSpec fourth;

	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		init();
		// ������ݷ�ʽ
		// ����Ѿ������������ظ�����

		// creatShortCut();

	}

	// private void creatShortCut() {
	// if(isExit()){
	// return;
	// }
	// Intent intent = new Intent();
	// intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	// //�������ݣ���ݷ�ʽ�����ƣ�ͼ��
	// intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "����");
	// Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
	// R.drawable.ic_launcher);
	// intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
	//
	// //���ͼ�������ĸ����
	// Intent targetIntent = new Intent();
	// targetIntent.setClass(this, MainActivity.class);
	// intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, targetIntent);
	//
	// sendBroadcast(intent);
	//
	// Toast.makeText(this, "��ݷ�ʽ�Ѵ����ɹ�", Toast.LENGTH_LONG).show();
	// }
	//
	// private boolean isExit() {
	// // TODO Auto-generated method stub
	// ContentResolver resolver = getContentResolver();
	// Uri uri =
	// Uri.parse("content://com.android.launcher2.settings/favorites");
	// int sdk = Build.VERSION.SDK_INT;
	// if(sdk < 8){
	// uri = Uri.parse("content://com.android.launcher.settings/favorites");
	// }
	//
	// Cursor c = resolver.query(uri, null, " title = ? ", new String[]{"����"},
	// null);
	// if(c.moveToNext()){
	// return true;
	// }
	// return false;
	// }

	private void init() {
		tabhost = this.getTabHost();

		first = tabhost.newTabSpec("first");
		second = tabhost.newTabSpec("second");
		third = tabhost.newTabSpec("third");
		fourth = tabhost.newTabSpec("fourth");
		// ָ��ѡ������֣�ͼ��
		/*
		 * first.setIndicator(createContent("�Ź�",
		 * getResources().getDrawable(R.drawable.ic_menu_deal_on)));
		 * second.setIndicator(createContent("�̼�",
		 * getResources().getDrawable(R.drawable.ic_menu_poi_off)));
		 * third.setIndicator(createContent("�ҵ�",
		 * getResources().getDrawable(R.drawable.ic_menu_user_off)));
		 * fourth.setIndicator(createContent("����",
		 * getResources().getDrawable(R.drawable.ic_menu_more_off)));
		 */

		first.setIndicator(createContent("�Ź�", R.drawable.first_tab));
		second.setIndicator(createContent("�̼�", R.drawable.two_tab));
		third.setIndicator(createContent("�ҵ�", R.drawable.third_tab));
		fourth.setIndicator(createContent("����", R.drawable.fours_tab));

		// ����ʾ��ҳ��
		// first.setContent(R.id.ll_first);
		first.setContent(new Intent(this, TuangouActivity.class));
		second.setContent(new Intent(this, ShangjiaActivity.class));
		third.setContent(new Intent(this, MineActivity.class));
		fourth.setContent(new Intent(this, GengduoActivity.class));
		// ��ѡ��ӽ�TabHost
		tabhost.addTab(first);
		tabhost.addTab(second);
		tabhost.addTab(third);
		tabhost.addTab(fourth);
		tabhost.setCurrentTab(0);
		// ����tabHost�л�ʱ��̬����ͼ��
		tabhost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				tabChanged(tabId);
			}

		});
	}

	// ����tab�仯�¼�
	private void tabChanged(String tabId) {
		// ��ǰѡ����
		if (tabId.equals("first")) {
			tabhost.setCurrentTabByTag("�Ź�");
		} else if (tabId.equals("second")) {
			tabhost.setCurrentTabByTag("�̼�");
		} else if (tabId.equals("third")) {
			tabhost.setCurrentTabByTag("�ҵ�");
		} else if (tabId.equals("fourth")) {
			tabhost.setCurrentTabByTag("����");
		}

	}

	/*
	 * // ����ѡ��ϵĲ������� private View createContent(String text, Drawable drawable)
	 * { View view = LayoutInflater.from(this).inflate(R.layout.tabwidget, null,
	 * false); ImageView img_name = (ImageView)
	 * view.findViewById(R.id.img_name); TextView tv_name = (TextView)
	 * view.findViewById(R.id.tv_name); img_name.setImageDrawable(drawable);
	 * tv_name.setText(text); return view; }
	 */

	// ���ص���ѡ��
	private View createContent(String text, int resid) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabwidget, null,
				false);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
		ImageView iv_icon = (ImageView) view.findViewById(R.id.img_name);
		tv_name.setText(text);
		iv_icon.setBackgroundResource(resid);
		return view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}

	}
}
