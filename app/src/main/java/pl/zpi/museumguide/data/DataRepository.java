package pl.zpi.museumguide.data;

/**
 * Created by Jakub Liczneski on 18.03.2017.
 */

import java.util.List;

import pl.zpi.museumguide.data.domain.Beacon;
import pl.zpi.museumguide.data.domain.Work;

/**
 * Provides abstraction for data source implementation
 */
public interface DataRepository {

    //todo create methods definitions
    List<Work> getAllWorks();
    List<Work> getBeaconWork(String uuid);
    Beacon getBeacon(String uuid);
    List<Beacon> getAllBeacons();
}
