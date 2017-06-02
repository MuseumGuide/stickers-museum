package pl.zpi.museumguide;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class Room extends AppCompatActivity {

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


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        background = (RelativeLayout) findViewById(R.id.map);
        background.setBackgroundResource(R.drawable.gui_map);

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
            public void onProductPickup(Work work, List<String> allStickers) {
                sticker1.setImageResource(R.drawable.gui_sticker);
                sticker2.setImageResource(R.drawable.gui_sticker);

                ImageView near = pointsOnMap.get(String.valueOf(work.getBeacon().getUuid()));
                near.setImageResource(R.drawable.gui_sticker_hover);

                showNotice(work);
            }

            @Override
            public void onProductPutdown(Work work) {
                sticker1.setImageResource(R.drawable.gui_sticker);
                sticker2.setImageResource(R.drawable.gui_sticker);
            }
        });


        mToolbar = (Toolbar) findViewById(R.id.navigation_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set click listener on all toolbar (not needed)
        findViewById(R.id.navigation_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT, true);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.room_layout);
        NavigationView n = (NavigationView) findViewById(R.id.room_nav);
        n.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    ////.......

                }
                mDrawerLayout.closeDrawers();  // CLOSE DRAWER
                return true;
            }
        });


//        mDrawerLayout = (DrawerLayout) findViewById(R.id.room_layout);
//        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
//        mToggle.setToolbarNavigationClickListener(view -> {
//            int x =0;
//        });
//        mDrawerLayout.addDrawerListener(mToggle);
//
//
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Map Activity");

        prepareTabLayout();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        //mToggle.syncState();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mToggle.onConfigurationChanged(newConfig);
//    }


    //here the drawer is opened
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareTabLayout() {
//        mToolbar = (Toolbar) findViewById(R.id.navigation_toolbar);
//
//        setSupportActionBar(mToolbar);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.room_layout);
//        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
//
//        mDrawerLayout.addDrawerListener(mToggle);
//
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Map Activity");


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void showNotice(Work work) {
        View bottomSheet = findViewById(R.id.design_bottom_sheet);

        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN)
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            TextView title = (TextView) findViewById(R.id.MapWorkTitle);
            title.setText(work.getTitle());
            setWorkInfoFragment(work);
            setAuthorInfoFragment(work);
            setGalleryFragment(work.getAuthor());
        }
    }


    private void setGalleryFragment(Author auth) {
        mSectionsPagerAdapter.setAuthor(auth);
        GridView grid = (GridView) findViewById(R.id.gridView);
        grid.invalidateViews();
    }

    private void setAuthorInfoFragment(Work work) {
        TextView authorName = (TextView) findViewById(R.id.authorNameFrag);
        Author author = work.getAuthor();
        authorName.setText(author.getFirstname() + " " + author.getLastname());

        ImageView authorImage = (ImageView) findViewById(R.id.authorImageFrag);

        authorImage.setImageResource(work.getAuthor().getIdDrawable());

        TextView authorWorks = (TextView) findViewById(R.id.authorWorks);

        String out_authorWorks = "";
        for (Work obj : work.getAuthor().getWorks())
            out_authorWorks += "-  " + obj.getTitle() + "\n";

        authorWorks.setText(out_authorWorks);
    }

    private void setWorkInfoFragment(Work work) {
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
