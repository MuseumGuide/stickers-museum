package pl.zpi.museumguide.bottomsheet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pl.zpi.museumguide.R;


public class DetailGalleryFrag extends Fragment {

    //private OnFragmentInteractionListener mListener;

    public DetailGalleryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_gallery, container, false);
        getContext().setTheme(R.style.BottomSheetTheme);

        ImageView imageView = (ImageView) view.findViewById(R.id.detailPhoto);

        imageView.setImageResource(R.drawable.data_glowa);
        return view;
    }



}

