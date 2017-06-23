package pl.zpi.museumguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //set listeners
        findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:51.1109817,17.049153?q=Muzeum Narodowe"));
                intent.setPackage("com.google.android.apps.maps");

                startActivity(intent);
            }
        });

        findViewById(R.id.phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:713435643"));
                startActivity(intent);
            }
        });

        findViewById(R.id.web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://mnwr.art.pl";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
