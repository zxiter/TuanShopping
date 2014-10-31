package com.xiang.tuanshopping.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * ��ȡ��¼ע����
 * @author Administrator
 */
public class UserUtils {
	/**
	 * ��ȡ��¼���
	 * @param path
	 * @param data:Ҫ���Ĳ���
	 * @return
	 */
	public static String loginResult(String path,Map<String,String> data){
		String result=null;
		try {
			HttpClient client = new DefaultHttpClient();
			//������ת��ʽ
			HttpPost post = new HttpPost(path);			
			//�ύ�Ĳ���
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for(Entry<String, String> entry:data.entrySet()){
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			
			//Ҫ�ύ��ʵ��
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
			post.setEntity(entity);//�ύ����
			
			//ִ��post���󣬻�ȡ��������Ӧ
			HttpResponse response = client.execute(post);
			//�ж�״̬
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//��ȡ��������Ӧ����
				//����Ӧ��ʵ�壬ת��Ϊ�ַ���
				result = EntityUtils.toString(response.getEntity());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	
	public static String registerResult(String path,Map<String,String> data){
		String result=null;
		try {
			HttpClient client=new DefaultHttpClient();
			//������ת��ʽ
			HttpPost post=new HttpPost(path);
			//�ύ�Ĳ���
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for(Entry<String, String> entry:data.entrySet()){
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			//Ҫ�ύ��ʵ��
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
			post.setEntity(entity);//�ύ����
			
			//ִ��post���󣬻�ȡ��������Ӧ
			HttpResponse response = client.execute(post);
			//�ж�״̬
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Log.d("geek","��Ӧ���");
				//��ȡ��������Ӧ����
				//����Ӧ��ʵ�壬ת��Ϊ�ַ���
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
}
