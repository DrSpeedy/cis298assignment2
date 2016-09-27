package edu.kvcc.cis298.cis298assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Hashtable;

// TODO: CLEAN!
public class TemperatureConverter extends AppCompatActivity {

    private Hashtable<String, TempScale> TempScales;

    // TODO: Abstract TempScales to its own class
    private Hashtable initTempScales() {
        Hashtable tempScales = new Hashtable();
        tempScales.put(getResources().getString(R.string.fahrenheit), new TempScale("Fahrenheit", 212, 32));
        tempScales.put(getResources().getString(R.string.celsius), new TempScale("Celsius", 100, 0));
        tempScales.put(getResources().getString(R.string.kelvin), new TempScale("Kelvin", 373.1339, 273.15));
        tempScales.put(getResources().getString(R.string.rankin), new TempScale("Rankine", 671.64102, 491.67));

        return tempScales;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_converter);

        TempScales = initTempScales();

        // Pull in activity widget resource handles
        final EditText mTemperatureInput = (EditText) findViewById(R.id.temperature_input);
        final RadioGroup mConvertFromGroup = (RadioGroup) findViewById(R.id.convert_from);
        final RadioGroup mConvertToGroup = (RadioGroup) findViewById(R.id.convert_to);
        final Button mConvertButton = (Button) findViewById(R.id.convert_button);
        final TextView mSolutionText = (TextView) findViewById(R.id.conversion_solution);
        final TextView mFormulaText = (TextView) findViewById(R.id.conversion_formula);

        mConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                final double input = Double.parseDouble(mTemperatureInput.getText().toString());
                final RadioButton mSelectionFrom = (RadioButton) findViewById(mConvertFromGroup.getCheckedRadioButtonId());
                final RadioButton mSelectionTo = (RadioButton) findViewById(mConvertToGroup.getCheckedRadioButtonId());

                // Convert temps
                TempScale convertFrom = TempScales.get(mSelectionFrom.getText());
                TempScale convertTo = TempScales.get(mSelectionTo.getText());

                ScaleTransformer transformer = new ScaleTransformer(convertFrom, convertTo);
                double solution = transformer.convert(input);

                // Update text
                mSolutionText.setText("" + solution + " deg " + convertTo.Name);
                mFormulaText.setText(transformer.toString());
            }
        });
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
