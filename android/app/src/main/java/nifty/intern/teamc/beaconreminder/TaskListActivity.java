package nifty.intern.teamc.beaconreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import nifty.intern.teamc.database.DatabaseManager;

import nifty.intern.teamc.IbeaconReceiver.IbeaconReceiver;
import nifty.intern.teamc.database.DatabaseManager;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Start Bluetooth Service
        startService(new Intent(TaskListActivity.this, IbeaconReceiver.class));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("nifty.intern.teamc.beaconreminder", "nifty.intern.teamc.beaconreminder.CreateTaskActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        ListView lv = (ListView) findViewById(R.id.listView1);
        DatabaseManager databaseManager = new DatabaseManager(this, lv);
        //NCMB initialize
        databaseManager.initialize(this.getApplicationContext());
        databaseManager.execute(DatabaseManager.TASKCLASS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
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
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(TaskListActivity.this, IbeaconReceiver.class));
    }

}
