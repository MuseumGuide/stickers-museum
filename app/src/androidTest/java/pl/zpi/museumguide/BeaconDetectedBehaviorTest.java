package pl.zpi.museumguide;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;
import android.view.WindowManager;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;

import junit.framework.AssertionFailedError;

import net.bytebuddy.matcher.StringMatcher;

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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
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
    private BeaconManager.NearableListener listener;
    private List<Nearable> nearables;
    private Handler mHandler;

    @Rule
    public ActivityTestRule<Room> mActivityRule = new ActivityTestRule<>(
            Room.class);

    @Before
    public void initValidString() throws Throwable {
        Work work = new DataPreparerRepository().getAllWorks().get(0);
        room = mActivityRule.getActivity();
        Beacon beacon = work.getBeacon();
        BeaconManager beaconManager = room.getRadarManager().getBeaconManager();

        //Java reflections
        Field f = beaconManager.getClass().getDeclaredField("nearableListener");
        f.setAccessible(true);
        listener = (BeaconManager.NearableListener) f.get(beaconManager);

        //list nearables
        nearables = new ArrayList<>();
        nearables.add(new Nearable(beacon.getUuid(), null, null, null, null, null, 0.0d, -5, false, 0.0d, 0.0d, 0.0d, 0, 0, null, null));


        //call onNearablesDiscovered with our beacon
        mActivityRule.runOnUiThread(() -> mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                listener.onNearablesDiscovered(nearables);
            }
        });

        unlockScreen();

        //set valid bottom sheet string
        mTitleString = work.getTitle();
    }


    public void unlockScreen() {
        Runnable wakeUpDevice = () -> room.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        room.runOnUiThread(wakeUpDevice);
    }

    @Test
    public void assertWorkTitle() throws Throwable {
        Message message = mHandler.obtainMessage(0, null);
        message.sendToTarget();

        waitFor(
                onView(withId(R.id.MapWorkTitle)),
                matches(isDisplayed()),
                5000
        );
    }


    private void waitFor(ViewInteraction viewInteraction, ViewAssertion viewAssertion, long timeout) {

        PollingTimeoutIdler idler = new PollingTimeoutIdler(viewInteraction, viewAssertion, timeout);
        registerIdlingResources(idler);

        viewInteraction.check(viewAssertion);

        unregisterIdlingResources(idler);
    }



    private class PollingTimeoutIdler implements IdlingResource {

        private final ViewAssertion mViewAssertion;
        private final long mTimeout;
        private final long mStartTime;
        private ResourceCallback mCallback;
        private volatile View mTestView;

        public PollingTimeoutIdler(ViewInteraction viewInteraction, ViewAssertion viewAssertion, long timeout) {
            mViewAssertion = viewAssertion;
            mTimeout = timeout;
            mStartTime = System.currentTimeMillis();

            viewInteraction.check(new ViewAssertion() {
                @Override
                public void check(View view, NoMatchingViewException noViewFoundException) {
                    mTestView = view;
                }
            });
        }

        @Override
        public String getName() {
            return getClass().getSimpleName();
        }

        @Override
        public boolean isIdleNow() {

            long elapsed = System.currentTimeMillis() - mStartTime;
            boolean timedOut = elapsed >= mTimeout;

            boolean idle = testView() || timedOut;
            if (idle) {
                mCallback.onTransitionToIdle();
            }

            return idle;
        }

        private boolean testView() {

            if (mTestView != null) {
                try {
                    mViewAssertion.check(mTestView, null);
                    return true;
                } catch (AssertionFailedError ex) {
                    return false;
                }
            } else {
                return false;
            }
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {
            mCallback = callback;
        }
    }
}
