package valentinbreiz.ps4payloadsender;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.TextView;

public class Connection extends AppCompatActivity {

    private  Button ConnectBtn;
    private  Button SendBtn;
    private  Button BrowseBtn;
    private  TextView Injecting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        ConnectBtn = (Button) findViewById(R.id.ConnectBtn);
        SendBtn = (Button) findViewById(R.id.SendPayloadBtn);
        BrowseBtn = (Button) findViewById(R.id.BrowsePayloadBtn);
        Injecting = (TextView) findViewById(R.id.InjectingLbl);

        BrowseBtn.setEnabled(false);
        SendBtn.setEnabled(false);

        Injecting.setVisibility(TextView.INVISIBLE);

    }

    protected void Connect(View view)
    {
        final EditText ip = (EditText) findViewById(R.id.IPAddressTextBox);
        final EditText port = (EditText) findViewById(R.id.PortTextBox);

        if (ip.getText().toString().equals("") || port.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage("You need to type an IP Address or a Port.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
        else
        {
            ip.setEnabled(false);
            port.setEnabled(false);
            ConnectBtn.setEnabled(false);
            BrowseBtn.setEnabled(true);
        }
    }

    protected void Browse(View view)
    {
        BrowseBtn.setText("file.bin");

        BrowseBtn.setEnabled(false);
        SendBtn.setEnabled(true);
    }

    public void Send(View view)
    {
        Injecting.setVisibility(TextView.VISIBLE);
        Injecting.setText("Done!");
    }
}
