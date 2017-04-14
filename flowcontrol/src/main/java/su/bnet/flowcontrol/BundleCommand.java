package su.bnet.flowcontrol;

import android.os.Bundle;

/**
 * Created by andrey on 25.11.2016.
 */

public class BundleCommand implements Command {
    private Bundle data;

    public Bundle getData() {
        return data;
    }

    @Override
    public void forward(Bundle data) {
        this.data = data;
    }

    @Override
    public void rollback() {

    }
}
