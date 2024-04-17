package fr.alexpado.go4lunch;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import fr.alexpado.go4lunch.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static void start(Activity from) {
        Intent intent = new Intent(from.getApplicationContext(), MainActivity.class);
        from.startActivity(intent);
    }

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (!NomNomUtils.isLoggedIn()) {
            // Not logged in
            this.finish();
            return;
        }

        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        this.setSupportActionBar(this.binding.appBarMain.toolbar);

        this.binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab)
                        .show();
            }
        });

        DrawerLayout   drawer         = this.binding.drawerLayout;
        NavigationView navigationView = this.binding.navView;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.main, menu);
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

        if (item.getItemId() == R.id.logout_menu) {
            AuthUI.getInstance().signOut(this.getApplicationContext());
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}