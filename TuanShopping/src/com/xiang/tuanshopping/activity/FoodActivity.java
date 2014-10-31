package com.xiang.tuanshopping.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.xiang.tuanshopping.R;
import com.xiang.tuanshopping.adapter.CategoryListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class FoodActivity extends Activity {

	private LinearLayout public_menu;
	private ArrayList<HashMap<String, Object>> itemList;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private LinearLayout linLayout;
	private LinearLayout layout;
	private ListView rootList;
	private String title[] = { "ȫ������", "��ʳ", "�Ƶ�", "��Ӱ", "��������", "�������", "����" };
	private FrameLayout flChild;
	private ListView childList;
	private PopupWindow mPopWin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food);
		
		LinearLayout ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FoodActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
		
		
		initPopupWindow();
	}

	private void initPopupWindow() {
		public_menu = (LinearLayout) findViewById(R.id.public_menu);
		itemList = new ArrayList<HashMap<String, Object>>();
		text1 = (TextView) public_menu.findViewById(R.id.text1);
		text2 = (TextView) public_menu.findViewById(R.id.text2);
		text3 = (TextView) public_menu.findViewById(R.id.text3);
		linLayout = (LinearLayout) findViewById(R.id.food);

		text1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopupWindow(linLayout.getWidth(), linLayout.getHeight());
			}
		});

		text2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopupWindow(linLayout.getWidth(), linLayout.getHeight());
			}
		});

		text3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopupWindow(linLayout.getWidth(), linLayout.getHeight());
			}
		});
	}

	private void showPopupWindow(int width, int height) {

		itemList = new ArrayList<HashMap<String, Object>>();
		layout = (LinearLayout) LayoutInflater.from(FoodActivity.this).inflate(
				R.layout.popup_category, null);
		rootList = (ListView) layout.findViewById(R.id.rootcategory);
		for (int i = 0; i < title.length; i++) {
			HashMap<String, Object> items = new HashMap<String, Object>();
			items.put("name", title[i]);
			items.put("count", i * 5);
			itemList.add(items);
		}

		CategoryListAdapter cla = new CategoryListAdapter(FoodActivity.this,
				itemList);
		rootList.setAdapter(cla);

		flChild = (FrameLayout) layout.findViewById(R.id.child_lay);
		childList = (ListView) layout.findViewById(R.id.childcategory);
		childList.setAdapter(cla);
		flChild.setVisibility(View.INVISIBLE);

		mPopWin = new PopupWindow(layout, width * 9 / 10, height / 2, true);
		mPopWin.setBackgroundDrawable(new BitmapDrawable());
		mPopWin.showAsDropDown(text1, 5, 1);
		mPopWin.update();

		rootList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				flChild.setVisibility(View.VISIBLE);
				childList
						.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								layout.setVisibility(View.GONE);
							}
						});
			}
		});
	}

}
