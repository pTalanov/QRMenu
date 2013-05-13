package ru.spbau.qrmenu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ru.spbau.qrmenu.entities.RestaurantMenuItem;

import java.util.ArrayList;
import java.util.List;

public class QRMenuMainActivity extends ListActivity {

    private static final int SCAN_QR_CODE_REQUEST_CODE = 0;

    private List<RestaurantMenuItem> orders;

    private ArrayAdapter<RestaurantMenuItem> listAdapter;

    public QRMenuMainActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        orders = new ArrayList<RestaurantMenuItem>();
        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem("Burger", 3.2);
        orders.add(restaurantMenuItem);
        orders.add(restaurantMenuItem);
        listAdapter = new ArrayAdapter<RestaurantMenuItem>(this, R.layout.main_menu_list_item, orders) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.main_menu_list_item, parent, false);
                TextView itemText = (TextView) view.findViewById(R.id.itemText);
                itemText.setText(orders.get(position).getName());
                TextView priceText = (TextView) view.findViewById(R.id.priceText);
                priceText.setText(String.format("%1$,.2f", orders.get(position).getCost()));
                Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
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

    @SuppressWarnings("UnusedParameters")
    public void onScanCodeClick(View view) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, SCAN_QR_CODE_REQUEST_CODE);
        refreshListView();
    }

    @SuppressWarnings("UnusedParameters")
    public void onMakeOrderClick(View view) {
        Toast.makeText(this, "Please wait. You order will soon be ready.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (SCAN_QR_CODE_REQUEST_CODE == requestCode) {
            switch (resultCode) {
                case RESULT_OK: {
                    String scanResult = data.getStringExtra("SCAN_RESULT");
                    orders.add(EntitiesSerializationHelper.parseMenuItem(scanResult));
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
