package hibernate.v2.rule202020.ui.custom;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import hibernate.v2.rule202020.C;
import hibernate.v2.rule202020.R;
import hibernate.v2.rule202020.ui.activity.MainActivity;

public class TrackingService extends Service {

	private Context mContext;
	private NotificationManager notificationManager;

	private BroadcastReceiver screenReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			Log.d(C.TAG, "screenReceiver.onReceive()");
			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				C.createAm(mContext);
			} else {
				cancelNotification();
			}
		}
	};

	private BroadcastReceiver notiReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			Log.d(C.TAG, "notiReceiver.onReceive()");
			if (intent.getAction().equals(C.ACTION_NOTI)) {
				C.createAm(mContext);
				showNotification();
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(C.TAG, "onStartCommand()");
		mContext = getApplicationContext();
		super.onStartCommand(intent, flags, startId);

		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(screenReceiver, filter);

		filter = new IntentFilter();
		filter.addAction(C.ACTION_NOTI);
		registerReceiver(notiReceiver, filter);

		C.createAm(mContext);

		return START_STICKY;
	}

	private void showNotification() {
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class),
				PendingIntent.FLAG_ONE_SHOT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

		builder.setSmallIcon(R.drawable.ic_launcher)
				.setWhen(System.currentTimeMillis())
				.setContentTitle(getString(R.string.noti_title))
				.setContentText(getString(R.string.noti_message))
				.setContentIntent(contentIntent)
				.setPriority(Notification.PRIORITY_MIN)
				.setAutoCancel(true);

		Notification notification = builder.build();
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notificationManager.notify(1, notification);
	}

	private void cancelNotification() {
		C.cancelAm(mContext);
		notificationManager.cancel(1);
	}

	@Override
	public void onDestroy() {
		Log.d(C.TAG, "onDestroy()");
		super.onDestroy();
		cancelNotification();
		unregisterReceiver(screenReceiver);
		unregisterReceiver(notiReceiver);
	}

}
