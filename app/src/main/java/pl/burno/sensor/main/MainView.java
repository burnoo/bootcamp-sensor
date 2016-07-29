package pl.burno.sensor.main;

public interface MainView
{
    void setBackground(int color);

    Object getSystemService(String name);

    void setTransparentBackground(int color);

    void rotateInfoBoxes(int rotationSpeed, float degrees);

    void showErrorNoSensor(MainPresenter.MySensor mySensor);
}
