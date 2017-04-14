package su.bnet.aton;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import su.bnet.aton.fragments.PlusOneFragment;
import su.bnet.aton.view.Toolbar;
import su.bnet.flowcontrol.BundleCommand;
import su.bnet.flowcontrol.FragmentNavigator;
import su.bnet.flowcontrol.Router;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private NavigationView navigationView;

    private Router<State, BundleCommand> router;
    private FragmentNavigator<State, BundleCommand> navigator;

    private void initRoute(){
        router = new Router<>();
        navigator = new FragmentNavigator<State, BundleCommand>(router, getFragmentManager(), R.id.frame) {
            @Override
            protected Fragment createFragment(State screenKey, Bundle data) {
                return PlusOneFragment.newInstance(screenKey.toString(), "other string");
            }
        };

        for(State s : State.values()){
            router.add(s, new BundleCommand());
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRoute();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.buildUI(this);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                overrideMenuFontsFonts(navigationView);
            }
        });
//        drawer.openDrawer(navigationView);

        navigator.forwardTo(State.MAIN_SCREEN);

//        navigator.forwardTo(State.MAIN_SCREEN);
//        navigator.forwardTo(State.FRAGMENT_7);
//        navigator.forwardTo(State.FRAGMENT_8);
//        navigator.forwardTo(State.CALL_SCREEN);
//        navigator.forwardTo(State.FEEDBACK_SCREEN);
//        navigator.forwardTo(State.KOTIROVKI_SCREEN);
//        navigator.forwardTo(State.PORTFEL_SCREEN);
//        navigator.forwardTo(State.OPERACII_SCREEN);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        navigator.replaceTo(State.MAIN_SCREEN);
                        break;
                    case 1:
                        navigator.replaceTo(State.PORTFEL_SCREEN);
                        break;
                    case 2:
                        navigator.replaceTo(State.KOTIROVKI_SCREEN);
                        break;
                    case 3:
                        navigator.replaceTo(State.OPERACII_SCREEN);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        toolbar.setTitle("Акции");
    }

    public void overrideMenuFontsFonts(View v)
    {
        try
        {
            if (v instanceof ViewGroup)
            {
                ViewGroup vg = (ViewGroup)v;
                for (int i = 0; i < vg.getChildCount(); i++)
                {
                    if(vg.getChildAt(i).getId() == R.id.textViewName) continue;
                    if(vg.getChildAt(i).getId() == R.id.content) continue;
                    overrideMenuFontsFonts(vg.getChildAt(i));
                }
            }
            else if (v instanceof TextView)
            {
                CalligraphyUtils.applyFontToTextView(this, (TextView) v, "fonts/GothamPro-Reg.ttf");
                ((TextView) v).setCompoundDrawablePadding(Math.round(this.getResources().getDimension(R.dimen.nav_item_drawable_padding)));
                ((TextView) v).setPadding(Math.round(this.getResources().getDimension(R.dimen.nav_item_padding_left)), 0, 0, 0);
            }
        }
        catch (Exception e)
        {
            //Log it, but ins't supposed to be here.
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!navigator.back()){
                //super.onBackPressed();
                finish();
            }
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_main:
                navigator.replaceTo(State.MAIN_SCREEN);
                break;
            case R.id.nav_call:
                navigator.replaceTo(State.CALL_SCREEN);
                break;
            case R.id.nav_feedback:
                navigator.replaceTo(State.FEEDBACK_SCREEN);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private enum State{
        MAIN_SCREEN,
        CALL_SCREEN,
        FEEDBACK_SCREEN,
        PORTFEL_SCREEN,
        KOTIROVKI_SCREEN,
        OPERACII_SCREEN,
        FRAGMENT_7,
        FRAGMENT_8
    }
}
