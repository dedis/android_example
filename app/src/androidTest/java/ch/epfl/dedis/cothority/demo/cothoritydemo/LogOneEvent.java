package ch.epfl.dedis.cothority.demo.cothoritydemo;

import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import ch.epfl.dedis.byzcoin.SignerCounters;
import ch.epfl.dedis.eventlog.Event;
import ch.epfl.dedis.lib.exception.CothorityException;
import ch.epfl.dedis.byzcoin.InstanceId;
import ch.epfl.dedis.byzcoin.ByzCoinRPC;
import ch.epfl.dedis.eventlog.EventLogInstance;
import ch.epfl.dedis.lib.darc.Signer;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LogOneEvent {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("ch.epfl.dedis.cothority.demo.cothoritydemo", appContext.getPackageName());

        String mString = "";

        mString = mString.concat("VERSION.RELEASE {" + Build.VERSION.RELEASE + "}");
        mString = mString.concat("\nVERSION.INCREMENTAL {" + Build.VERSION.INCREMENTAL + "}");
        mString = mString.concat("\nVERSION.SDK {" + Build.VERSION.SDK + "}");
        mString = mString.concat("\nBOARD {" + Build.BOARD + "}");
        mString = mString.concat("\nBRAND {" + Build.BRAND + "}");
        mString = mString.concat("\nDEVICE {" + Build.DEVICE + "}");
        mString = mString.concat("\nFINGERPRINT {" + Build.FINGERPRINT + "}");
        mString = mString.concat("\nHOST {" + Build.HOST + "}");
        mString = mString.concat("\nID {" + Build.ID + "}");

        Signer admin = SecureKG.getSigner();
        ByzCoinRPC bc = null;
        try {
            bc = SecureKG.getRPC();

            SignerCounters ctrs = bc.getSignerCounters(Arrays.asList(admin.getIdentity().toString()));
            ctrs.increment();

            EventLogInstance el = EventLogInstance.fromByzcoin(bc, SecureKG.getEventlogId());
            InstanceId key = el.log(new Event("android-info", mString),
                    Arrays.asList(admin), ctrs.getCounters());
        } catch (CothorityException e) {
            e.printStackTrace();
        }
    }
}
