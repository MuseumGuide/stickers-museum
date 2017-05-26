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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        amountStickersInRooms = countStickersInRooms();

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
        createFlipperAnimation();

        lastOption = 0;

        radarManager.setListener(new RadarManager.Listener() {
            @Override
            public void onProductPickup(Work work, List<String> allStickers)
            {
                //todo work out function to choose right room
                switch(work.getBeacon().getRoom())
                {
                    case 1:
                        if(lastOption != 1)
                        {
                            flipper.setDisplayedChild(1);

                            sticker1 = (ImageView) findViewById(R.id.sticker_21);
                            sticker2 = (ImageView) findViewById(R.id.sticker_22);
                            sticker3 = (ImageView) findViewById(R.id.sticker_23);
                            sticker4 = (ImageView) findViewById(R.id.sticker_24);

                            ImageView[] iv_stickers = {sticker1, sticker2, sticker3, sticker4};

                            savePointsOnMap(work, iv_stickers);
                            setNearSticker(work);
                            showNotice(work);

                            lastOption = 1;
                        }
                        break;
                    case 2:
                        if(lastOption != 2)
                        {
                            flipper.setDisplayedChild(2);

                            sticker1 = (ImageView) findViewById(R.id.sticker_1);
                            sticker2 = (ImageView) findViewById(R.id.sticker_2);
                            sticker3 = (ImageView) findViewById(R.id.sticker_3);
                            sticker4 = (ImageView) findViewById(R.id.sticker_4);

                            ImageView[] iv_stickers = {sticker1, sticker2, sticker3, sticker4};

                            savePointsOnMap(work, iv_stickers);
                            setNearSticker(work);
                            showNotice(work);

                            lastOption = 2;
                        }
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
        //todo close code in one method and add to cases in switch

        pointsOnMap = new HashMap<>();

        int amountAdded = 0;
        for(Beacon b : dataRepository.getBeacons())
        {
            if(b.getRoom() == work.getBeacon().getRoom() && amountAdded < amountStickersInRooms[work.getBeacon().getRoom()])
            {
                pointsOnMap.put(b.getUuid(), iv_stickers[amountAdded]);
                amountAdded++;
            }
        }
    }

    private void createFlipperAnimation()
    {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(600);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(200);
        fadeOut.setDuration(600);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);

        flipper.setInAnimation(fadeIn);
        flipper.setOutAnimation(fadeOut);
    }

    private int[] countStickersInRooms()
    {
        //todo create counting function

        int[] aa = {4, 4, 4, 4};

        return aa;
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
