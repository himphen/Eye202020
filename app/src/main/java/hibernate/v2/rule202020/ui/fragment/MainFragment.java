package hibernate.v2.rule202020.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hibernate.v2.rule202020.C;
import hibernate.v2.rule202020.R;
import hibernate.v2.rule202020.ui.custom.TrackingService;

/**
 * Created by himphen on 21/5/16.
 */
public class MainFragment extends BaseFragment {

	private SharedPreferences setting;

	@BindView(R.id.btnStart)
	Button btnStart;

	@OnClick(R.id.btnHelp)
	public void onClickHelp(View view) {
		MaterialDialog.Builder dialog = new MaterialDialog.Builder(mContext)
				.title(R.string.help_btn)
				.customView(R.layout.dialog_help, true)
				.positiveText(R.string.ui_okay);
		dialog.show();
	}

	@OnClick(R.id.btnStart)
	public void onClickStart(View view) {
		if (C.isServiceActivated(mContext)) {
			stopTracking();
		} else {
			startTracking();
		}
	}

	public MainFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setting = PreferenceManager.getDefaultSharedPreferences(mContext);
		init();
	}

	private void init() {
		if (C.isServiceActivated(mContext)) {
			deactivateBtn();
		} else {
			activateBtn();
		}
	}

	private void activateBtn() {
		btnStart.setText(R.string.start_btn_not_start);
	}

	private void deactivateBtn() {
		btnStart.setText(R.string.start_btn_is_start);
	}

	public void startTracking() {
		Log.d(C.TAG, "Start tracking");
		Intent intent = new Intent(mContext, TrackingService.class);
		mContext.startService(intent);
		deactivateBtn();
		setting.edit().putBoolean(C.SETTING_IS_START, true).apply();
	}

	public void stopTracking() {
		Log.d(C.TAG, "Stop tracking");
		Intent intent = new Intent(mContext, TrackingService.class);
		mContext.stopService(intent);
		activateBtn();
		setting.edit().putBoolean(C.SETTING_IS_START, false).apply();
	}
}
