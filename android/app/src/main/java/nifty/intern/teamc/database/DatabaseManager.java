package nifty.intern.teamc.database;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.ArrayList;
import java.util.List;

import nifty.intern.teamc.beaconreminder.TaskListActivity;

/**
 * Created by USER on 2016/08/31.
 */
public class DatabaseManager extends AsyncTask<Integer, Integer, List<String>>{

    private static final String APP_KEY="APP_KEY";
    private static final String CLIENT_KEY="CLIENT_KEY";

    private Context context;
    private ListView listView;

    public DatabaseManager(Context context, ListView listView){
        super();
        this.context = context;
        this.listView = listView;
    }

    public void initialize(Context context) {
        NCMB.initialize(context, APP_KEY, CLIENT_KEY);
    }


    public List<String> getAllTask(){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBQuery<NCMBObject> query = new NCMBQuery<>("TestClass");
        //keyというフィールドがvalueとなっているデータを検索する条件を設定
        query.whereExists("task");
        try {
            List<NCMBObject> results = query.find();
            List<String> tasklist = new ArrayList<String>();
            for(NCMBObject obj: results){
                tasklist.add(obj.getString("task"));
            }
            return tasklist;
        }catch(NCMBException e){
            return new ArrayList<String>();
        }
    }

    @Override
    protected List<String> doInBackground(Integer... value) {
        return  getAllTask();
    }

    @Override
    protected void onPostExecute(List<String> result) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_expandable_list_item_1, result);
        listView.setAdapter(adapter);
    }

    public static void storeTask(String name){
        // クラスのNCMBObjectを作成
        NCMBObject obj = new NCMBObject("TestClass");

        // オブジェクトの値を設定
        obj.put("task", name);

        // データストアへの登録
        obj.saveInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if(e != null){
                    //保存に失敗した場合の処理
                    System.out.println("storeTask failed");
                }else {
                    //保存に成功した場合の処理
                    System.out.println("storeTask success");
                }
            }
        });
    }
}