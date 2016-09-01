package nifty.intern.teamc.beaconreminder;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import nifty.intern.teamc.IbeaconReceiver.IbeaconReceiver;
import nifty.intern.teamc.database.DatabaseManager;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       // タスク名
                                       EditText taskNameText = (EditText) findViewById(R.id.taskEditText);
                                       String taskName = taskNameText.getText().toString();
                                       // 対象人物
                                       Spinner spinner = (Spinner) findViewById(R.id.spinner);
                                       String targetName = (String) spinner.getSelectedItem();
                                       // タスク詳細文
                                       EditText taskDescText = (EditText) findViewById(R.id.discEditText);
                                       String taskDesc = taskDescText.getText().toString();

                                       DatabaseManager.storeTask(taskName, IbeaconReceiver.MemberID, targetName, taskDesc);

                                       Intent intent = new Intent();
                                       intent.setClassName("nifty.intern.teamc.beaconreminder", "nifty.intern.teamc.beaconreminder.TaskListActivity");
                                       startActivity(intent);
                                   }
                               }
        );
    }

    @Override
    public void onStart(){
        super.onStart();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        DatabaseManager databaseManager = new DatabaseManager(this, spinner);
        //NCMB initialize
        databaseManager.initialize(this.getApplicationContext());
        databaseManager.execute(DatabaseManager.MEMBERCLASS);
    }

}
