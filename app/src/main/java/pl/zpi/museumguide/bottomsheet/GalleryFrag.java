package pl.zpi.museumguide.bottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import pl.zpi.museumguide.R;

/**
 * Created by verat on 2017-04-27.
 */

public class GalleryFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.author_gallery, container, false);
        GridView gridview = (GridView) v.findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(getContext()));
        return v;
    }
}


