package pl.zpi.museumguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.sdk.SystemRequirementsChecker;

import pl.zpi.museumguide.connection.*;
import pl.zpi.museumguide.data.DataPreparerRepository;
import pl.zpi.museumguide.data.DataRepository;
import pl.zpi.museumguide.data.domain.Beacon;
import pl.zpi.museumguide.data.domain.Work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private DataRepository dataRepository; //todo inject
    private RadarManager radarManager;
    private ArrayAdapter<String> arrayAdapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataRepository = new DataPreparerRepository();

        setContentView(R.layout.activity_main);

        Map<Beacon, Work> products = new HashMap<>();

        //todo get from repository
        Beacon b1 = dataRepository.getBeacon("9d52d31fa9e0f214");
        Beacon b2 = dataRepository.getBeacon("c0e0ce88435105aa");
        //todo resolve many works on one beacon
        products.put(b1, b1.getWork().get(0));
        products.put(b2, b2.getWork().get(0));
        radarManager = new RadarManager(this, products);

        radarManager.setListener(new RadarManager.Listener() {
            @Override
            public void onProductPickup(Work work, List<String> allStickers) {
                ((TextView) findViewById(R.id.titleLabel)).setText(work.getTitle());
                //todo display all authors
                ((TextView) findViewById(R.id.authorLabel)).setText(work.getAuthors().get(0).getDisplayName());
                lv = (ListView) findViewById(R.id.lista);

                arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, allStickers);

                lv.setAdapter(arrayAdapter);
                findViewById(R.id.authorLabel).setVisibility(View.VISIBLE);
            }

            @Override
            public void onProductPutdown(Work work) {
                ((TextView) findViewById(R.id.titleLabel)).setText("SZUKA SZUKA");
                findViewById(R.id.authorLabel).setVisibility(View.INVISIBLE);
            }
        });


    }

    public void goToMap(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Room.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
        } else {
            Log.d(TAG, "Starting RadarManager updates");
            radarManager.startUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping RadarManager updates");
        radarManager.stopUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        radarManager.destroy();
    }
}
