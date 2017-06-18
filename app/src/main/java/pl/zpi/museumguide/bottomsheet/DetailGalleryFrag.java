package pl.zpi.museumguide.bottomsheet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import pl.zpi.museumguide.R;
import pl.zpi.museumguide.data.domain.Author;


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

        Bundle bundle = getArguments();

        int position = bundle.getInt("selected_image");

        imageView.setImageResource(R.drawable.data_glowa);
        //imageView.setImageResource(new ImageAdapter(getActivity().getApplicationContext()).getDrawable(position));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity().getSupportFragmentManager();

                if(fm.getBackStackEntryCount() > 0){
                    fm.popBackStack();
                }


            }
        });
        return view;
    }





}

