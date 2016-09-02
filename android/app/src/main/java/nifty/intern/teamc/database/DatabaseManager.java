package nifty.intern.teamc.database;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.AdapterView;
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

import nifty.intern.teamc.IbeaconReceiver.IbeaconReceiver;
import nifty.intern.teamc.beaconreminder.TaskListActivity;

/**
 * Created by USER on 2016/08/31.
 */
public class DatabaseManager extends AsyncTask<String, Integer, List<String>>{

    private static final String APP_KEY="";
    private static final String CLIENT_KEY="";

    public static final String MEMBERCLASS="Member";
    public static final String MEMBERNAME="name";
    public static final String MEMBERID="memberid";

    private static final String BEACONID="beaconid";
    private static final String RSSI="rssi";

    private static final String OBJECTID ="objectId";

    public static final String TASKCLASS ="Task";
    public static final String TASKNAME ="name";
    public static final String TASKDETAIL ="detail";
    public static final String TASKLOCATION = "location";
    private static final String ORIGINID ="originid";
    public static final String TARGETNAME ="targetname";

    public static final String ROOMCLASS ="Room";
    public static final String ROOMNAME ="name";


    private Context context;
    private AdapterView view;

    public DatabaseManager(Context context, AdapterView view){
        super();
        this.context = context;
        this.view = view;
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
        query.whereExists(MEMBERNAME);
        List<String> memberlist = new ArrayList<String>();
        try {
            List<NCMBObject> results = query.find();
            for(NCMBObject obj: results){
                memberlist.add(obj.getString(MEMBERNAME));
            }
        }catch(NCMBException e){
        }
        return memberlist;
    }

    public static List<String> getAllLocation(){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBQuery<NCMBObject> query = new NCMBQuery<>(ROOMCLASS);
        //名前が設定してあるすべてのレコードを取得
        query.whereExists(ROOMNAME);
        List<String> memberlist = new ArrayList<String>();
        try {
            List<NCMBObject> results = query.find();
            for(NCMBObject obj: results){
                memberlist.add(obj.getString(ROOMNAME));
                System.out.println("getAllLocation" + obj.getString(ROOMNAME));
            }
        }catch(NCMBException e){
        }
        return memberlist;
    }

    public List<String> getAllTask(){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBQuery<NCMBObject> query = new NCMBQuery<>(TASKCLASS);
        //keyというフィールドがvalueとなっているデータを検索する条件を設定
        query.whereExists(TASKNAME);
        try {
            List<NCMBObject> results = query.find();
            List<String> tasklist = new ArrayList<String>();
            for(NCMBObject obj: results){
                String str = obj.getString(TASKNAME)+ "  " + obj.getString(TARGETNAME);
                tasklist.add(str);
            }
            return tasklist;
        }catch(NCMBException e){
            return new ArrayList<String>();
        }
    }




    public static List<NCMBObject> getMemberRecord(String beaconid){
        NCMBQuery<NCMBObject> query = new NCMBQuery<>(MEMBERCLASS);
        query.whereEqualTo(BEACONID, beaconid);
        query.whereNotEqualTo(MEMBERID, IbeaconReceiver.MemberID);
        try{
            List<NCMBObject> results = query.find();
            System.out.println("getMember" + results.size());
            return results;
        }catch(NCMBException e){
            System.out.println("getMember");
            return null;
        }
    }

    public static List<NCMBObject> getTaskRecord(){
        NCMBQuery<NCMBObject> query = new NCMBQuery<>(TASKCLASS);
        query.whereEqualTo(ORIGINID, IbeaconReceiver.MemberID);
        try{
            List<NCMBObject> results = query.find();
            System.out.println("getTask" + results.size());
            return results;
        }catch(NCMBException e){
            System.out.println("getTask");
            return null;
        }
    }

    @Override
    protected List<String> doInBackground(String... value) {
        if(value[0] == TASKCLASS){
            return  getAllTask();
        }else if(value[0] == MEMBERCLASS) {
            return getAllMember();
        }else{
            System.out.println("ERROR : doInBackground ");
            return new ArrayList<String>();
        }
    }

    @Override
    protected void onPostExecute(List<String> result) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_expandable_list_item_1, result);
        view.setAdapter(adapter);
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

    public static void storeTask(String taskname, String originid, String targetname, String taskdetail){
        // クラスのNCMBObjectを作成
        NCMBObject obj = new NCMBObject(TASKCLASS);

        // オブジェクトの値を設定
        obj.put(TASKNAME, taskname);
        obj.put(ORIGINID, originid);
        obj.put(TARGETNAME, targetname);
        obj.put(TASKDETAIL, taskdetail);

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

    public static void storeTask(String taskname, String originid, String targetname, String taskdetail, String location){
        // クラスのNCMBObjectを作成
        NCMBObject obj = new NCMBObject(TASKCLASS);

        // オブジェクトの値を設定
        obj.put(TASKNAME, taskname);
        obj.put(ORIGINID, originid);
        obj.put(TARGETNAME, targetname);
        obj.put(TASKDETAIL, taskdetail);
        obj.put(TASKLOCATION, location);

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