package pl.zpi.museumguide;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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
import uk.co.senab.photoview.PhotoViewAttacher;

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
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        background = (RelativeLayout) findViewById(R.id.map);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item);

    }

    private void prepareTabLayout()
    {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.roomLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.Close);

        setSupportActionBar(mToolbar);
        mDrawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.syncState();

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
            setGalleryFragment(work.getAuthor());
        }
    }

    private void setGalleryFragment(Author auth)
    {
        mSectionsPagerAdapter.setAuthor(auth);
        GridView grid = (GridView) findViewById(R.id.gridView);
        grid.invalidateViews();
    }

    private void setAuthorInfoFragment(Work work)
    {
        TextView authorName = (TextView) findViewById(R.id.authorNameFrag);
        Author author = work.getAuthor();
        authorName.setText(author.getFirstname() + " " + author.getLastname());

        ImageView authorImage = (ImageView) findViewById(R.id.authorImageFrag);

        authorImage.setImageResource(work.getAuthor().getIdDrawable());

        TextView authorWorks = (TextView) findViewById(R.id.authorWorks);

        String out_authorWorks = "";
        for(Work obj : work.getAuthor().getWorks())
            out_authorWorks += "-  " + obj.getTitle() + "\n";

        authorWorks.setText(out_authorWorks);
    }

    private void setWorkInfoFragment(Work work)
    {
        TextView description = (TextView) findViewById(R.id.workDescriptionFragment);
        description.setText(work.getDescription());

        description.setTextColor(getResources().getColor(R.color.colorLight));
        //// TODO: 2017-05-11 set same color of text in all fragments

        ImageView workImage = (ImageView) findViewById(R.id.workImageFrag);

        workImage.setImageResource(work.getIdDrawable());

        PhotoViewAttacher photoAttacher = new PhotoViewAttacher(workImage);
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
