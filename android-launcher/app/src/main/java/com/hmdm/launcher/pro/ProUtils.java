/*
 * Headwind MDM: Open Source Android MDM Software
 * https://h-mdm.com
 *
 * Copyright (C) 2019 Headwind Solutions LLC (http://h-sms.com)
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
 * limitations under the License.
 */

package com.hmdm.launcher.pro;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Process;
import android.view.View;

import com.hmdm.launcher.R;
import com.hmdm.launcher.json.ServerConfig;

import java.util.Calendar;

/**
 * OSS stubs extended for Intermark fork: permission wizard enabled (overlay + usage stats).
 * Kiosk and other Pro-only features remain stubbed.
 */
public class ProUtils {

    /**
     * Enable startup permission dialogs (overlay, usage history) in open-source launcher.
     */
    public static boolean isPro() {
        return true;
    }

    public static boolean kioskModeRequired(Context context) {
        return false;
    }

    public static void initCrashlytics(Context context) {
        // Stub
    }

    public static void sendExceptionToCrashlytics(Throwable e) {
        // Stub
    }

    public static boolean checkAccessibilityService(Context context) {
        return true;
    }

    /**
     * Real check for PACKAGE_USAGE_STATS / usage access (not the OSS stub that always returned true).
     */
    public static boolean checkUsageStatistics(Context context) {
        if (context == null) {
            return false;
        }
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        if (appOps == null) {
            return false;
        }
        int mode;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mode = appOps.unsafeCheckOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    Process.myUid(),
                    context.getPackageName());
        } else {
            mode = appOps.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    Process.myUid(),
                    context.getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    public static View preventStatusBarExpansion(Activity activity) {
        return null;
    }

    public static View preventApplicationsList(Activity activity) {
        return null;
    }

    public static View createKioskUnlockButton(Activity activity) {
        return null;
    }

    public static boolean isKioskAppInstalled(Context context) {
        return false;
    }

    public static boolean isKioskModeRunning(Context context) {
        return false;
    }

    public static Intent getKioskAppIntent(String kioskApp, Activity activity) {
        return null;
    }

    public static boolean startCosuKioskMode(String kioskApp, Activity activity, boolean enableSettings) {
        return false;
    }

    public static void updateKioskOptions(Activity activity) {
        // Stub
    }

    public static void updateKioskAllowedApps(String kioskApp, Activity activity, boolean enableSettings) {
        // Stub
    }

    public static void unlockKiosk(Activity activity) {
        // Stub
    }

    public static void processConfig(Context context, ServerConfig config) {
        // Stub
    }

    public static void processLocation(Context context, Location location, String provider) {
        // Stub
    }

    public static String getAppName(Context context) {
        return context.getString(R.string.app_name);
    }

    public static String getCopyright(Context context) {
        return "(c) " + Calendar.getInstance().get(Calendar.YEAR) + " " + context.getString(R.string.vendor);
    }
}
