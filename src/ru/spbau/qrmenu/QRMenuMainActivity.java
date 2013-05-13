package ru.spbau.qrmenu;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import ru.spbau.qrmenu.entities.RestaurantMenuItem;
import ru.spbau.qrmenu.entities.RestaurantTable;

import java.util.ArrayList;
import java.util.List;

public class QRMenuMainActivity extends ListActivity {

    private static final int SCAN_QR_CODE_REQUEST_CODE = 0;
    public static final String TAG = "ru.spbau.qrmenu.QRMenuMainActivity";

    private List<RestaurantMenuItem> orders;

    private ArrayAdapter<RestaurantMenuItem> listAdapter;

    private RestaurantTable table;

    public QRMenuMainActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTable();
        setContentView(R.layout.main);
        orders = new ArrayList<RestaurantMenuItem>();
        setUpRestaurantNameView();
        setUpListAdapter();
    }

    private void setUpRestaurantNameView() {
        if (table.getRestaurantName() == null) {
            return;
        }
        TextView restaurantName = (TextView) findViewById(R.id.restaurant_name);
        restaurantName.setText(table.getRestaurantName());
    }

    private void setUpListAdapter() {
        listAdapter = new ArrayAdapter<RestaurantMenuItem>(this, R.layout.main_menu_list_item, orders) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.main_menu_list_item, parent, false);
                TextView itemText = (TextView) view.findViewById(R.id.itemText);
                itemText.setText(orders.get(position).getName());
                TextView priceText = (TextView) view.findViewById(R.id.priceText);
                priceText.setText(priceFormat(QRMenuMainActivity.this.orders.get(position).getCost()));
                ImageButton deleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orders.remove(position);
                        refreshListView();
                    }
                });
                return view;
            }
        };
        setListAdapter(listAdapter);
        refreshListView();
    }

    private void setTable() {
        Intent intent = getIntent();
        String jsonTable = intent.getStringExtra(QRMenuStartActivity.EXTRA_MESSAGE);
        Log.i(TAG, "JSON: " + jsonTable);
        this.table = EntitiesSerializationHelper.parseTable(jsonTable);
    }

    private String priceFormat(double price) {
        return String.format("%1$,.2f", price);
    }

    @SuppressWarnings("UnusedParameters")
    public void onScanCodeClick(View view) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, SCAN_QR_CODE_REQUEST_CODE);
        refreshListView();
    }

    @SuppressWarnings("UnusedParameters")
    public void onMakeOrderClick(View view) {
        String total = priceFormat(calculateOrderTotal());
        new AlertDialog.Builder(this)
                .setTitle("Make order")
                .setMessage("Are you sure you want to make this order for a total of " + total + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(QRMenuMainActivity.this, "Please wait. You order will soon be ready.", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private double calculateOrderTotal() {
        double result = 0;
        for (RestaurantMenuItem order : orders) {
            result += order.getCost();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_QR_CODE_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK: {
                    String scanResult = data.getStringExtra("SCAN_RESULT");
                    RestaurantMenuItem menuItem = EntitiesSerializationHelper.parseMenuItem(scanResult);
                    if (menuItem == null) {
                        Toast.makeText(this, "Sorry. That is not a valid QR Code.", Toast.LENGTH_LONG).show();
                    } else {
                        orders.add(menuItem);
                    }
                    break;
                }
                default: {
                    //TODO: do nothing
                    RestaurantMenuItem newItem = new RestaurantMenuItem("Milk shake", 20.12);
                    orders.add(newItem);
                    break;
                }
            }
        }
        refreshListView();
    }

    private void refreshListView() {
        listAdapter.notifyDataSetChanged();
    }
}
