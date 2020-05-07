package com.dellead.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ViewHolder mViewHolder = new ViewHolder();
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findInterfaceElements();
        incrementQuantity();
        decrementQuantity();
        orderButton();
    }

    private void orderButton(){
        this.mViewHolder.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalNumber =  quantity * 15;
                mViewHolder.txtTotal.setText("Your total is $" + totalNumber + "! Thanks for buying with us");
            }
        });
    }

    private void incrementQuantity() {
        this.mViewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quantity++;
                //mViewHolder.txtQuantity.setText(String.valueOf(quantity));
                if (quantity < 10) {
                    quantity++;
                    mViewHolder.txtQuantity.setText(String.valueOf(quantity));
                    mViewHolder.btnMinor.setEnabled(true);
                } else {
                    mViewHolder.btnMore.setEnabled(false);
                    mViewHolder.btnMinor.setEnabled(true);
                }
            }
        });
    }

    private void decrementQuantity() {
        this.mViewHolder.btnMinor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // quantity--;
                // mViewHolder.txtQuantity.setText(String.valueOf(quantity));
                if (quantity > 1) {
                    quantity--;
                    mViewHolder.btnMore.setEnabled(true);
                    mViewHolder.txtQuantity.setText(String.valueOf(quantity));
                } else {
                    mViewHolder.btnMinor.setEnabled(false);
                    mViewHolder.btnMore.setEnabled(true);
                }
            }
        });
    }



    private void findInterfaceElements() {
        this.mViewHolder.txtName = findViewById(R.id.txt_name);
        this.mViewHolder.txtPrice = findViewById(R.id.txt_price);
        this.mViewHolder.btnMinor = findViewById(R.id.btn_minor);
        this.mViewHolder.txtQuantity = findViewById(R.id.txt_quantity);
        this.mViewHolder.btnMore = findViewById(R.id.btn_more);
        this.mViewHolder.btnOrder = findViewById(R.id.btn_order);
        this.mViewHolder.txtTotal =  findViewById(R.id.txt_total);
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtPrice;
        Button btnMinor;
        TextView txtQuantity;
        Button btnMore;
        Button btnOrder;
        TextView txtTotal;

    }
}
