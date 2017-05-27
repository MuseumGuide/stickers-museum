package pl.zpi.museumguide.connection;

import android.content.Context;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.zpi.museumguide.data.DataPreparerRepository;
import pl.zpi.museumguide.data.DataRepository;
import pl.zpi.museumguide.data.domain.Beacon;
import pl.zpi.museumguide.data.domain.Work;

public class RadarManager {
    private Listener listener;
    private DataRepository dataRepository;
    private BeaconManager beaconManager;
    private String scanId;

    private Map<Beacon, Integer> nearablesRssi;
    List<String> labels;

    public RadarManager(Context context, final Map<Beacon, Work> products)
    {
        dataRepository = new DataPreparerRepository();
        beaconManager = new BeaconManager(context);
        nearablesRssi = new HashMap<>();
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> list) {
                labels = new ArrayList<>();
                for (Nearable nearable : list) {
                    Beacon beacon = dataRepository.getBeacon(nearable.identifier);

                    if (!products.keySet().contains(beacon)) {
                        continue;
                    }

                    nearablesRssi.put(beacon, nearable.rssi);
                    Work currentWork = products.get(beacon);
                    //todo change this if to check beacon id not label
                    if (!labels.contains(currentWork.getTitle() + "\n" + currentWork.getAuthor().getDisplayName()))
                        labels.add(currentWork.getTitle() + "\n" + currentWork.getAuthor().getDisplayName());

                    Work work = products.get(getBest(nearablesRssi));
                    listener.onProductPickup(work, labels);
                }
            }
        });
    }

    public Beacon getBest(Map<Beacon, Integer> map) {
        Map.Entry<Beacon, Integer> best = null;

        for (Map.Entry<Beacon, Integer> entry : map.entrySet()) {
            if (best == null || entry.getValue().compareTo(best.getValue()) > 0)
                best = entry;
        }

        return best.getKey();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onProductPickup(Work work, List<String> allStickers);

        void onProductPutdown(Work work);
    }

    public void startUpdates() {
        beaconManager.connect(() -> scanId = beaconManager.startNearableDiscovery());
    }

    public void stopUpdates() {
        beaconManager.stopNearableDiscovery(scanId);
    }

    public void destroy() {
        beaconManager.disconnect();
    }

    public BeaconManager getBeaconManager() {
        return this.beaconManager;
    }
}
