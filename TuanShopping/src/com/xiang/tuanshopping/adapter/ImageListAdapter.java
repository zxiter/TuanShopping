package com.xiang.tuanshopping.adapter;

import java.util.List;

import com.ab.global.AbConstant;
import com.ab.net.AbImageDownloadCallback;
import com.ab.net.AbImageDownloadItem;
import com.ab.net.AbImageDownloadQueue;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.xiang.tuanshopping.R;
import com.xiang.tuanshopping.bean.Merchs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageListAdapter extends BaseAdapter {
	private Context context;
	//xmlתView����
    private LayoutInflater mInflater;
    //�б�չ�ֵ�����
    private List<Merchs> mData;
    private AbImageDownloadQueue mAbImageDownloadQueue = null;
    
    public ImageListAdapter(Context context,List<Merchs> mData){
    	this.context=context;
    	this.mData=mData;
    	 //���ڽ�xmlתΪView
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mAbImageDownloadQueue = AbImageDownloadQueue.getInstance();
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		//�Ż�listview
		if(convertView==null){
			//ʹ���Զ���Ĳ���
			convertView=mInflater.inflate(R.layout.first_item,parent, false);
			holder=new ViewHolder();
			//��ʼ�������е�Ԫ��
			holder.bought=(TextView) convertView.findViewById(R.id.tv_bought);
			holder.itemsIcon=(ImageView) convertView.findViewById(R.id.itemsIcon);
			holder.name=(TextView) convertView.findViewById(R.id.tv_merchs_name);
			holder.price=(TextView) convertView.findViewById(R.id.tv_price);
			holder.range=(TextView) convertView.findViewById(R.id.tv_range);
			holder.shortTitle=(TextView) convertView.findViewById(R.id.tv_shorttitle);
			holder.value=(TextView) convertView.findViewById(R.id.tv_value);
			
			convertView.setTag(holder);
		}else{
			 holder = (ViewHolder) convertView.getTag();
		}
		//��ȡ���е�����
		final Merchs merch=(Merchs) mData.get(position);
		//�õ�ͼƬ·��
		 String imageUrl =merch.getImage();
		 //������������
		 //����
		 holder.bought.setText("����"+merch.getBought());
		 holder.name.setText(merch.getDescribe());
		 //�۸�
		 holder.price.setText(merch.getPrice()+"Ԫ");
		 holder.price.setTextColor(Color.GREEN);
		 holder.price.setTextSize(16);
		 //����
		 holder.range.setText("��"+merch.getRange()+"��");
		 //�̱���
		 holder.shortTitle.setText(merch.getShorttitle());
		 //ԭ��
		 holder.value.setText("ԭ��"+merch.getValue());
		 holder.value.setTextSize(10);
		 holder.value.setTextColor(Color.GRAY);
		 holder.value.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//�м����
		 holder.value.getPaint().setAntiAlias(true);// �����
		 
		 //�첽����ͼƬ
		 if(!AbStrUtil.isEmpty(imageUrl)){
			//���������� 
             AbImageDownloadItem item = new AbImageDownloadItem(); 
   	      //������ʾ�Ĵ�С
   	      item.width = 120;
   	      item.height = 120;
   	      //����Ϊ����
   	      item.type = AbConstant.SCALEIMG;
   	      item.imageUrl = imageUrl;
   	     
             holder.itemsIcon.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_loading.png"));
   	      //������ɺ���½���
   	      item.callback = new AbImageDownloadCallback() { 
   	            @Override 
   	            public void update(Bitmap bitmap, String imageUrl) { 
   	            	if(bitmap!=null){
   	            		holder.itemsIcon.setImageBitmap(bitmap); 
   	            	}else{
   	            	    holder.itemsIcon.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_error.png"));
   	            	}
   	            } 
   	      }; 
   	      mAbImageDownloadQueue.download(item); 
         }else{
       	  holder.itemsIcon.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_no.png"));
         }
		return convertView;
	}

	/**
	 * �����е�Ԫ��
	 */
	static class ViewHolder{
		ImageView itemsIcon;
		TextView shortTitle;
		TextView range;
		TextView name;
		TextView price;
		TextView value;
		TextView bought;
	}
}
