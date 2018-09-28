package cn.zhuguangsheng.hiftputildemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.zhuguangsheng.hiftputillib.HiFtpUtil;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = MainActivity.class.getSimpleName();

    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findControls();
        addListeners();
    }

    void findControls() {
        btn1 = findViewById(R.id.btn1);
    }

    void addListeners() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadNew2();
            }
        });
    }

    public final static String sourceFilePath = "/mnt/sdcard/Download/1.txt";
    public final static String descFileName = "1.txt";

    void uploadNew2() {
        HiFtpUtil.setbIsLogOn(true);
        HiFtpUtil.setFtpServerIp("115.28.65.xxx");
        HiFtpUtil.setFtpUsername("tom");
        HiFtpUtil.setFtpPassword("xxx");
        HiFtpUtil.setFtpPort(21);

        new Thread(new Runnable() {
            @Override
            public void run() {
                HiFtpUtil.uploadFileSync(sourceFilePath, descFileName, "20180928", new HiFtpUtil.FtpCallback() {
                    @Override
                    public void uploadResult(boolean result, String msg) {
                        Log.i(TAG, "result=" + result + ", msg=" + msg);
                    }
                });
            }
        }).start();
    }

}
