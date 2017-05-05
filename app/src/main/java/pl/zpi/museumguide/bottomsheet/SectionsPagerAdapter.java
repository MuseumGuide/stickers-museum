package pl.zpi.museumguide.bottomsheet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by verat on 2017-05-05.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new WorkInfoFrag();
            case 1:
                return new AuthorInfoFrag();
            case 2:
                return new GalleryFrag();
        }
        return null;
    }

    @Override
    public int getCount() { return 3;}

    @Override
    public CharSequence getPageTitle(int position)
    {
        //// TODO: 2017-05-05 Change text to icons
        switch (position) {
            case 0:
                return "Work";
            case 1:
                return "Author";
            case 2:
                return "Gallery";
        }
        return null;
    }
}