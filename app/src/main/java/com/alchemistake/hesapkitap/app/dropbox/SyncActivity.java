package com.alchemistake.hesapkitap.app.dropbox;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.alchemistake.hesapkitap.app.R;
import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.DbxFiles;
import com.dropbox.core.v2.DbxUsers;

public class SyncActivity extends DropboxActivity {

    boolean logged = false;
    Button loginButton, upload, download;

    TextView email, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        loginButton = (Button)findViewById(R.id.login);
        upload = (Button)findViewById(R.id.upload);
        download = (Button)findViewById(R.id.download);

        email = (TextView)findViewById(R.id.email_text);
        status = (TextView)findViewById(R.id.status);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.startOAuth2Authentication(SyncActivity.this, getString(R.string.app_key));
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        if(logged)
            loginButton.setVisibility(View.INVISIBLE);
        else
            loginButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void loadData() {
        new GetCurrentAccountTask(DropboxClient.DbxUsers(), new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(DbxUsers.FullAccount result) {
                email.setText(result.email);
                loginButton.setText("Değiştir");
                status.setText("Giriş Yaptı");
                logged = true;
            }

            @Override
            public void onError(Exception e) {
                email.setText("");
                loginButton.setText("Giriş Yap");
                status.setText("Giriş Yapılmadı");
                logged = false;
            }
        }).execute();
    }

    private void uploadFile() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Uploading");
        dialog.show();

        new UploadFileTask(this, DropboxClient.DbxFiles(), new UploadFileTask.Callback() {
            @Override
            public void onUploadComplete(DbxFiles.FileMetadata result) {
                dialog.dismiss();

                Toast.makeText(SyncActivity.this,
                        result.name + " size " + result.size + " modified " + result.clientModified.toGMTString(),
                        Toast.LENGTH_SHORT)
                        .show();

                // Reload the folder
                loadData();
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();
                Toast.makeText(SyncActivity.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute("");
    }
}
