package ru.spbau.qrmenu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class QRMenuMainActivity extends ListActivity {

    private static final int SCAN_QR_CODE_REQUEST_CODE = 0;

    private List<String> orders;

    private ArrayAdapter<String> listAdapter;

    public QRMenuMainActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        orders = new ArrayList<String>();
        orders.add("Sample order1");
        orders.add("Sample order2");
        listAdapter = new ArrayAdapter<String>(this, R.layout.main_menu_list_item, orders) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                View view = inflater.inflate(R.layout.main_menu_list_item, parent, false);
                TextView itemText = (TextView) view.findViewById(R.id.itemText);
                itemText.setText(orders.get(position));
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
                    orders.add(scanResult);
                    break;
                }
                default: {
                    orders.add("Canceled");
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
