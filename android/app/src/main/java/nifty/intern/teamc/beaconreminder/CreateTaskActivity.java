package nifty.intern.teamc.beaconreminder;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        EditText editText = (EditText) findViewById(R.id.editText);
        // エディットテキストのテキストを設定します
//        editText.setText("テスト");
        // エディットテキストのテキストを全選択します
//        editText.selectAll();
        // エディットテキストのテキストを取得します
//        String text = editText.getText().toString();

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       EditText editText = (EditText) findViewById(R.id.editText);
                                       String taskName = editText.getText().toString();
                                       DatabaseManager.storeTask(taskName);
                                       Intent intent = new Intent();
                                       intent.setClassName("nifty.intern.teamc.beaconreminder", "nifty.intern.teamc.beaconreminder.TaskListActivity");
                                       startActivity(intent);
                                   }
                               }
        );
    }

}
