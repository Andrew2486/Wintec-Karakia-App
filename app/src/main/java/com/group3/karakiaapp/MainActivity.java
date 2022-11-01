package com.group3.karakiaapp;

import android.os.*;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.navigation.*;
import androidx.navigation.fragment.*;
import androidx.navigation.ui.*;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    NavController navController;
    AppBarConfiguration appBarConfig;
    View infocardContainer;
    TextView infocard;
    @Override
    // TODO: Setup fragments to keep memory during rotation
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navhome);
        instance = this;

        infocardContainer = findViewById(R.id.infocard_container);
        infocard = findViewById(R.id.infocard_text);
        ((Button)findViewById(R.id.infocard_close)).setOnClickListener((x) -> CloseInfoCard());

        NavHostFragment navHost = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host);
        navController = navHost.getNavController();

        AppBarConfiguration.Builder config = new AppBarConfiguration.Builder(R.id.HomeFragment,R.id.TOSFragment);
        //config.setOpenableLayout((DrawerLayout)findViewById(R.id.drawer));
        appBarConfig = config.build();

        setSupportActionBar(findViewById(R.id.toolbar));
        NavigationUI.setupActionBarWithNavController(this,navController, appBarConfig);
        //NavigationUI.setupWithNavController((BottomNavigationView)findViewById(R.id.quicknav_bar),navController);
        //NavigationUI.setupWithNavController((NavigationView)findViewById(R.id.navmenu),navController);
    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,appBarConfig) || super.onSupportNavigateUp();
    }

    boolean infocardOpen = false;
    public void OpenInfoCard(String message) { OpenInfoCard(message, false);}
    public void OpenInfoCard(String message, boolean instant) { // TODO: Set info card opening animation
        infocard.setText(message);
        if (!infocardOpen) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)infocardContainer.getLayoutParams();
            params.leftMargin = -params.width;
            infocardContainer.setLayoutParams(params);
            infocardOpen = true;
        }
    }
    public void CloseInfoCard() { // TODO: Set info card closing animation
        if (infocardOpen) {
            infocard.setText("");
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)infocardContainer.getLayoutParams();
            params.leftMargin = 0;
            infocardContainer.setLayoutParams(params);
            infocardOpen = false;
        }
    }

    public static class Settings {
        public static boolean agreedToToS = false;
    }
}
