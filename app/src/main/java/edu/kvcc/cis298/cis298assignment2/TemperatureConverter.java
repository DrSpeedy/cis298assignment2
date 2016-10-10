package edu.kvcc.cis298.cis298assignment2;

import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

// TODO: CLEAN!
public class TemperatureConverter extends AppCompatActivity {

    // Tag to be used for log messages
    public final String TAG = this.getClass().getSimpleName();
    // Key used to retrieve relevant data from the saved instance
    // bundle to repopulate fields after the activity is rotated.
    private final String KEY_ROTATE_CHECKED_FROM = "rotation.convertfrom";
    private final String KEY_ROTATE_CHECKED_TO = "rotation.convertto";
    private final String KEY_ROTATE_INPUT = "rotation.input";

    private TempScaleContainer mTempScaleContainer;
    private TextView mFormulaText;
    private EditText mTemperatureInput;
    private RadioGroup mConvertFromGroup;
    private RadioGroup mConvertToGroup;
    private Button mConvertButton;
    private TextView mSolutionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_converter);

        // If the instance has been saved before creation,
        // restore all saved data.
        if (savedInstanceState != null) {
            restoreSavedInstanceState(savedInstanceState);
        }

        // Create a new TempScaleContainer and add available conversion scales,
        // along with the temperature at which water boils and freezes. This data
        // will then be used to generate a linear equation to allow for the conversion.
        mTempScaleContainer = new TempScaleContainer(TemperatureConverter.this);
        mTempScaleContainer.add(R.string.fahrenheit, 212, 32);
        mTempScaleContainer.add(R.string.celsius, 100, 0);
        mTempScaleContainer.add(R.string.kelvin, 373.1339, 273.15);
        mTempScaleContainer.add(R.string.rankin, 671.64102, 491.67);

        // Pull in activity widget resource handles
        mTemperatureInput = (EditText) findViewById(R.id.temperature_input);
        mConvertFromGroup = (RadioGroup) findViewById(R.id.convert_from);
        mConvertToGroup = (RadioGroup) findViewById(R.id.convert_to);
        mConvertButton = (Button) findViewById(R.id.convert_button);
        mSolutionText = (TextView) findViewById(R.id.conversion_solution);
        mFormulaText = (TextView) findViewById(R.id.conversion_formula);

        mConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                final String inputString = mTemperatureInput.getText().toString();
                final double input;
                final RadioButton mSelectionFrom;
                final RadioButton mSelectionTo;
                try {
                    input = Double.parseDouble(inputString);
                    // Verify both have an option selected
                    mSelectionFrom = (RadioButton) findViewById(mConvertFromGroup.getCheckedRadioButtonId());
                    mSelectionTo = (RadioButton) findViewById(mConvertToGroup.getCheckedRadioButtonId());

                    // Convert temps
                    TempScale convertFrom = mTempScaleContainer.get(mSelectionFrom.getText());
                    TempScale convertTo = mTempScaleContainer.get(mSelectionTo.getText());

                    ScaleTransformer transformer = new ScaleTransformer(convertFrom, convertTo);
                    double solution = transformer.convert(input);

                    // Update text
                    mSolutionText.setText("" + solution + " deg " + convertTo.mName);
                    mFormulaText.setText(transformer.toString());
                }
                catch(Exception e) {
                    Log.d(TAG, "Failed to convert user input!");
                    Toast.makeText(TemperatureConverter.this, "Error: Bad input!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void restoreSavedInstanceState(Bundle savedInstanceState) {
        try {
            // Retrieve saved data from the savedInstanceState bundle
            final int convertedFromIdx = savedInstanceState.getInt(KEY_ROTATE_CHECKED_FROM);
            final int convertedToIdx = savedInstanceState.getInt(KEY_ROTATE_CHECKED_TO);
            final String temperatureInput = savedInstanceState.getString(KEY_ROTATE_INPUT);

            // Get handles for the previously selected radio buttons
            RadioButton convertFromRadioButton = (RadioButton) findViewById(convertedFromIdx);
            RadioButton convertToRadioButton = (RadioButton) findViewById(convertedToIdx);
            // And then re-toggle them on
            convertFromRadioButton.toggle();
            convertToRadioButton.toggle();
            // Restore the saved input
            mTemperatureInput.setText(temperatureInput);
        } catch (Exception e) {
            // If for some reason an exception is thrown by the above code,
            // log error message to logcat as well as a toast on the device.
            String msg = "Failed restoreSavedInstanceState()";
            Log.d(TAG, msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
    }

    // Over ride the onSaveInstanceState() callback so that we may use it as a hook
    // to save our current activity instance data before the activity is paused/stopped.
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt(KEY_ROTATE_CHECKED_FROM, mConvertFromGroup.getCheckedRadioButtonId());
        outState.putInt(KEY_ROTATE_CHECKED_TO, mConvertToGroup.getCheckedRadioButtonId());
        outState.putString(KEY_ROTATE_INPUT, mTemperatureInput.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_temperature_converter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
