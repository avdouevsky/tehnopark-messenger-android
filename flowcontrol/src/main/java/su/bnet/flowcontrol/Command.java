package su.bnet.flowcontrol;

import android.os.Bundle;

/**
 * Created by andrey on 25.11.2016.
 */

public interface Command{
    public void forward(Bundle data);
    public void rollback();
}
