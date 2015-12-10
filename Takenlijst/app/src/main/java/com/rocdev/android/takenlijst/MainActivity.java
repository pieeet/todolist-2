package com.rocdev.android.takenlijst;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    TakenlijstDB db;
    PagerAdapter adapter;
    ViewPager pager;
    int tabPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new TakenlijstDB(getApplicationContext());
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (Lijst lijst: db.getLijsten()) {
            TabLayout.Tab tab = tabLayout.newTab();
            String lijstnaam = lijst.getNaam();
            tab.setText(lijstnaam);
            tab.setTag(lijstnaam);
            tabLayout.addTab(tab);
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPos = tab.getPosition();
                pager.setCurrentItem(tabPos);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if (savedInstanceState != null) {
            int tabPos = savedInstanceState.getInt("tabPos");
            pager.setCurrentItem(tabPos);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        tabPos = pager.getCurrentItem();
        outState.putInt("tabPos", tabPos);
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

        else if (id == R.id.menuAddTaak) {
            Intent intent = new Intent(this, AddEditActivity.class);
            Lijst lijst = db.getLijst(tabLayout.getSelectedTabPosition() + 1);
            intent.putExtra("tab", lijst.getNaam());
            startActivity(intent);
        }

        else if (id == R.id.menuDelete) {
            //verberg taken die afgerond zijn
            TabLayout.Tab tab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
            String tabTag = tab.getTag().toString();
            ArrayList<Taak> taken = db.getTaken(tabTag);
            for (Taak t: taken) {
                if (t.getDatumMillisVoltooid() > 0) {
                    t.setVerborgen(Taak.TRUE);
                    db.updateTaak(t);
                }
            }
            int currentIndex = pager.getCurrentItem();
            LijstFragment huidigFragment = adapter.getFragment(currentIndex);
            huidigFragment.refreshTaskList();
        }

        return super.onOptionsItemSelected(item);
    }


}
