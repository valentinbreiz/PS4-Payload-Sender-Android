package valentinbreiz.ps4payloadsender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Intent mainIntent, introIntent, loginActivity;
    boolean premier_lancement;

    @Override
    protected void onStart() {
        super.onStart();

        Intent mainIntent = new Intent(this, Connection.class);
        startActivity(mainIntent);

    //    premier_lancement = Prefs.getBoolean("premier_lancement", TRUE);

        finish();

    }
}