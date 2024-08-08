package com.example.menus;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button buttonShowPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable EdgeToEdge display
        setContentView(R.layout.activity_main);
        // Set padding to the main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Register context menu to textview, 1 context menu per activity
        textView = findViewById(R.id.textview);
        registerForContextMenu(textView);

        // Register a popup menu to any view you have, here use this button
        buttonShowPopup = findViewById(R.id.buttonShowPopup);
        buttonShowPopup.setOnClickListener(v -> showPopupMenu(v));
    }


    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);

        // Add submenu to the menu, for example in the 2nd position.
        // If we want to add it in the first position, then we can use menu.addSubMenu(R.string.submenu) directly.
        SubMenu subMenu = menu.addSubMenu(Menu.NONE, R.id.submenu, 2, R.string.submenu);
        // Add items to the submenu
        subMenu.add(Menu.NONE, R.id.sub_option1, 1, R.string.sub_option_1);
        subMenu.add(Menu.NONE, R.id.sub_option2, 2, R.string.sub_option_2);
        subMenu.add(Menu.NONE, R.id.sub_option3, 3, R.string.sub_option_3);

        // on submenu click
        MenuItem menuItem = menu.findItem(R.id.submenu);
        menuItem.setOnMenuItemClickListener(item -> {
            Toast.makeText(this, item.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
            return true; // Return true to consume the click event
        });

        // on sub menu item click
        for (int i = 0; i < subMenu.size(); i++)
            subMenu.getItem(i).setOnMenuItemClickListener(item -> {
                Toast.makeText(this, item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                return true; // Return true to consume the click event
            });


        return true; // Return true to display the options menu
    }

    // Handle action bar item clicks here.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        Toast.makeText(this, item.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
        return true; // Return true to consume the click event
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
        return true; // Return true to consume the click event
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
            return true;
        });

        popupMenu.show();
    }
}