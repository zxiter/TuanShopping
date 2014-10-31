package com.xiang.tuanshopping.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.xiang.tuanshopping.R;
import com.xiang.tuanshopping.application.DemoApplication;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class MapActivity extends Activity {

	final static String TAG = "MapActivity";
	//�������
	MKSearch mSearch = null;	// ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	/**
	 *  MapView �ǵ�ͼ���ؼ�
	 */
	private MapView mMapView = null;
	/**
	 *  ��MapController��ɵ�ͼ���� 
	 */
	private MapController mMapController = null;
	/**
	 *  MKMapViewListener ���ڴ����ͼ�¼��ص�
	 */
	MKMapViewListener mMapListener = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * ʹ�õ�ͼsdkǰ���ȳ�ʼ��BMapManager.
         * BMapManager��ȫ�ֵģ���Ϊ���MapView���ã�����Ҫ��ͼģ�鴴��ǰ������
         * ���ڵ�ͼ��ͼģ�����ٺ����٣�ֻҪ���е�ͼģ����ʹ�ã�BMapManager�Ͳ�Ӧ������
         */
        DemoApplication app = (DemoApplication)this.getApplication();
        
        //----------------ע�ᶨλ����
        LocationClient mLocClient = app.mLocationClient;
        mLocClient.registerLocationListener(new BDLocationListener() {
			
			@Override
			public void onReceivePoi(BDLocation arg0) {
			}
			
			@Override
			public void onReceiveLocation(BDLocation location) {
				float lat = (float)location.getLatitude();
				float lon = (float)location.getLongitude();
				Log.d("geek", String.valueOf(lat));
				Log.d("geek", String.valueOf(lon));
				GeoPoint ptCenter = new GeoPoint((int)(lat*1e6), (int)(lon*1e6));
				//��Geo����
				mSearch.reverseGeocode(ptCenter);
			}
		});
        
        //-----------------��ʼ��λ
        Vibrator mVibrator01 =(Vibrator)app.getSystemService(Service.VIBRATOR_SERVICE);
        app.mVibrator01 = mVibrator01;
		
	    LocationClientOption option = new LocationClientOption();
	    option.setOpenGps(true);
	    option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ
	    option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
	    option.setServiceName("com.baidu.location.service_v2.9");
	    option.disableCache(true);	
	    mLocClient.setLocOption(option);
	    //��ʼ
	    mLocClient.start();
		//����λ
		mLocClient.requestLocation();
        
        
        
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(this);
            /**
             * ���BMapManagerû�г�ʼ�����ʼ��BMapManager
             */
            app.mBMapManager.init(DemoApplication.strKey,new DemoApplication.MyGeneralListener());
        }
        /**
          * ����MapView��setContentView()�г�ʼ��,��������Ҫ��BMapManager��ʼ��֮��
          */
        setContentView(R.layout.map);
        mMapView = (MapView)findViewById(R.id.bmapView);
        /**
         * ��ȡ��ͼ������
         */
        mMapController = mMapView.getController();
        /**
         *  ���õ�ͼ�Ƿ���Ӧ����¼�  .
         */
        mMapController.enableClick(true);
        /**
         * ���õ�ͼ���ż���
         */
        mMapController.setZoom(12);
       
        /**
         * ����ͼ�ƶ���ָ����
         * ʹ�ðٶȾ�γ�����꣬����ͨ��http://api.map.baidu.com/lbsapi/getpoint/index.html��ѯ��������
         * �����Ҫ�ڰٶȵ�ͼ����ʾʹ����������ϵͳ��λ�ã��뷢�ʼ���mapapi@baidu.com��������ת���ӿ�
         */
        GeoPoint p ;
        double cLat = 39.945 ;
        double cLon = 116.404 ;
        Intent  intent = getIntent();
        if ( intent.hasExtra("x") && intent.hasExtra("y") ){
        	//����intent����ʱ���������ĵ�Ϊָ����
        	Bundle b = intent.getExtras();
        	p = new GeoPoint(b.getInt("y"), b.getInt("x"));
        }else{
        	//�������ĵ�Ϊ�찲��
        	 p = new GeoPoint((int)(cLat * 1E6), (int)(cLon * 1E6));
        }
        
        mMapController.setCenter(p);
        
        /**
    	 *  MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
    	 */
        mMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
				/**
				 * �ڴ˴����ͼ�ƶ���ɻص�
				 * ���ţ�ƽ�ƵȲ�����ɺ󣬴˻ص�������
				 */
			}
			
			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * �ڴ˴����ͼpoi����¼�
				 * ��ʾ��ͼpoi���Ʋ��ƶ����õ�
				 * ���ù��� mMapController.enableClick(true); ʱ���˻ص����ܱ�����
				 * 
				 */
				String title = "";
				if (mapPoiInfo != null){
					title = mapPoiInfo.strText;
					Toast.makeText(MapActivity.this,title,Toast.LENGTH_SHORT).show();
					mMapController.animateTo(mapPoiInfo.geoPt);
				}
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
				/**
				 *  �����ù� mMapView.getCurrentMap()�󣬴˻ص��ᱻ����
				 *  ���ڴ˱����ͼ���洢�豸
				 */
			}

			@Override
			public void onMapAnimationFinish() {
				/**
				 *  ��ͼ��ɴ������Ĳ�������: animationTo()���󣬴˻ص�������
				 */
			}
            /**
             * �ڴ˴����ͼ������¼� 
             */
			@Override
			public void onMapLoadFinish() {
				Toast.makeText(MapActivity.this, 
						       "��ͼ�������", 
						       Toast.LENGTH_SHORT).show();
				
			}
		};
		mMapView.regMapViewListener(DemoApplication.getInstance().mBMapManager, mMapListener);
		
		
		
		// ��ʼ������ģ�飬ע���¼�����
        mSearch = new MKSearch();
        mSearch.init(app.mBMapManager, new MKSearchListener() {
            @Override
            public void onGetPoiDetailSearchResult(int type, int error) {
            }
            
			public void onGetAddrResult(MKAddrInfo res, int error) {
				if (error != 0) {
					String str = String.format("����ţ�%d", error);
					Toast.makeText(MapActivity.this, str, Toast.LENGTH_LONG).show();
					return;
				}
				//��ͼ�ƶ����õ�
				mMapView.getController().animateTo(res.geoPt);	
				if (res.type == MKAddrInfo.MK_GEOCODE){
					//������룺ͨ����ַ���������
					String strInfo = String.format("γ�ȣ�%f ���ȣ�%f", res.geoPt.getLatitudeE6()/1e6, res.geoPt.getLongitudeE6()/1e6);
					Toast.makeText(MapActivity.this, strInfo, Toast.LENGTH_LONG).show();
				}
				if (res.type == MKAddrInfo.MK_REVERSEGEOCODE){
					//��������룺ͨ������������ϸ��ַ���ܱ�poi
					String strInfo = res.strAddr;
					Toast.makeText(MapActivity.this, strInfo, Toast.LENGTH_LONG).show();
					
				}
				//����ItemizedOverlayͼ��������ע�����
				ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(null, mMapView);
				//����Item
				OverlayItem item = new OverlayItem(res.geoPt, "", null);
				//�õ���Ҫ���ڵ�ͼ�ϵ���Դ
				Drawable marker = getResources().getDrawable(R.drawable.icon_marka);  
				//Ϊmaker����λ�úͱ߽�
				marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
				//��item����marker
				item.setMarker(marker);
				//��ͼ�������item
				itemOverlay.addItem(item);
				
				//�����ͼ����ͼ��
				mMapView.getOverlays().clear();
				//���һ����עItemizedOverlayͼ��
				mMapView.getOverlays().add(itemOverlay);
				//ִ��ˢ��ʹ��Ч
				mMapView.refresh();
			}
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				
			}
			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
			}
			public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
			}
			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
			}
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}
			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {
				// TODO Auto-generated method stub
				
			}

        });
        
		
    }
    
    @Override
    protected void onPause() {
    	/**
    	 *  MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
    	 */
        mMapView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onResume() {
    	/**
    	 *  MapView������������Activityͬ������activity�ָ�ʱ�����MapView.onResume()
    	 */
        mMapView.onResume();
        super.onResume();
        
        
    }
    
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView������������Activityͬ������activity����ʱ�����MapView.destroy()
    	 */
        mMapView.destroy();
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mMapView.onRestoreInstanceState(savedInstanceState);
    }
    
	
}
