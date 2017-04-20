package pl.zpi.museumguide.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import pl.zpi.museumguide.R;

public class Room extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        FrameLayout background = (FrameLayout) findViewById(R.id.map);

        background.setBackgroundResource(R.drawable.i_map);
    }


}
