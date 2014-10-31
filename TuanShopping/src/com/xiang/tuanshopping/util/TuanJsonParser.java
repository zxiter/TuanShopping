package com.xiang.tuanshopping.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiang.tuanshopping.application.DataApplication;
import com.xiang.tuanshopping.bean.Merchs;

public class TuanJsonParser {
	
	/**
	 * ��ȡ������Json���ݣ�תΪMeiTuanBean���󼯺�
	 * @param uri
	 * @return
	 */
	public static List<Merchs> parse(String uri) {
		List<Merchs> list = new ArrayList<Merchs>();
		try {
			String result = HttpUtils.download(uri);
			Log.d("geek", result);
			// ��json���飬ת���ɼ���
			// 1.���ַ���ת��JSONArray
			JSONArray array = new JSONArray(result);
			Log.d("geek", "����������"+array.length());
			for (int i = 0; i < array.length(); i++) {
				// 2.�õ�JSONArray�еĵ���JSONObject
				JSONObject jo = (JSONObject) array.get(i);
				Log.d("geek", "����json����"+jo.toString());
				// 3.�õ�����JSONObject�е�����
				Merchs merchs = new Merchs(jo.getInt("merchsid"),
						jo.getString("loc"),jo.getString("image"),
						jo.getString("range"),jo.getInt("category"),
						jo.getString("shorttitle"),jo.getString("describe"),
						jo.getString("price"),jo.getString("value"),
						jo.getString("bought"),jo.getString("grade"),
						jo.getString("gradecount"),jo.getString("date"),
						jo.getString("city"));
				Log.d("geek","������Ʒ��Ϣ:"+merchs.toString());
				list.add(merchs);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	

}
