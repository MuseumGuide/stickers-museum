package pl.zpi.museumguide.bottomsheet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import pl.zpi.museumguide.R;
import pl.zpi.museumguide.data.DataPreparerRepository;

import uk.co.senab.photoview.PhotoViewAttacher;



public class DetailGalleryFrag extends Fragment {

    public DetailGalleryFrag() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_gallery, container, false);
        getContext().setTheme(R.style.BottomSheetTheme);

        ImageView imageView = (ImageView) view.findViewById(R.id.detailPhoto);

        Bundle bundle = getArguments();

        int position = bundle.getInt("selected_img");

        imageView.setImageResource(new DataPreparerRepository().getAllWorks().get(position).getIdDrawable());
        PhotoViewAttacher photoAttacher = new PhotoViewAttacher(imageView);

        return view;
    }
}

