package valentinbreiz.ps4payloadsender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

    private Socket socket;
    private byte[] payload;
    DataOutputStream dataOutputStream = null;

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
            try
            {
                socket = new Socket();
                socket.setTcpNoDelay(true);
                socket.connect(new InetSocketAddress(ip.getText().toString(), Integer.parseInt(port.getText().toString())));

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
            }
            catch (UnknownHostException e1)
            {
                e1.printStackTrace();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }

            ip.setEnabled(false);
            port.setEnabled(false);
            ConnectBtn.setEnabled(false);
            BrowseBtn.setEnabled(true);
        }
    }

    private static final int PICKFILE_RESULT_CODE = 1;

    protected void Browse(View view)
    {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, PICKFILE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    String FilePath = data.getData().getPath();

                    BrowseBtn.setText(FilePath);
                    File file = new File(FilePath);
                    int size = (int) file.length();
                    byte[] bytes = new byte[size];
                    try {
                        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                        buf.read(bytes, 0, bytes.length);
                        buf.close();
                        payload = bytes;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    BrowseBtn.setEnabled(false);
                    SendBtn.setEnabled(true);
                }
                break;
        }
    }

    public void Send(View view)
    {
        Injecting.setVisibility(TextView.VISIBLE);

        try
        {
            if (payload == null)
            {
                Injecting.setText("Error, file is null!");
            }
            else
            {
                dataOutputStream.write(payload);
                Injecting.setText("Done!");
            }
            socket.close();
        }
        catch (IOException e)
        {
            Injecting.setText("Error!");
            e.printStackTrace();
        }
    }
}
