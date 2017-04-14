package su.bnet.aton;

import android.app.Application;

import su.bnet.aton.helpers.Fonts;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by andrey on 23.11.2016.
 */

public class AtonApplication extends Application {
    private static AtonApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Fonts.create(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/GothamPro-Reg.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static AtonApplication getInstance() {
        return instance;
    }
}
