package com.xiang.tuanshopping.activity;

import com.xiang.tuanshopping.R;
import com.xiang.tuanshopping.bean.UserInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

/**
 * ͨ���ж��û���Ϣ�Ƿ�Ϊ������ʾ����
 * @author Administrator
 *
 */
public class MineActivity extends Activity {
	private UserInfo userInfo=null;
	//�ҵ�����ȯ
	private LinearLayout ll_mine_ticker;
	//�ղؼ�
	private LinearLayout ll_mine_favorite;
	//ÿ���Ƽ�
	private LinearLayout ll_everyday_recommend;
	//������
	private LinearLayout ll_order_first;
	//�Ѹ���
	private LinearLayout ll_order_second;
	//�齱��
	private LinearLayout ll_order_third;
	//����
	private LinearLayout ll_other;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third_mine);
		
		
	}
	
	/**
	 * ��ʼ���ؼ�
	 */
	public void initControl(){
		
		ll_mine_ticker = (LinearLayout) findViewById(R.id.ll_mine_meituan_ticker);
		ll_mine_favorite = (LinearLayout) findViewById(R.id.ll_mine_favorite);
		
		ll_everyday_recommend = (LinearLayout) findViewById(R.id.ll_everyday_recommend);
		ll_order_first = (LinearLayout) findViewById(R.id.ll_order_first);
		ll_order_second = (LinearLayout) findViewById(R.id.ll_order_second);
		ll_order_third = (LinearLayout) findViewById(R.id.ll_order_third);
		
		ll_other = (LinearLayout) findViewById(R.id.ll_other);
	}
	
	/**
	 * Ϊ���ؼ����ü���
	 */
	public void setLisentener(){
		//1���ҵ�����ȯ
		ll_mine_ticker.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//�ж��Ƿ��¼��δ��¼����ת����¼ҳ��
				if(userInfo==null){
					Intent intent=new Intent(MineActivity.this,LoginActivity.class);
					startActivity(intent);
				}else{//���ѵ�¼����ת���ҵ�����ȯҳ��
					
				}
			}
		});
		//2���ղؼ�
		ll_mine_favorite.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// ��ת���ղ�ҳ��
				
			}
		});
		//3��ÿ���Ƽ�
		ll_everyday_recommend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ��ת��ÿ���Ƽ�ҳ
				
			}
		});
		//4��������
		ll_order_first.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		//5���Ѹ���
	}
	
	/**
	 * ���Ͻǵĵ���¼�
	 * ��δ��½����ת����½����
	 * ���ѵ�½����ת��֪ͨ����
	 */
	public void inform(View btn){
		
	}
	
	/**
	 * ��¼
	 */
	public void onLogin(View btn){
		//��ת����¼ҳ��
		Intent intent=new Intent(this,LoginActivity.class);
		startActivity(intent);
	}
}
