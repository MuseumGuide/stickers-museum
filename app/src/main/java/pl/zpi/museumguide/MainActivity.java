package pl.zpi.museumguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.SystemRequirementsChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.zpi.museumguide.connection.RadarManager;
import pl.zpi.museumguide.data.DataPreparerRepository;
import pl.zpi.museumguide.data.DataRepository;
import pl.zpi.museumguide.data.domain.Beacon;
import pl.zpi.museumguide.data.domain.Work;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private DataRepository dataRepository; //todo inject
    private RadarManager radarManager;
    private ArrayAdapter<String> arrayAdapter;

    @BindView(R.id.titleLabel)
    TextView titleLabel;
    @BindView(R.id.authorLabel)
    TextView authorLabel;
    @BindView(R.id.list)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dataRepository = new DataPreparerRepository();
        Map<Beacon, Work> products = new HashMap<>();

        //todo get from repository
        Beacon b1 = dataRepository.getBeacon(DataPreparerRepository.beacon1UUID);
        Beacon b2 = dataRepository.getBeacon(DataPreparerRepository.beacon2UUID);
        //todo resolve many works on one beacon
        products.put(b1, b1.getWork().get(0));
        products.put(b2, b2.getWork().get(0));
        radarManager = new RadarManager(this, products);

        radarManager.setListener(new RadarManager.Listener() {
            @Override
            public void onProductPickup(Work work, List<String> allStickers) {
                titleLabel.setText(work.getTitle());
                //todo display all authors
                authorLabel.setText(work.getAuthor().getDisplayName());

                arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, allStickers);

                list.setAdapter(arrayAdapter);
                authorLabel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProductPutdown(Work work) {
                titleLabel.setText("SZUKA SZUKA");
                authorLabel.setVisibility(View.INVISIBLE);
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
