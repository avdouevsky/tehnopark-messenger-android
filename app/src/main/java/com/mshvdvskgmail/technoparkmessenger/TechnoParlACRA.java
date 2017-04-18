package com.mshvdvskgmail.technoparkmessenger;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Admin on 18.04.2017.
 */

@ReportsCrashes(
        formKey = "", // This is required for backward compatibility but not used
        formUri = "http://bnet.i-partner.ru/projects/ACRA/",
        mode = ReportingInteractionMode.TOAST,
        forceCloseDialogAfterToast = true,
        resToastText = R.string.crash_toast_text
)

public class TechnoParlACRA extends Application {
    @Override
    public void onCreate() {
        //...
        if(!BuildConfig.DEBUG) ACRA.init(this);
    }
}
