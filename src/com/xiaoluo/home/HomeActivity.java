package com.xiaoluo.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.Menu;
import android.view.MenuItem;

import com.xiaoluo.fragment.BaseFragmentActivity;
import com.xiaoluo.fragment.ModuleFragment;
import com.xiaoluo.fragment.SplashActivity;
import com.xiaoluo.utilities.Constants;
import com.xiaoluo.utilities.Trace;

public class HomeActivity extends BaseFragmentActivity implements TabListener, OnPageChangeListener {
	private ViewPager mHemoPager;
	private ActionBar mActionBar;
	private ModuleAdapter mAdapter;
	private String[] tabs = new String[]{"萝莉", "女王", "御姐"};

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_home);
	}

	@Override
	protected void initializeData() {

	}

	@Override
	protected void initializeViews() {
		mHemoPager = (ViewPager)findViewById(R.id.mHemoPager);
		
		mAdapter = new ModuleAdapter(getSupportFragmentManager());
		mHemoPager.setAdapter(mAdapter);
		mHemoPager.setOnPageChangeListener(this);
		
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Add 3 tabs, specifying the tab's text and TabListener
	    for (int i = 0; i < tabs.length; i++) {
	    	mActionBar.addTab(mActionBar.newTab().setText(tabs[i]).setTabListener(this));
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		Trace.d("HomeActivity:onNewIntent");
		if (intent.getBooleanExtra(Constants.RESTART_APP, false)) {
			protectApplication();
		} else if (intent.getBooleanExtra(Constants.RELOGIN_APP, false)) {
			// reLogin();
		} else {
			// type = intent.getStringExtra(Constants.KEY_ACTION_TYPE);
			// UserInfo mUserInfo = (UserInfo)
			// intent.getSerializableExtra(UserInfo.USER_INFO);
			// if(TextUtil.isValidate(type) && mDrawerFragment != null) {
			// mDrawerFragment.setCurrentFragment(type, mUserInfo);
			// }
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Trace.d("HomeActivity:onRestart");
		if (getIntent().getBooleanExtra(Constants.RESTART_APP, false)) {
			protectApplication();
		}
	}

	private void protectApplication() {
		finish();
		startActivity(new Intent(this, SplashActivity.class));
	}
	
	// Since this is an object collection, use a FragmentStatePagerAdapter,
	// and NOT a FragmentPagerAdapter.
	public class ModuleAdapter extends FragmentStatePagerAdapter {
	    public ModuleAdapter(FragmentManager fm) {
	        super(fm);
	    }

	    @Override
	    public Fragment getItem(int i) {
	        return ModuleFragment.newInstance(tabs[i]);
	    }

	    @Override
	    public int getCount() {
	        return tabs.length;
	    }

	    @Override
	    public CharSequence getPageTitle(int position) {
	        return tabs[position];
	    }
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		 mHemoPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		mActionBar.setSelectedNavigationItem(position);
	}
}
