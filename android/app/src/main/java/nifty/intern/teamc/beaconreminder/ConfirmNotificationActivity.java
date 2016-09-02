package nifty.intern.teamc.beaconreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import nifty.intern.teamc.database.DatabaseManager;

public class ConfirmNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_notification);

        Intent intent = getIntent();

        TextView taskName = (TextView) findViewById(R.id.taskName);
        TextView targetName = (TextView) findViewById(R.id.targetName);
        TextView taskDesc = (TextView) findViewById(R.id.taskDesc);

        taskName.setText("タスク名 : " + intent.getStringExtra("taskName"));

        String roomName = intent.getStringExtra("roomName");
        if(roomName.equals(DatabaseManager.NOROOM)){
            targetName.setText(intent.getStringExtra("targetName") + " さんが近くにいます");
        }else{
            targetName.setText(intent.getStringExtra("targetName") + " さんが " + roomName + " にいます");
        }

        taskDesc.setText(intent.getStringExtra("taskDesc"));
    }
}
