package pl.zpi.museumguide;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.estimote.sdk.SystemRequirementsChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.zpi.museumguide.bottomsheet.SectionsPagerAdapter;
import pl.zpi.museumguide.connection.RadarManager;
import pl.zpi.museumguide.data.DataPreparerRepository;
import pl.zpi.museumguide.data.DataRepository;
import pl.zpi.museumguide.data.domain.Author;
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


    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        background = (RelativeLayout) findViewById(R.id.map);

        findViewById(R.id.background_room).setBackgroundColor(Color.parseColor("#e1e1e1"));
        findViewById(R.id.main_room).setBackgroundColor(Color.parseColor("#e1e1e1"));

        background.setBackgroundResource(R.drawable.mapa);

        sticker1 = (ImageView) findViewById(R.id.sticker_1);
        sticker2 = (ImageView) findViewById(R.id.sticker_2);
        sticker3 = (ImageView) findViewById(R.id.sticker_3);
        sticker4 = (ImageView) findViewById(R.id.sticker_4);

        dataRepository = new DataPreparerRepository();

        Map<Beacon, Work> products = new HashMap<>();

        //todo get from repository
        Beacon b1 = dataRepository.getBeacon(DataPreparerRepository.beacon1UUID);
        Beacon b2 = dataRepository.getBeacon(DataPreparerRepository.beacon2UUID);

        pointsOnMap = new HashMap<>();
        pointsOnMap.put(DataPreparerRepository.beacon1UUID, sticker1);
        pointsOnMap.put(DataPreparerRepository.beacon2UUID, sticker2);

        //todo resolve many works on one beacon
        products.put(b1, b1.getWork().get(0));
        products.put(b2, b2.getWork().get(0));
        radarManager = new RadarManager(this, products);
        String idLastSticker = "";

        radarManager.setListener(new RadarManager.Listener() {
            @Override
            public void onProductPickup(Work work, List<String> allStickers)
            {
                sticker1.setImageResource(R.drawable.sticker);
                sticker2.setImageResource(R.drawable.sticker);

                ImageView near = pointsOnMap.get(String.valueOf(work.getBeacon().getUuid()));
                near.setImageResource(R.drawable.sticker_hover);

                showNotice(work);
            }

            @Override
            public void onProductPutdown(Work work)
            {
                sticker1.setImageResource(R.drawable.sticker);
                sticker2.setImageResource(R.drawable.sticker);
            }
        });
        prepareTabLayout();
    }

    private void prepareTabLayout()
    {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.container);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void showNotice(Work work)
    {
        View bottomSheet = findViewById(R.id.design_bottom_sheet);

        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        if(behavior.getState() == BottomSheetBehavior.STATE_HIDDEN)
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        if(behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
        {
            TextView title = (TextView) findViewById(R.id.MapWorkTitle);
            title.setText(work.getTitle());
            setWorkInfoFragment(work);
            setAuthorInfoFragment(work);
            setGalleryFragment(work);
        }
    }

    private void setGalleryFragment(Work work)
    {
        //// TODO: 2017-05-05 Fill gallery titles
    }

    private void setAuthorInfoFragment(Work work)
    {
        TextView authorName = (TextView) findViewById(R.id.authorNameFrag);
        Author author = work.getAuthors().get(0);
        authorName.setText(author.getFirstname() + " " + author.getLastname());


        ImageView authorImage = (ImageView) findViewById(R.id.authorImageFrag);
        //// TODO: 2017-05-04 implement Author image field
        authorImage.setImageResource(R.drawable.logo);


        TextView authorInfo = (TextView) findViewById(R.id.authorInfoFrag);
        //// TODO: 2017-05-04 implement Author info field (String?)
        authorInfo.setText("Author Info");
    }

    private void setWorkInfoFragment(Work work)
    {
        TextView description = (TextView) findViewById(R.id.workDescriptionFragment);
        description.setText(work.getInformation().get(0).getText());

        ImageView workImage = (ImageView) findViewById(R.id.workImageFrag);
        //// TODO: 2017-05-04 implement Work image field
        workImage.setImageResource(android.R.drawable.sym_def_app_icon);
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
