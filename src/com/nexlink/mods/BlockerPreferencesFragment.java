/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.nexlink.mods;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.nexlink.browser.Browser;
import com.nexlink.browser.R;

//An extension to put in the browser settings
public class BlockerPreferencesFragment extends PreferenceFragment{
	SharedPreferences mSharedPreferences;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(Browser.appContext);
		addPreferencesFromResource(R.xml.blocker_preferences);
		findPreference("blockerWhitelistEnabled").setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				Blocker.whitelistEnabled = (Boolean) arg1;
				mSharedPreferences.edit().putBoolean("blockerWhitelistEnabled", ((Boolean)arg1).booleanValue());
				return true;
			}
		});
		EditTextPreference editTextPreference = (EditTextPreference)findPreference("blockerWhitelist");
		editTextPreference.getEditText().setSelection(editTextPreference.getEditText().getText().length());
		editTextPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				Blocker.whitelist = new ArrayList<String>(Arrays.asList(((String) arg1).split("\n")));
				mSharedPreferences.edit().putString("blockerWhitelist", (String) arg1);
				return true;
			}
		});
		
	}
}