package tw.com.hycleo.keypad.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import tw.com.hycleo.keypad.R;
import tw.com.hycleo.keypad.view.KeypadView;

public class MainActivity extends ActionBarActivity {

    private KeypadView keypadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keypadView = (KeypadView)findViewById(R.id.keypadView);
        keypadView.initDefaultKeypadItem();
    }

}
