package fr.wcs.insiteo;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.insiteo.lbs.Insiteo;
import com.insiteo.lbs.common.ISError;
import com.insiteo.lbs.common.auth.entities.ISUserSite;
import com.insiteo.lbs.common.init.ISEPackageType;
import com.insiteo.lbs.common.init.ISEServerType;
import com.insiteo.lbs.common.init.ISPackage;
import com.insiteo.lbs.common.init.listener.ISIInitListener;
import com.insiteo.lbs.location.ISILocationListener;
import com.insiteo.lbs.location.ISLocation;
import com.insiteo.lbs.common.init.ISEPackageType;
import com.insiteo.lbs.map.render.ISERenderMode;


import java.util.Stack;

public class MainActivity extends Activity implements ISIInitListener, ISILocationListener {

    RelativeLayout mRelativeLayout;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout)findViewById(R.id.activity_main);
        Insiteo.getInstance().initialize(this, listener);
    }

    ISIInitListener listener = new ISIInitListener() {

        @Override
        public void onInitDone(ISError error, ISUserSite suggestedSite, boolean fromLocalCache) {
            Log.d("InsiteoTest", "onInitDone");
            if(error == null) {
                Log.d("InsiteoTest", "onInitDone2");
                Insiteo.getInstance().startAndUpdate(suggestedSite, this);
                // The suggested site will be started
            }
        }

        @Override
        public void onStartDone(ISError error, Stack<ISPackage> packageToUpdate) {
            Log.d("InsiteoTest", "onStartDone");

            if(error == null) {
                if(!packageToUpdate.isEmpty()) {
                    // Package update are available. They will be downloaded.
                    Log.d("InsiteoTest", "onStartDone1");
                } else {
                    // No package require to be updated. The SDK is no ready to be used.
                    Log.d("InsiteoTest", "onStartDone2");
                }
            }
        }



        @Override
        public void onDataUpdateDone(ISError isError) {

        }

        @Override
        public Location selectClosestToLocation() {
            return null;
        }

        @Override
        public void onPackageUpdateProgress(ISEPackageType packageType, boolean download,
                                            long progress, long total) {
            showUpdateUI();
        }
    };


    @Override
    public void onInitDone(ISError isError, ISUserSite isUserSite, boolean b) {

    }

    @Override
    public void onStartDone(ISError isError, Stack<ISPackage> stack) {

    }

    @Override
    public void onPackageUpdateProgress(ISEPackageType isePackageType, boolean b, long l, long l1) {

    }

    @Override
    public void onDataUpdateDone(ISError error) {
        if(error == null) {
            // Packages have been updated. The SDK is no ready to be used.
            Log.d("InsiteoTest", "onDataUpdateDone !");
        }
    }

    @Override
    public Location selectClosestToLocation() {
        return null;
    }

    private void showUpdateUI() {
        Log.d("InsiteoTest", "ShowUpdateUI");
    }

    @Override
    public void onLocationInitDone(ISError isError) {

    }

    @Override
    public void onLocationReceived(ISLocation isLocation) {

    }

    @Override
    public void onAzimuthReceived(float v) {

    }

    @Override
    public void onCompassAccuracyTooLow() {

    }

    @Override
    public void onNeedToActivateGPS() {

    }

    @Override
    public void onLocationLost(ISLocation isLocation) {

    }

    @Override
    public void noRegisteredBeaconDetected() {

    }

    @Override
    public void onWifiActivationRequired() {

    }

    @Override
    public void onBleActivationRequired() {

    }
}
