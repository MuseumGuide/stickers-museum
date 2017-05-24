package pl.zpi.museumguide.bottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import pl.zpi.museumguide.R;
import pl.zpi.museumguide.data.domain.Author;

/**
 * Created by verat on 2017-04-27.
 */

public class GalleryFrag extends Fragment {


    ImageAdapter imageAdapter;
    public void setAuthor(Author auth)
    {
        imageAdapter.setAuthor(auth);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.author_gallery, container, false);
        view.getContext().setTheme(R.style.BottomSheetTheme);
        GridView gridview = (GridView) view.findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(getContext());
        gridview.setAdapter(imageAdapter);
        return view;
    }
}


