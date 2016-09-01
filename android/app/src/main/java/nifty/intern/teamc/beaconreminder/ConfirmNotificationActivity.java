package nifty.intern.teamc.beaconreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ConfirmNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_notification);

        Intent intent = getIntent();
        TextView taskName = (TextView) findViewById(R.id.taskName);
        taskName.setText("タスク名 : " + intent.getStringExtra("taskName"));
        TextView targetName = (TextView) findViewById(R.id.targetName);
        targetName.setText(intent.getStringExtra("targetName") + " さんが近くにいます");
        TextView taskDesc = (TextView) findViewById(R.id.taskDesc);
        taskDesc.setText(intent.getStringExtra("taskDesc"));
    }
}
