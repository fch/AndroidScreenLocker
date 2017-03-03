package com.example.chflo.test2;

/**
 /*
 * Screen Lock - App to lock the screen without silly features
 * Copyright (C) 2017 FC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    final static int ENABLE_ADMIN = 1;
    final static int SUCCESS = -1;

    private ComponentName mAdminName = null;

    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdminName = new ComponentName(this, AdminManageReceiver.class);
        DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        /*
        check if app has admin privileges, if not, request them
         */
        if (mDPM.isAdminActive(mAdminName)) {
            mDPM.lockNow();
        } else {
            Log.e("screenlock", "Unable to lock the phone");
            showAdminManagement();
        }

        finish();
    }

    private void showAdminManagement() {

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                R.string.desc_enable_admin);
        startActivityForResult(intent, ENABLE_ADMIN);

    }
}
