package example.com.buzzsdksample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.buzztechno.sdk.Buzz;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Buzz.getInstance().addOrShowDeck();
    }
}
