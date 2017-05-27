package pl.zpi.museumguide;

import android.app.Activity;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.zpi.museumguide.connection.RadarManager;
import pl.zpi.museumguide.data.DataPreparerRepository;
import pl.zpi.museumguide.data.DataRepository;
import pl.zpi.museumguide.data.domain.Beacon;
import pl.zpi.museumguide.data.domain.Work;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.mockito.Mockito.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jakub Licznerski on 26.05.2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BeaconDetectedBehaviorTest {
    private String mTitleString;
    private Room room;
    private Work work;
    private BeaconManager.NearableListener listener;
    private List<Nearable> nearables;
    private BeaconManager beaconManager;
    private Beacon beacon;

    @Rule
    public ActivityTestRule<Room> mActivityRule = new ActivityTestRule<>(
            Room.class);

    @Before
    public void initValidString() throws Throwable {
        mActivityRule.runOnUiThread(() -> {
            DataRepository dataRepository = new DataPreparerRepository();
            room = mActivityRule.getActivity();

            // Specify a valid string.
            work = dataRepository.getAllWorks().get(0);
            beacon = work.getBeacon();
            beaconManager = room.radarManager.getBeaconManager();

            Field f = null; //NoSuchFieldException
            try {
                f = beaconManager.getClass().getDeclaredField("nearableListener");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            f.setAccessible(true);
            try {
                listener = (BeaconManager.NearableListener) f.get(beaconManager);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //list nearables
            nearables = new ArrayList<>();
            nearables.add(new Nearable(beacon.getUuid(), null, null, null, null, null, 0.0d, -5, false, 0.0d, 0.0d, 0.0d, 0, 0, null, null));

            listener.onNearablesDiscovered(nearables);
        });


        mTitleString = work.getTitle();
    }

    @Test
    public void assertWorkTitle() throws Throwable {


        room.showNotice(work);
        onView(withId(R.id.MapWorkTitle))
                .check(matches(withText(mTitleString)));
    }

}
