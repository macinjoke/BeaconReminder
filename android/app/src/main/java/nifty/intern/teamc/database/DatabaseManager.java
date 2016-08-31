package nifty.intern.teamc.database;

import android.content.Context;

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
public class DatabaseManager {
    private static final String APP_KEY="APP_KEY";
    private static final String CLIENT_KEY="CLIENT_KEY";
    public List<String> tasklist = new ArrayList<String>();

    public static void initialize(Context context) {
        NCMB.initialize(context, APP_KEY, CLIENT_KEY);
    }


    public static void getAllTask(){
        //TestClassを検索するためのNCMBQueryインスタンスを作成
        NCMBQuery<NCMBObject> query = new NCMBQuery<>("TestClass");
        //keyというフィールドがvalueとなっているデータを検索する条件を設定
        query.whereExists("task");



        //データストアからデータを検索
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> results, NCMBException e) {
                if (e != null) {
                    //検索失敗時の処理
                    System.out.println("getAllTask failed");
                } else {
                    //検索成功時の処理
                    List<String> tasklist = new ArrayList<String>();
                    System.out.println("getAllTask success");
                    for(NCMBObject obj: results){
                        tasklist.add(obj.getString("task"));
                        System.out.println(obj.getString("task"));
                    }
                    TaskListActivity.setList(tasklist);
                }
            }
        });
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
