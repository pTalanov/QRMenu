package ru.spbau.qrmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QRMenuMainActivity extends Activity {

    private static final int SCAN_QR_CODE_REQUEST_CODE = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, SCAN_QR_CODE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (SCAN_QR_CODE_REQUEST_CODE == requestCode) {
            TextView tv = (TextView) findViewById(R.id.textView);
            switch (resultCode) {
                case RESULT_OK : {
                    tv.setText(data.getStringExtra("SCAN_RESULT"));
                    break;
                }
                default : {
                    tv.setText("Canceled");
                    break;
                }
            }
        }
    }
}
