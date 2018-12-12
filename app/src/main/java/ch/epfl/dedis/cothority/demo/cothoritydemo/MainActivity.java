package ch.epfl.dedis.cothority.demo.cothoritydemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;

import ch.epfl.dedis.byzcoin.SignerCounters;
import ch.epfl.dedis.eventlog.Event;
import ch.epfl.dedis.lib.exception.CothorityException;
import ch.epfl.dedis.byzcoin.InstanceId;
import ch.epfl.dedis.byzcoin.ByzCoinRPC;
import ch.epfl.dedis.eventlog.EventLogInstance;
import ch.epfl.dedis.lib.darc.Signer;

public class MainActivity extends AppCompatActivity {
    ByzCoinRPC bc;
    SignerCounters ctrs;
    Signer signer;
    int ct;

    public MainActivity() {
        try {
            bc = SecureKG.getRPC();
            signer = SecureKG.getSigner();
            ctrs = bc.getSignerCounters(Arrays.asList(signer.getIdentity().toString()));
        } catch (CothorityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ctrs.increment();
                    EventLogInstance el = EventLogInstance.fromByzcoin(bc, SecureKG.getEventlogId());
                    InstanceId key = el.log(new Event("android-hello", "Hello from MainActivity! " + ct),
                            Arrays.asList(signer), ctrs.getCounters());
                    ct++;
                } catch (CothorityException e) {
                    e.printStackTrace();
                }

                Snackbar.make(view, "A test event was sent to the event log.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
