/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffee = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(message);
//    }
    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
//        Log.v("Main","hasWhippedCream"+hasWhippedCream);
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price ,hasWhippedCream, hasChocolate, name);


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
//        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"foo@bar.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject)+ name);
        intent.putExtra(Intent.EXTRA_TEXT   , priceMessage);
        try {
            startActivity(Intent.createChooser(intent, "Choose email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Log.v("Main","no email client is installed");
            // handle edge case where no email client is installed
        }
//        displayMessage(priceMessage);
    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;
        if (addWhippedCream){
            basePrice = basePrice+1;
        }
        if (addChocolate){
            basePrice = basePrice+2;
        }
        return numberOfCoffee * basePrice;}
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        String priceMessage = getString(R.string.order_summary_name)+ name;
        priceMessage += "\n"+getString(R.string.order_summary_whipped_cream)+ hasWhippedCream;
        priceMessage += "\n"+getString(R.string.order_summary_chocolate)+ hasChocolate;
        priceMessage += "\n" +getString(R.string.quantity)+ numberOfCoffee;
        priceMessage += "\n"+getString(R.string.order_summary_price)+": $" + price;
        priceMessage += "\n" + getString(R.string.thank_you) ;
        return priceMessage;

    }
    public void increment(View view) {
        if (numberOfCoffee == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        numberOfCoffee = numberOfCoffee +1;
        display(numberOfCoffee);


    }
    public void decrement(View view) {
        if (numberOfCoffee == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        numberOfCoffee = numberOfCoffee-1;
        display(numberOfCoffee );
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }
}
