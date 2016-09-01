package nifty.intern.teamc.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.health.SystemHealthManager;
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

    private static final String APP_KEY="8b780f8a55a3218250526d42cb76e5dcaa71577222951ae021cd144c03c03ae9";
    private static final String CLIENT_KEY="0f0bfb2c7c7e702fe05aba70f3e274c6571ec3f2e9fc90df1e6ab238745ddaf2";
    private static final String MEMBERCLASS="Member";
    private static final String NAME="name";
    private static final String MEMBERID="memberid";
    private static final String BEACONID="beaconid";
    private static final String RSSI="rssi";
    private static final String OBJECTID ="objectId";


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


    public static void updateMember(String memberid, String beaconid, Integer rssi){
        NCMBQuery<NCMBObject> query = new NCMBQuery<>(MEMBERCLASS);
        query.whereEqualTo(MEMBERID, memberid);
        try {
            List<NCMBObject> list = query.find();
            for(NCMBObject obj: list){
                obj.put(BEACONID, beaconid);
                obj.put(RSSI, rssi);
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
        }catch(NCMBException e){
            System.out.println("updateMember failed");
        }
    }

    public static List<String> getAllMember(){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBQuery<NCMBObject> query = new NCMBQuery<>(MEMBERCLASS);
        //名前が設定してあるすべてのレコードを取得
        query.whereExists(NAME);
        List<String> memberlist = new ArrayList<String>();
        try {
            List<NCMBObject> results = query.find();
            for(NCMBObject obj: results){
                memberlist.add(obj.getString(NAME));
            }
        }catch(NCMBException e){
        }
        return memberlist;
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