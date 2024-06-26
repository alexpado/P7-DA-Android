package fr.alexpado.go4lunch;

import static fr.alexpado.go4lunch.utils.LogUtils.debug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.alexpado.go4lunch.databinding.ActivityMainBinding;
import fr.alexpado.go4lunch.events.login.LogoutEvent;
import fr.alexpado.go4lunch.services.AuthenticationService;
import fr.alexpado.go4lunch.utils.NomNomUtils;

public class MainActivity extends AppCompatActivity {

    public static void start(Activity from) {

        Intent intent = new Intent(from.getApplicationContext(), MainActivity.class);
        from.startActivity(intent);
    }

    private AppBarConfiguration   appBarConfiguration;
    private ActivityMainBinding   binding;
    private AuthenticationService authenticationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.authenticationService = BeanFactory.getInstance(AuthenticationService.class);

        if (!NomNomUtils.isLoggedIn()) {
            // Not logged in
            this.finish();
            return;
        }

        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.setSupportActionBar(this.binding.activityMainContent.toolbar);

        DrawerLayout         drawer         = this.binding.drawerLayout;
        BottomNavigationView navigationView = this.binding.activityMainContent.bottomNavView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        this.appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow
        ).setOpenableLayout(drawer)
         .build();

        NavController navController = Navigation.findNavController(
                this,
                R.id.nav_host_fragment_content_main
        );
        NavigationUI.setupActionBarWithNavController(
                this,
                navController,
                this.appBarConfiguration
        );
        NavigationUI.setupWithNavController(navigationView, navController);
        debug(this, "Activity is now created.");
    }

    @Override
    protected void onStart() {

        super.onStart();
        LogoutEvent.HANDLERS.subscribe(this::onLogoutEvent);
        debug(this, "Activity is now ready.");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController nav = Navigation.findNavController(
                this,
                R.id.nav_host_fragment_content_main
        );

        return NavigationUI.navigateUp(
                nav,
                this.appBarConfiguration
        ) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        debug(this, "onOptionsItemSelected(): Menu Item '%s' has been clicked.", item.getItemId());
        if (item.getItemId() == R.id.logout_menu) {
            this.authenticationService.logout(this.getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onLogoutEvent(LogoutEvent event) {

        debug(this, "Received logout event.");
        LogoutEvent.HANDLERS.unsubscribe(this::onLogoutEvent);
        this.finish();
    }

}