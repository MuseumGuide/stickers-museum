package pl.zpi.museumguide.connection;

import android.content.Context;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadarManager
{
    private Listener listener;

    private BeaconManager beaconManager;
    private String scanId;

    private Map<NearableID, Integer> nearablesRssi;
    List<String> labels;

    public RadarManager(Context context, final Map<NearableID, Art> products)
    {
        beaconManager = new BeaconManager(context);
        nearablesRssi = new HashMap<>();
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> list)
            {
                labels = new ArrayList<String>();
                for (Nearable nearable : list)
                {
                    NearableID nearableID = new NearableID(nearable.identifier);
                    if (!products.keySet().contains(nearableID)) { continue; }

                    nearablesRssi.put(nearableID, nearable.rssi);
                    if(!labels.contains((products.get(nearableID)).toString()))
                        labels.add((products.get(nearableID)).toString());

                    Art product = products.get(getBest(nearablesRssi));
                    listener.onProductPickup(product, labels);
                }
            }
        });
    }

    public NearableID getBest(Map<NearableID, Integer> map)
    {
        Map.Entry<NearableID, Integer> best = null;

        for (Map.Entry<NearableID, Integer> entry : map.entrySet())
        {
            if (best == null || entry.getValue().compareTo(best.getValue()) > 0)
                best = entry;
        }

        return best.getKey();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener
    {
        void onProductPickup(Art product, List<String> allStickers);
        void onProductPutdown(Art product);
    }

    public void startUpdates() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                scanId = beaconManager.startNearableDiscovery();
            }
        });
    }

    public void stopUpdates() {
        beaconManager.stopNearableDiscovery(scanId);
    }

    public void destroy() {
        beaconManager.disconnect();
    }
}
