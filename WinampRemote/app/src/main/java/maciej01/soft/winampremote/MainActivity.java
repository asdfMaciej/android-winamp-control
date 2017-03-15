package maciej01.soft.winampremote;

import android.content.Intent;
import android.os.StrictMode;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onTesttt(View view) {
        Button gen = ((Button)findViewById(R.id.button));
        NetClient nc = new NetClient("192.168.1.109", 21337, "");
        nc.sendDataWithString("pause");
        //String r = nc.receiveDataFromServer();
        gen.setText("chuj");
    }

    public void openOptions(View view) {
        Intent i;
        i = new Intent(this, MyPreferencesActivity.class);
        startActivity(i);
    }
}
