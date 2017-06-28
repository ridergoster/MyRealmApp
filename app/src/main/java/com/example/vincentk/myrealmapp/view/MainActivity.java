package com.example.vincentk.myrealmapp.view;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.vincentk.myrealmapp.R;
import com.example.vincentk.myrealmapp.adapter.MyURLAdapter;
import com.example.vincentk.myrealmapp.model.MyURL;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<MyURL> myURLRealmList;
    private Realm realm;
    private MyURLAdapter myURLAdapter;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        listView = (ListView)findViewById(R.id.listView);
        myURLRealmList = new ArrayList<>();
        myURLAdapter = new MyURLAdapter(MainActivity.this, myURLRealmList);
        listView.setAdapter(myURLAdapter);
        myURLRealmList.addAll(realm.where(MyURL.class).findAll());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuOpt) {
        getMenuInflater().inflate(R.menu.menu_list, menuOpt);
        menu = menuOpt;
        SearchManager manager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateList(newText);
                return true;
            }
        });
        return true;
    }

    private void updateList(String query) {
        myURLRealmList.clear();

        RealmQuery<MyURL> queryRealm = realm.where(MyURL.class);
        if (query.length() > 0) {
            queryRealm.contains("name",query, Case.INSENSITIVE);
        }
        myURLRealmList.addAll(queryRealm.findAllSorted("updateDate"));
        myURLAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                openAddActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAddActivity() {
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
