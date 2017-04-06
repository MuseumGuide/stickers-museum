package pl.zpi.museumguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.sdk.SystemRequirementsChecker;
import pl.zpi.museumguide.connection.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private RadarManager radarManager;
    private ArrayAdapter<String> arrayAdapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<NearableID, Art> products = new HashMap<>();

        products.put(new NearableID("9d52d31fa9e0f214"), new Art("chair", "Widze krzeslo"));
        products.put(new NearableID("c0e0ce88435105aa"), new Art("bag", "Widze torbe"));
        radarManager = new RadarManager(this, products);

        radarManager.setListener(new RadarManager.Listener()
        {
            @Override
            public void onProductPickup(Art product, List<String> allStickers)
            {
                ((TextView) findViewById(R.id.titleLabel)).setText(product.getName());
                ((TextView) findViewById(R.id.descriptionLabel)).setText(product.getSummary());
                lv = (ListView) findViewById(R.id.lista);

                arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, allStickers);

                lv.setAdapter(arrayAdapter);
                findViewById(R.id.descriptionLabel).setVisibility(View.VISIBLE);
            }
            @Override
            public void onProductPutdown(Art product)
            {
                ((TextView) findViewById(R.id.titleLabel)).setText("SZUKA SZUKA");
                findViewById(R.id.descriptionLabel).setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onResume()
    {
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
