package pl.burno.sensor.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.burno.sensor.R;

public class MainActivity extends AppCompatActivity implements MainView
{
    @BindView(R.id.background)
    View background;

    @BindView(R.id.transparentBackground)
    View transparentBackground;

    @BindView(R.id.infoBox1)
    View infoBox1;

    @BindView(R.id.infoBox2)
    View infoBox2;

    private MainPresenter mPresenter;

    private float boxWidth;
    private float boxHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mPresenter = new MainPresenter(this);

        boxWidth = getResources().getDimension(R.dimen.info_box_width);
        boxHeight = getResources().getDimension(R.dimen.info_box_height);

        setUpView();
    }

    private void setUpView()
    {
        infoBox1.setPivotX(boxWidth / 2);
        infoBox1.setPivotY(boxHeight / 8);

        infoBox2.setPivotX(boxWidth / 2);
        infoBox2.setPivotY(boxHeight- boxHeight / 8);

        infoBox1.setOnTouchListener((view, motionEvent) -> {
            infoBox1.setX(motionEvent.getRawX() - boxWidth / 2);
            infoBox1.setY(motionEvent.getRawY() - getResources().getDimension(R.dimen.status_bar));
            return true;
        });

        infoBox2.setOnTouchListener((view, motionEvent) -> {
            infoBox2.setX(motionEvent.getRawX() - boxWidth / 2);
            infoBox2.setY(motionEvent.getRawY() - boxHeight - getResources().getDimension(R.dimen.status_bar));
            return true;
        });
    }

    @Override
    public void setBackground(int color)
    {
        background.setBackgroundColor(color);
    }

    @Override
    public void setTransparentBackground(int color)
    {
        transparentBackground.setBackgroundColor(color);
    }

    @Override
    public void rotateInfoBoxes(int rotationSpeed, float degrees)
    {
        infoBox1.setRotation(rotationSpeed * degrees);
        infoBox2.setRotation(rotationSpeed * -1 * degrees);
    }

    @Override
    public void showErrorNoSensor(MainPresenter.MySensor mySensor)
    {
        String message = "";
        switch (mySensor)
        {
            case LIGHT:
                message = getString(R.string.light_detector);
                break;
            case ROTATION:
                message = getString(R.string.rotation_sensor);
                break;
            case ACCELEROMETER:
                message = getString(R.string.accelerometer_sensor);
                break;
        }
        Toast.makeText(MainActivity.this, String.format(getString(R.string.no_sensor), message), Toast.LENGTH_SHORT).show();
    }
}
