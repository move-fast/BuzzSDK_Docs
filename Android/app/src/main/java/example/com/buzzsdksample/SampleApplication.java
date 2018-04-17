package example.com.buzzsdksample;

import android.app.Application;
import com.buzztechno.sdk.Buzz;
import com.buzztechno.sdk.Config;

/**
 * @author Matthias Schmitt
 */
public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Config config = new Config(getResources());
        Buzz.initialize(this, config, "e78grdmnqainn9pnz6fllabyzjxptpdq", "0pwb6ep3em0t3dsamr0wqn1lin3h9tir");
    }
}
