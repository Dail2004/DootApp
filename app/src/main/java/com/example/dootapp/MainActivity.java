package com.example.dootapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.dootapp.databinding.ActivityMainBinding;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        navigatorChangerListener(navController);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .build();
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.appBarMain.fab.setOnClickListener(v -> {
            navController.navigate(R.id.create_task_fragment);
        });
    }

    private void navigatorChangerListener(NavController navController) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (controller.getGraph().getStartDestination() == destination.getId()) {
                binding.appBarMain.fab.show();
            } else {
                binding.appBarMain.fab.hide();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}