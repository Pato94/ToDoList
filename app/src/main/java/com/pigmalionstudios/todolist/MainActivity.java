package com.pigmalionstudios.todolist;


import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.setDisplayShowTitleEnabled(false);

        /**CREAR TABS**/

        ActionBar.Tab tabPendientes = actionBar.newTab()
                 .setText(R.string.tag_pendientes)
                 .setTabListener(new TabsListener<ListaFragment>(
                        this, this.getString(R.string.tag_pendientes), ListaFragment.class));

        actionBar.addTab(tabPendientes);

        ActionBar.Tab tabHechas = actionBar.newTab()
                 .setText(R.string.tag_hechas)
                 .setTabListener(new TabsListener<ListaFragment>(
                this, this.getString(R.string.tag_hechas), ListaFragment.class));
        actionBar.addTab(tabHechas);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
