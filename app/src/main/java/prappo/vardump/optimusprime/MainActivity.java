package prappo.vardump.optimusprime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pusher pusher = new Pusher("0b0fefa7979743fd63c1");

        Channel channel = pusher.subscribe("optimus");

        channel.bind("my_event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                try {
                    JSONObject json = new JSONObject(data);
                    final String title = json.getString("title");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Title : " + title,Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(data);
            }
        });

        pusher.connect();
    }
}
