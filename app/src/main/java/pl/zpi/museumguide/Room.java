package pl.zpi.museumguide;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.estimote.sdk.SystemRequirementsChecker;

import pl.zpi.museumguide.data.DataPreparerRepository;
import pl.zpi.museumguide.data.DataRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.zpi.museumguide.connection.RadarManager;
import pl.zpi.museumguide.data.domain.Beacon;
import pl.zpi.museumguide.data.domain.Work;

public class Room extends AppCompatActivity
{

    private static final String TAG = "Room";

    private DataRepository dataRepository;
    private RadarManager radarManager;
    private ArrayAdapter<String> arrayAdapter;
    private Map<String, ImageView> pointsOnMap;
    private ListView lv;
    private ImageView sticker1;
    private ImageView sticker2;
    private ImageView sticker3;
    private ImageView sticker4;
    private RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        background = (RelativeLayout) findViewById(R.id.map);

        ((FrameLayout) findViewById(R.id.background_room)).setBackgroundColor(Color.parseColor("#e1e1e1"));
        ((ConstraintLayout) findViewById(R.id.main_room)).setBackgroundColor(Color.parseColor("#e1e1e1"));

        background.setBackgroundResource(R.drawable.mapa);

        sticker1 = (ImageView) findViewById(R.id.sticker_1);
        sticker2 = (ImageView) findViewById(R.id.sticker_2);
        sticker3 = (ImageView) findViewById(R.id.sticker_3);
        sticker4 = (ImageView) findViewById(R.id.sticker_4);

        dataRepository = new DataPreparerRepository();

        Map<Beacon, Work> products = new HashMap<>();

        //todo get from repository
        Beacon b1 = dataRepository.getBeacon("9d52d31fa9e0f214");
        Beacon b2 = dataRepository.getBeacon("c0e0ce88435105aa");

        pointsOnMap = new HashMap<String, ImageView>();
        pointsOnMap.put("9d52d31fa9e0f214", sticker1);
        pointsOnMap.put("c0e0ce88435105aa", sticker2);

        //todo resolve many works on one beacon
        products.put(b1, b1.getWork().get(0));
        products.put(b2, b2.getWork().get(0));
        radarManager = new RadarManager(this, products);

        radarManager.setListener(new RadarManager.Listener() {
            @Override
            public void onProductPickup(Work work, List<String> allStickers)
            {
                ((TextView) findViewById(R.id.temp)).setText(work.getTitle());

                sticker1.setImageResource(R.drawable.sticker);
                sticker2.setImageResource(R.drawable.sticker);

                ImageView near = pointsOnMap.get(String.valueOf(work.getBeacon().getUuid()));
                near.setImageResource(R.drawable.sticker_hover);
            }

            @Override
            public void onProductPutdown(Work work) {
                ((TextView) findViewById(R.id.titleLabel)).setText("SZUKA SZUKA");
                findViewById(R.id.authorLabel).setVisibility(View.INVISIBLE);
            }
        });
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
