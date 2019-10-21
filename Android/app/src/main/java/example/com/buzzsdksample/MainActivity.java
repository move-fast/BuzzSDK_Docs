package example.com.buzzsdksample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import com.buzztechno.sdk.Buzz;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SecondActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Buzz.getInstance().addOrShowDeck();
    }
}
