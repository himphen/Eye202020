package hibernate.v2.rule202020.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hibernate.v2.rule202020.C;
import hibernate.v2.rule202020.R;
import hibernate.v2.rule202020.Util;
import hibernate.v2.rule202020.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {

	private SharedPreferences settingDefault;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	private AdView adView;

	@BindView(R.id.adLayout)
	RelativeLayout adLayout;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		C.detectLanguage(mContext);

		ActionBar ab = initActionBar(getSupportActionBar(), R.string.app_name);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setHomeButtonEnabled(false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_container_adview);
		settingDefault = PreferenceManager
				.getDefaultSharedPreferences(mContext);

		ButterKnife.bind(this);
		setSupportActionBar(toolbar);

		ActionBar ab = initActionBar(getSupportActionBar(), R.string.app_name);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setHomeButtonEnabled(false);

		C.forceShowMenu(mContext);

		adView = C.initAdView(mContext, adLayout);

		Fragment mainFragment = new MainFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, mainFragment)
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_language:
				language();
				break;
			case R.id.action_settings:
				Intent intent = new Intent().setClass(mContext, SettingsActivity.class);
				mContext.startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.removeAllViews();
			adView.destroy();
		}
		super.onDestroy();
	}


	private void language() {
		String language = settingDefault.getString(Util.PREF_LANGUAGE, "auto");
		int a = 0;
		switch (language) {
			case "auto":
				a = 0;
				break;
			case "en":
				a = 1;
				break;
			case "zh":
				a = 2;
				break;
		}

		MaterialDialog.Builder dialog = new MaterialDialog.Builder(this)
				.title(R.string.action_language)
				.items(R.array.language_choose)
				.itemsCallbackSingleChoice(a, new MaterialDialog.ListCallbackSingleChoice() {
					@Override
					public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
						switch (which) {
							case 0:
								settingDefault.edit().putString(Util.PREF_LANGUAGE, "auto")
										.apply();
								break;
							case 1:
								settingDefault.edit().putString(Util.PREF_LANGUAGE, "en")
										.apply();
								break;
							case 2:
								settingDefault.edit().putString(Util.PREF_LANGUAGE, "zh")
										.apply();
								break;
						}
						startActivity(new Intent(mContext, MainActivity.class));
						finish();
						return false;
					}
				})
				.negativeText(R.string.ui_cancel);
		dialog.show();

	}
}
