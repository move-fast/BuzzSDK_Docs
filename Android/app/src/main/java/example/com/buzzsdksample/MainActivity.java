package example.com.buzzsdksample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.buzztechno.sdk.Buzz;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Buzz.getInstance().addOrShowDeck();
    }
}
