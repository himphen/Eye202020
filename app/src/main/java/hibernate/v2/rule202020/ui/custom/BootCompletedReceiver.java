package hibernate.v2.rule202020.ui.custom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import hibernate.v2.rule202020.C;

public class BootCompletedReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
			if (C.isServiceActivated(context)) {
				Intent serviceIntent = new Intent(context,
						TrackingService.class);
				context.startService(serviceIntent);
			}
		}
	}
}