package com.xiaoluo.home;

import java.util.ArrayList;

import com.xiaoluo.fragment.BaseFragmentActivity;
import com.xiaoluo.fragment.SplashActivity;
import com.xiaoluo.utilities.Constants;
import com.xiaoluo.utilities.Trace;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends BaseFragmentActivity {
	private GridView mModulesGrv;
	private ModuleAdapter mModuleAdapter;
	private ArrayList<String> entities = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_home);
	}
	
	@Override
	protected void initializeData() {
		
	}
	
	@Override
	protected void initializeViews() {
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.home, menu);
		
//		MenuItem item = menu.add(0, 101, 0, "设置");
//		item.setIcon(R.drawable.ic_launcher);
//		MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
		
//		if(item.getItemId() == 101) {
//			startActivities(this, SettingActivity.class);
//		}
		
		return true;
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		Trace.d("HomeActivity:onNewIntent");
		if(intent.getBooleanExtra(Constants.RESTART_APP, false)) {
			protectApplication();
		} else if(intent.getBooleanExtra(Constants.RELOGIN_APP, false)) {
			// reLogin();
		} else {
//			type = intent.getStringExtra(Constants.KEY_ACTION_TYPE);
//			UserInfo mUserInfo = (UserInfo) intent.getSerializableExtra(UserInfo.USER_INFO);
//			if(TextUtil.isValidate(type) && mDrawerFragment != null) {
//				mDrawerFragment.setCurrentFragment(type, mUserInfo);
//			}
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Trace.d("HomeActivity:onRestart");
		if(getIntent().getBooleanExtra(Constants.RESTART_APP, false)) {
			protectApplication();
		}
	}

	private void protectApplication() {
		finish();
		startActivity(new Intent(this, SplashActivity.class));
	}
	
	class ModuleAdapter extends BaseAdapter {
		private ViewHolder holder;
		
		@Override
		public int getCount() {
			return entities.size();
		}

		@Override
		public Object getItem(int position) {
			return entities.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
//			if(convertView == null || convertView.getTag() == null) {
//				holder = new ViewHolder();
//				convertView = (LinearLayout) LayoutInflater.from(HomeActivity.this).inflate(R.layout.activity_home_modyfy, parent);
//				holder.mModuleIcon = (ImageView) convertView.findViewById(R.id.mModuleIcon);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			holder.mModuleIcon.setImageBitmap(bm);;
			
			return convertView;
		}
	}
	
	static class ViewHolder {
		ImageView mModuleIcon;
		TextView mModuleText;
	}
}
