package pl.zpi.museumguide;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
    private Map<String, ImageView> pointsOnMap;
    private ListView lv;
    private ImageView sticker1;
    private ImageView sticker2;
    private ImageView sticker3;
    private ImageView sticker4;
    private RelativeLayout background;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewFlipper flipper;
    private int lastOption;
    private int[] amountStickersInRooms;

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private boolean switchingRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        //todo amount of rooms
        amountStickersInRooms = new int[4];
        switchingRoom = false;

        background = (RelativeLayout) findViewById(R.id.map);
        background.setBackgroundResource(R.drawable.gui_map);

        dataRepository = new DataPreparerRepository();
        Map<Beacon, Work> products = new HashMap<>();

        //todo get from repository
        Beacon b1 = dataRepository.getBeacon(DataPreparerRepository.beacon1UUID);
        Beacon b2 = dataRepository.getBeacon(DataPreparerRepository.beacon2UUID);

        //todo resolve many works on one beacon
        products.put(b1, b1.getWork().get(0));
        products.put(b2, b2.getWork().get(0));
        radarManager = new RadarManager(this, products);

        flipper = (ViewFlipper) findViewById(R.id.flipper);

        lastOption = 0;

        radarManager.setListener(new RadarManager.Listener() {
            @Override
            public void onProductPickup(Work work, List<String> allStickers)
            {
                //todo work out function to choose right room
                switch(work.getBeacon().getRoom())
                {
                    case 1:
                        if(!switchingRoom)
                            createFlipperAnimation(1, false);

                        if(lastOption != 1)
                        {
                            flipper.setDisplayedChild(1);
                            lastOption = 1;
                        }

                        sticker1 = (ImageView) findViewById(R.id.sticker_21);
                        sticker2 = (ImageView) findViewById(R.id.sticker_22);
                        sticker3 = (ImageView) findViewById(R.id.sticker_23);
                        sticker4 = (ImageView) findViewById(R.id.sticker_24);

                        amountStickersInRooms[0] = 4;

                        //todo change this
                        ImageView[] iv_stickers1 = {sticker1, sticker2, sticker3, sticker4};

                        savePointsOnMap(work, iv_stickers1);
                        setNearSticker(work);
                        showNotice(work);

                        createFlipperAnimation(1, true);
                        switchingRoom = true;

                        break;

                    case 2:
                        if(!switchingRoom)
                            createFlipperAnimation(2, false);

                        if(lastOption != 2)
                        {
                            flipper.setDisplayedChild(2);
                            lastOption = 2;
                        }

                        sticker1 = (ImageView) findViewById(R.id.sticker_1);
                        sticker2 = (ImageView) findViewById(R.id.sticker_2);
                        sticker3 = (ImageView) findViewById(R.id.sticker_3);
                        sticker4 = (ImageView) findViewById(R.id.sticker_4);

                        amountStickersInRooms[1] = 4;

                        ImageView[] iv_stickers2 = {sticker1, sticker2, sticker3, sticker4};

                        savePointsOnMap(work, iv_stickers2);
                        setNearSticker(work);
                        showNotice(work);

                        createFlipperAnimation(2, true);
                        switchingRoom = true;

                        break;
                }
            }

            @Override
            public void onProductPutdown(Work work)
            {

            }
        });
        prepareTabLayout();
    }

    private void setNearSticker(Work work)
    {
        sticker1.setImageResource(R.drawable.gui_sticker);
        sticker2.setImageResource(R.drawable.gui_sticker);
        sticker3.setImageResource(R.drawable.gui_sticker);
        sticker4.setImageResource(R.drawable.gui_sticker);

        // best
        ImageView near = pointsOnMap.get(String.valueOf(work.getBeacon().getUuid()));
        near.setImageResource(R.drawable.gui_sticker_hover);
    }

    private void savePointsOnMap(Work work, ImageView[] iv_stickers)
    {
        pointsOnMap = new HashMap<>();

        int amountAdded = 0;
        for(Beacon b : dataRepository.getBeacons())
        {
            if(b.getRoom() == work.getBeacon().getRoom() && amountAdded < amountStickersInRooms[work.getBeacon().getRoom() - 1])
            {
                pointsOnMap.put(b.getUuid(), iv_stickers[amountAdded]);
                amountAdded++;
            }
        }
    }

    private void createFlipperAnimation(int option, boolean isRoom)
    {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setStartOffset(1000);
        fadeIn.setDuration(2000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(2000);

        Animation zoom;

        if(!isRoom)
        {
            switch (option)
            {
                case 1:
                    zoom = AnimationUtils.loadAnimation(this, R.anim.zoom1);
                    zoom.setDuration(2100);
                    flipper.setOutAnimation(zoom);
                    break;

                case 2:
                    zoom = AnimationUtils.loadAnimation(this, R.anim.zoom2);
                    zoom.setDuration(2100);
                    flipper.setOutAnimation(zoom);
                    break;
            }
        }

        if(isRoom)
            flipper.setOutAnimation(fadeOut);

        flipper.setInAnimation(fadeIn);
    }

    private void prepareTabLayout()
    {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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
