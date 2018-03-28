/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    private double pricePerCup = 5.0;
    private double chocolatePrice = 2.0;
    private double whippedCreamPrice = 1.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippingCream = findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippingCream.isChecked();

        CheckBox chocolateSyrup = findViewById(R.id.chocolate_syrup);
        boolean hasChocolate = chocolateSyrup.isChecked();

        EditText name = findViewById(R.id.name);
        String customerName = name.getText().toString();

        String priceMessage = createOrderSummary(quantity, hasWhippedCream, hasChocolate, customerName);

        /* Send an email */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "kennethwty93@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order from " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage); // Email content
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayPrice(priceMessage); Code not needed since it is redirected to the email app
    }

    /**
     * This method creates an order summary including names, quantity, and total price
     * The method does something unnecessary, but this is for practice only
     */
    private String createOrderSummary(int cupOfJoe, boolean addWhippedCream, boolean addChocolate, String name)
    {
        String result = "Name: " + name + "\nQuantity: " + cupOfJoe;
        result += "\nAdd Whipped Cream: " + addWhippedCream;
        result += "\nAdd Chocolate Syrup: " + addChocolate;
        result += "\nTotal: $" + calculatePrice(addWhippedCream, addChocolate) + "\nThank You!";
        return result;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int numberOfCoffee) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffee);
    }

    /**
     * This method calculates the price based on the number of cups of coffee selected
     */
    /*
    private void displayPrice(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */

    /**
     * This method increments the number of coffee
     */
    public void increment(View view) {
        ++quantity;
        display(quantity);
    }

    /**
     * This is the decrement method for the minus sign
     */
    public void decrement(View view) {
        if (quantity > 0) {
            --quantity;
            display(quantity);
        } else {
            quantity = 0;
            Toast.makeText(this, "Quantity must be 0 and above.", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculatePrice(boolean whippedCreamP, boolean chocoP) {
        double total = quantity * pricePerCup;

        if(whippedCreamP)
            total += quantity; /* Add a dollar per cup (assuming all with whipped cream) */

        if(chocoP) {
            total = total + quantity * 2; /* Add two dollars per cup */
        }

        return total;
    }
}