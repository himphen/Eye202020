package hibernate.v2.rule202020;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

import hibernate.v2.rule202020.ui.activity.MainActivity;
import hibernate.v2.rule202020.ui.custom.TrackingService;

public class C extends Util {

	public static final String TAG = "TAG";
	public static final String PREF = "PREF_OPTION";

	public static final String SETTING_IS_START = "SETTING_IS_START";

	public static final String IAP_PID = "iap1984";
	public static final String ACTION_NOTI = "ACTION_NOTI";

	public static void createAm(Context mContext) {
		Log.d(C.TAG, "createAm");
		SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(mContext);
		int time = Integer.parseInt(setting.getString("pref_reminder", "20"));
		Log.d(C.TAG, "MINUTE " + time);

		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.MINUTE, time);
		cal.add(Calendar.SECOND, 5);

		Intent intent = new Intent(mContext, TrackingService.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 500,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);
	}

	public static void cancelAm(Context mContext) {
		Log.d(C.TAG, "cancelAm");
		Intent intent = new Intent(mContext, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 500,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);

		AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pendingIntent);
	}

	public static boolean isServiceActivated(Context mContext) {
		SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(mContext);
		return setting.getBoolean(C.SETTING_IS_START, false);
	}
}
