package com.group3.karakiaapp;

import android.os.Bundle;

import androidx.appcompat.app.*;
import androidx.navigation.*;
import androidx.navigation.fragment.*;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navhome);

        NavHostFragment navHost = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHost.getNavController();

        //AppBarConfiguration.Builder config = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.searchFragment);
        //config.setOpenableLayout((DrawerLayout)findViewById(R.id.drawer));
        //appBarConfig = config.build();

        //setSupportActionBar(findViewById(R.id.toolbar));
        //NavigationUI.setupActionBarWithNavController(this,navController, appBarConfig);
        //NavigationUI.setupWithNavController((BottomNavigationView)findViewById(R.id.quicknav_bar),navController);
        //NavigationUI.setupWithNavController((NavigationView)findViewById(R.id.navmenu),navController);
    }
}
