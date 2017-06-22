package pl.zpi.museumguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> languages = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, new String[]{"Polski", "English"});
        spinner.setAdapter(languages);
    }
}
