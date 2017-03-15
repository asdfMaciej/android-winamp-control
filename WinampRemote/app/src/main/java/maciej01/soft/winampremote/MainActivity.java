package maciej01.soft.winampremote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;


public class MainActivity extends AppCompatActivity {
    public MainActivity oszaleje() {
        return this;
    }
    public class WinampHandler {
        private String ip;
        private int port;

        private void getPreferences() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(oszaleje());
            ip = prefs.getString("ip_address", "192.168.1.109");
            port = 21337;//Integer.parseInt(prefs.getString("port_address", "21337"));
        }

        public String sendCommand(String command) {
            String r = "";
            NetClient nc = new NetClient(ip, port, "");
            nc.sendDataWithString(command);
            r = nc.receiveDataFromServer();
            return r;
        }

        public void pause() {
            sendCommand("pause");
        }
    }

    public class SimpleSocketClient extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {

            WinampHandler wh = new WinampHandler();
            wh.pause();
            return "haha";
        }

        @Override
        protected void onPostExecute(String result) {
            bConnectSet(result);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar seekVolume = (SeekBar) findViewById(R.id.seekBar);
        final TextView info = (TextView) findViewById(R.id.textView2);
        Button bConnect = ((Button) findViewById(R.id.button));
        Button bOptions = ((Button) findViewById(R.id.button3));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTesttt(v);
            }
        });
        bOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptions(v);
            }
        });
        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressVal, boolean fromUser) {
                progress = progressVal;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                info.setText("Covered: " + progress + "/" + seekBar.getMax());
            }
        });
    }

    public void onTesttt(View view) {
        Button bConnect = ((Button) findViewById(R.id.button));
        WinampHandler wh = new WinampHandler();
        wh.pause();
        //
        bConnect.setText("chuj");
        //SimpleSocketClient ss = new SimpleSocketClient();
        //bConnect.setText("create");
        //ss.execute("192.168.1.109", "21337", "next");
        //bConnect.setText("heil");

    }
    private void bConnectSet(String str) {
        Button bConnect = ((Button) findViewById(R.id.button));
        bConnect.setText(str);
    }

    public void openOptions(View view) {
        Intent i;
        i = new Intent(this, MyPreferencesActivity.class);
        startActivity(i);
    }


}

