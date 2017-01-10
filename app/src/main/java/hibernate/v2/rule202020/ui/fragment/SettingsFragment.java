package hibernate.v2.rule202020.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.view.View;

import hibernate.v2.rule202020.R;

public class SettingsFragment extends BasePreferenceFragment {

	public SettingsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_general);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ListPreference prefReminder = (ListPreference) findPreference("pref_reminder");
		ListPreference prefDetect = (ListPreference) findPreference("pref_detect");

		prefReminder.setSummary(prefReminder.getEntry());
		prefDetect.setSummary(prefDetect.getEntry());

		prefReminder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setSummary(((ListPreference) preference).getEntry());
				return true;
			}
		});
		prefDetect.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setSummary(((ListPreference) preference).getEntry());
				return true;
			}
		});

		findPreference("pref_rate_app").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				openDialogRateApp();
				return false;
			}
		});
		findPreference("pref_report").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				openDialogReport();
				return false;
			}
		});
		findPreference("pref_more_app").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				openDialogMoreApp();
				return false;
			}
		});
	}

	private void openDialogMoreApp() {
		Uri uri = Uri.parse("market://search?q=pub:\"Hibernate\"");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	private void openDialogRateApp() {
		Uri uri = Uri.parse("market://details?id=hibernate.v2.rule202020");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	private void openDialogReport() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		String[] tos = {"hibernatev2@gmail.com"};
		intent.putExtra(Intent.EXTRA_EMAIL, tos);
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.report_title));
		intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.report_subject));
		intent.setType("message/rfc822");
		startActivity(Intent.createChooser(intent, getString(R.string.report)));
	}
}
