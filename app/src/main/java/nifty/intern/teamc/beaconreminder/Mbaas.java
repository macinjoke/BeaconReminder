package nifty.intern.teamc.beaconreminder;

/**
 * Created by USER on 2016/08/30.
 */
import android.content.Context;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;

public class Mbaas {
    private static final String APP_KEY="8b780f8a55a3218250526d42cb76e5dcaa71577222951ae021cd144c03c03ae9";
    private static final String CLIENT_KEY="0f0bfb2c7c7e702fe05aba70f3e274c6571ec3f2e9fc90df1e6ab238745ddaf2";
    public Mbaas(Context context){
        NCMB.initialize(context,APP_KEY,CLIENT_KEY);
    }

    public void store(){
        // クラスのNCMBObjectを作成
        NCMBObject obj = new NCMBObject("TestClass");

        // オブジェクトの値を設定
        obj.put("message", "Hello, Mbaas!!");

        // データストアへの登録
        obj.saveInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if(e != null){
                    //保存に失敗した場合の処理

                }else {
                    //保存に成功した場合の処理

                }
            }
        });
    }
}
