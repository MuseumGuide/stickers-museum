package pl.zpi.museumguide.bottomsheet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pl.zpi.museumguide.data.domain.Author;

/**
 * Created by verat on 2017-05-05.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public GalleryFrag gallery;

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new WorkInfoFrag();
            case 1:
                return new AuthorInfoFrag();
            case 2:
            {
                gallery = new GalleryFrag();
                return gallery;
            }
        }
        return null;
    }

    public void setAuthor(Author auth)    {        gallery.setAuthor(auth);    }

    @Override
    public int getCount() { return 3;}

    @Override
    public CharSequence getPageTitle(int position)
    {
        return null;
    }
}