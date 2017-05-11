package pl.zpi.museumguide.bottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.zpi.museumguide.R;

/**
 * Created by verat on 2017-04-27.
 */

public class AuthorInfoFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.author_info, container, false);
        view.getContext().setTheme(R.style.BottomSheetTheme);
        return view;
    }
}
