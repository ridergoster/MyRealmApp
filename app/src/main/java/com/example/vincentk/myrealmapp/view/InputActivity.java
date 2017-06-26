package com.example.vincentk.myrealmapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vincentk.myrealmapp.R;
import com.example.vincentk.myrealmapp.model.MyURL;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by vincentk on 26/06/2017.
 */

public class InputActivity extends AppCompatActivity {

    Realm realm;
    EditText nameInput;
    EditText urlInput;
    EditText descriptionInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_input);
        nameInput = (EditText)findViewById(R.id.nameInput);
        urlInput = (EditText)findViewById(R.id.urlInput);
        descriptionInput = (EditText)findViewById(R.id.descriptionInput);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);

                if(URLUtil.isValidUrl(sharedText)) {
                    try {
                        URI domain = new URI(sharedText);
                        nameInput.setText(domain.getAuthority());
                        urlInput.setText(domain.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "NOT A VALID URL", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void saveURL(View view) {
        realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Log.d("exec MYURL: ", "BEFORE");
                    MyURL myURL = createMyURl(realm);
                    Log.d("exec MYURL: ", "AFTER");
                    Log.d("exec MYURL: id", String.valueOf(myURL.getId()));
                    Log.d("exec MYURL: name", myURL.getName());
                    Log.d("exec MYURL: url", myURL.getUrl());
                    Log.d("exec MYURL: desc", myURL.getDescription());
                    Log.d("exec MYURL: date", myURL.getUpdateDate().toString());

                    realm.copyToRealmOrUpdate(myURL);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    onBackPressed();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    OnError(error);
                }
            });
    }

    private MyURL createMyURl(Realm realm) {
        Log.d("CREATE MYURL: ", "START");
        Log.d("CREATE MYURL - realm:", realm.toString());
        long nextId = 1;
        if (realm.where(MyURL.class).findAll().size() > 0) {
            nextId  = realm.where(MyURL.class).max("id").longValue() + 1;
        }
        Log.d("CREATE MYURL - id: ", String.valueOf(nextId));

        String name = nameInput.getText().toString();
        String url = urlInput.getText().toString();
        MyURL myURL = new MyURL(nextId, name, url);
        myURL.setDescription(descriptionInput.getText().toString());
        myURL.setUpdateDate(new Date());

        return myURL;
    }

    private void OnError(Throwable error) {
        Log.e("ERROR", error.toString());
        Toast.makeText(this, "ERROR ON UPDATE", Toast.LENGTH_SHORT).show();
    }


    public void onBackPressed() {
        Intent myIntent = new Intent(InputActivity.this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();
        return;
    }
}
