package com.nexlink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import android.preference.PreferenceManager;

import com.android.browser.Browser;
import com.android.browser.BrowserSettings;

public class Blocker {
	protected static Boolean whitelistEnabled;
	protected static List<String> whitelist;
	private static final Pattern protocol = Pattern.compile("^.*:\\/\\/");
	private static final Pattern trailing = Pattern.compile("[\\/|\\?|&|#|$].*$");
	
	public static boolean isBlocked(String url){
		if(BrowserSettings.getInstance().getHomePage().equals(url)){
			return false;
		}
		if(whitelist == null){
			whitelist = new ArrayList<String>(Arrays.asList(PreferenceManager.getDefaultSharedPreferences(Browser.appContext).getString("blockerWhitelist", "").split("\n")));
		}
		if(whitelistEnabled == null){
			whitelistEnabled = Boolean.valueOf(PreferenceManager.getDefaultSharedPreferences(Browser.appContext).getBoolean("blockerWhitelistEnabled", true));
		}
		if(!whitelistEnabled.booleanValue()){
			return false;
		}
		boolean blocked = true;
		String domain = null;
		if(url != null){
			domain = protocol.matcher(url).replaceFirst("");
			domain = trailing.matcher(domain).replaceFirst("");
		}
		if(domain != null){
			for(String filter : whitelist){
				if(!filter.isEmpty() && domain.trim().toLowerCase().contains(filter.trim().toLowerCase())){
					blocked = false;
					/*System.out.println(domain);
			    	AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
			    	alert.setTitle("Forbidden");
			    	alert.setMessage("This site is forbidden, sry :(");
			    	alert.setPositiveButton("Ok", null);
			    	alert.show();*/
				}
			}
		}
		System.out.println(blocked);
		return blocked;
	}

	static void clear(){
		PreferenceManager.getDefaultSharedPreferences(Browser.appContext).edit().clear().apply();
	}
}
