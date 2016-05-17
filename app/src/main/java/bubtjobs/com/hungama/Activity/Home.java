package bubtjobs.com.hungama.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bubtjobs.com.hungama.Fragment.DiscoverFragment;
import bubtjobs.com.hungama.Fragment.NewMusicFragment;
import bubtjobs.com.hungama.Fragment.PopularMusicFragment;
import bubtjobs.com.hungama.Fragment.RadioFragment;
import bubtjobs.com.hungama.Fragment.VideosFragment;
import bubtjobs.com.hungama.Others.SessionManager;
import bubtjobs.com.hungama.R;
import bubtjobs.com.hungama.Service.MusicService;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Spinner country_music;
    boolean check=false;

    SessionManager sessionManager;

    TextView nav_name_tv,nav_reward_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        country_music.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String music_type = (String) parent.getItemAtPosition(position);
                if (check) {

                    // Toast.makeText(Home.this, "" + music_type, Toast.LENGTH_SHORT).show();
                    sessionManager.setMusicType(music_type);
                    startActivity(new Intent(Home.this, Home.class));
                    finish();
                } else {
                    check = true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void init(){
        check=false;
        sessionManager=new SessionManager(Home.this);
        // toolbar
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        country_music = (Spinner)toolbar.findViewById(R.id.country_music);
//       ImageView ser_bt = (ImageView)toolbar.findViewById(R.id.ser_bt);
//        ser_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Home.this,Test.class));
//            }
//        });

        if(sessionManager.getMusicType().equals("Bengali"))
        {
           country_music.setSelection(0);
        }
        else{
            country_music.setSelection(1);
        }

        // viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView = navigationView.inflateHeaderView(R.layout.nav_header_home);

        nav_name_tv = (TextView) hView.findViewById(R.id.name_tv);
        nav_reward_tv = (TextView) hView.findViewById(R.id.reward_tv);
        navigationView.setNavigationItemSelectedListener(this);

        String name="Welcome " + sessionManager.getUserName();
        nav_name_tv.setText(name);

        if(!name.equals("Welcome Guest(Sign In)"))
        {
            nav_reward_tv.setText("");
        }


        nav_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new VideosFragment(), "Videos");
        adapter.addFrag(new NewMusicFragment(), "New Music");
        //adapter.addFrag(new RadioFragment(), "Radio");
        adapter.addFrag(new PopularMusicFragment(), "Popular Music");
        //adapter.addFrag(new DiscoverFragment(), "Discover");
        //adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            moveTaskToBack(true);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void checkLogin(){
        if(nav_name_tv.getText().toString().equals("Welcome Guest(Sign In)"))
        {
            Intent intent=new Intent(Home.this,Login.class);
            startActivity(intent);
        }
        else{

        }
    }
}
