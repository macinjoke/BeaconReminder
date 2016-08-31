package nifty.intern.teamc.database;

import android.content.Context;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;

/**
 * Created by USER on 2016/08/31.
 */
public class DatabaseManager {
    private static final String APP_KEY="APP_KE";
    private static final String CLIENT_KEY="CLIENT_KEY";
    public static void initialize(Context context) {
        NCMB.initialize(context, APP_KEY, CLIENT_KEY);
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
