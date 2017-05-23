package fr.wcs.insiteo;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.insiteo.lbs.Insiteo;
import com.insiteo.lbs.common.ISError;
import com.insiteo.lbs.common.auth.entities.ISUserSite;
import com.insiteo.lbs.common.init.ISEPackageType;
import com.insiteo.lbs.common.init.ISPackage;
import com.insiteo.lbs.common.init.listener.ISIInitListener;
import com.insiteo.lbs.location.ISILocationListener;
import com.insiteo.lbs.location.ISLocation;
import com.insiteo.lbs.location.ISLocationProvider;
import com.insiteo.lbs.map.ISMap2DView;
import com.insiteo.lbs.map.render.ISGenericRTO;

import java.util.Stack;

public class MainActivity extends Activity implements  ISIInitListener, ISILocationListener {
    RelativeLayout mRelativeLayout;
    ISMap2DView mMapView;

    ISLocationProvider mLocProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout)findViewById(R.id.activity_main);
        Insiteo.getInstance().initialize(this, this);
    }

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
    public void onPackageUpdateProgress(ISEPackageType packageType, boolean download,
                                        long progress, long total) {
        showUpdateUI();
    }

    private void showUpdateUI() {
        Log.d("InsiteoTest", "ShowUpdateUI");
    }

    @Override
    public void onDataUpdateDone(ISError error) {
        if(error == null) {
            // Packages have been updated. The SDK is no ready to be used.
            Log.d("InsiteoTest", "onDataUpdateDone !");

            initMap();
        }
    }

    private void initMap() {
        mMapView = new ISMap2DView(this);
        mRelativeLayout.addView(mMapView);
        /* This method will add the rto at the map center */
        ISGenericRTO genericRTO = new ISGenericRTO(mMapView.getScreenCenter(), "MyRTO");
        genericRTO.setLabel("MyRTO");
        mMapView.addRTO(genericRTO);

        initLocation();
    }

    private void initLocation() {
        Log.d("InsiteoTest", "onLocationInitDone !");
        mLocProvider = ISLocationProvider.getInstance();
        // Add location renderer to the ISMapView, thus location is displayed on map
        mMapView.addRenderer(mLocProvider.getRenderer(getResources()));
        mLocProvider.start(this);
    }

    /**
     * This callback will be used in order to select the most suitable ISSite that will be returned in
     * by onInitDone(ISError, ISUserSite, boolean). Most of the time this should be used to return the user's location. If no android.location.Location
     * (ie null) is returned  then the suggested ISSite will simply be the first one returned by the server.
     *
     * @return the android.location.Location to find the most suitable ISSite or null
     */
    @Override
    public Location selectClosestToLocation() {
        return null;
    }

    @Override
    public void onLocationInitDone(ISError isError) {
        Log.d("InsiteoTest", "onLocationInitDone !");
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