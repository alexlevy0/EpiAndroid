package epiandroid.alexlevy0.epiandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import epiandroid.alexlevy0.epiandroid.fragments.LogFragment;
import epiandroid.alexlevy0.epiandroid.fragments.MessageFragment;
import epiandroid.alexlevy0.epiandroid.fragments.ModulesFragment;
import epiandroid.alexlevy0.epiandroid.fragments.PlanningFragment;
import epiandroid.alexlevy0.epiandroid.fragments.ProjetsFragment;
import epiandroid.alexlevy0.epiandroid.fragments.TokenFragment;
import epiandroid.alexlevy0.epiandroid.fragments.TrombiFragment;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NetworkImageView profilPicture;
    private String           login;
    private ImageLoader      mImageLoader;
    private TextView         profileName;
    private TextView         profileMail;
    private Fragment         logFragment;
    private Fragment         messageFragment;
    private Fragment         planningFragment;
    private Fragment         tokenFragment;
    private Fragment         trombiFragment;
    private Fragment         modulesFragment;
    private Fragment         projetsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!getIntent().hasExtra("login"))
            Toast.makeText(MainActivity.this, "Impossible de trouver le login", Toast.LENGTH_LONG).show();
        login = getIntent().getStringExtra("login");

        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        profileName = (TextView) header.findViewById(R.id.profileName);
        profileName.setText(login);
        profileMail = (TextView) header.findViewById(R.id.profileMail);
        profileMail.setText(login + getString(R.string.mailEpitech));

        profilPicture = (NetworkImageView) header.findViewById(R.id.profilPictureNav);
        getProfilPicture(login);

        messageFragment = new MessageFragment();
        logFragment = new LogFragment();
        modulesFragment = new ModulesFragment();
        planningFragment = new PlanningFragment();
        projetsFragment = new ProjetsFragment();
        tokenFragment = new TokenFragment();
        trombiFragment = new TrombiFragment();

//        Bundle bundle = new Bundle();
//        bundle.putString("token", "From Activity");
//// set Fragmentclass Arguments
//        Fragmentclass fragobj = new Fragmentclass();
//        fragobj.setArguments(bundle);


    }

    public boolean getProfilPicture(String login)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority(getString(R.string.url_profilPicture))
                .appendPath("userprofil").appendPath(login + ".bmp");
        Log.d("IMAGE PROFILE", builder.build().toString());
        mImageLoader = NetworkSingleton.getInstance(this).getImageLoader();
        profilPicture.setImageUrl(builder.build().toString(), mImageLoader);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.log:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, logFragment).commit();
                break;
            case R.id.messages:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, messageFragment).commit();
                break;
            case R.id.planning:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, planningFragment).commit();
                break;
            case R.id.token:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, tokenFragment).commit();
                break;
            case R.id.trombi:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, trombiFragment).commit();
                break;
            case R.id.modules:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, modulesFragment).commit();
                break;
            case R.id.projets:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame, projetsFragment).commit();
                break;
            case R.id.logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("login", "");
                intent.putExtra("token", "");
                startActivity(intent);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    public void onButtonClicked(View v) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        DialogFragment newFragment = new PlanningDatePicker();
        newFragment.show(fragmentManager, "timePicker");
    } */
}
