package pl.burno.sensor.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import pl.burno.sensor.R;

public class MainActivity extends AppCompatActivity implements MainView
{
    @BindView(R.id.background)
    View background;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setBackground(int color)
    {
        background.setBackgroundColor(color);
    }
}
