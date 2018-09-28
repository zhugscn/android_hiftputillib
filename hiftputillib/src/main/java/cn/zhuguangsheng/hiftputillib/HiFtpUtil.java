package cn.zhuguangsheng.hiftputillib;

import android.util.Log;

/**
 * Created by zhuguangsheng on 2018/9/28.
 */

public class HiFtpUtil {
    private final static String TAG = HiFtpUtil.class.getSimpleName();

    //是否启用日志
    public static boolean bIsLogOn = true;

    public static String FTP_SERVER_IP = "192.168.1.88";
    public static String FTP_USERNAME = "tom";//"anonymous";
    public static String FTP_PASSWORD = "123";
    public static int FTP_PORT = 21;

    //仅此单例即可
    static FTPClientFunctions mFtpClient = new FTPClientFunctions();

    public static void setbIsLogOn(boolean bIsLogOn) {
        HiFtpUtil.bIsLogOn = bIsLogOn;
    }

    public static String getFtpServerIp() {
        return FTP_SERVER_IP;
    }

    public static void setFtpServerIp(String ftpServerIp) {
        FTP_SERVER_IP = ftpServerIp;
    }

    public static String getFtpUsername() {
        return FTP_USERNAME;
    }

    public static void setFtpUsername(String ftpUsername) {
        FTP_USERNAME = ftpUsername;
    }

    public static String getFtpPassword() {
        return FTP_PASSWORD;
    }

    public static void setFtpPassword(String ftpPassword) {
        FTP_PASSWORD = ftpPassword;
    }

    public static int getFtpPort() {
        return FTP_PORT;
    }

    public static void setFtpPort(int ftpPort) {
        FTP_PORT = ftpPort;
    }

    public interface FtpCallback {
        //上传
        void uploadResult(boolean result, String msg);
    }

    /**
     * 上传文件
     * 同步方式调用
     * @param sourceFilePath
     * @param destFtpServerFileName
     * @param ftpSubDir 子目录名，暂时只支持1级子目录
     * @param callback 回调定义
     */
    public synchronized static void uploadFileSync(String sourceFilePath,
                                                   String destFtpServerFileName,
                                                   String ftpSubDir,
                                                   FtpCallback callback) {
        // TODO 可以首先去判断一下网络
        boolean connectResult = mFtpClient.ftpConnect(FTP_SERVER_IP, FTP_USERNAME, FTP_PASSWORD, FTP_PORT);
        if (connectResult) {
            boolean changeDirResult = true;
            //changeDirResult = ftpClient.ftpChangeDir("/**");
            if (changeDirResult) {
                boolean uploadResult = mFtpClient.ftpUpload(sourceFilePath, destFtpServerFileName, ftpSubDir);
                if (uploadResult) {
                    logi("上传成功");
                    callback.uploadResult(false, "上传成功");
                    boolean disConnectResult = mFtpClient.ftpDisconnect();
                    if (disConnectResult) {
                        logi("关闭ftp连接成功");
                    } else {
                        logw("关闭ftp连接失败");
                    }
                } else {
                    logw("上传失败");
                    callback.uploadResult(false, "上传失败");
                }
            } else {
                logw("切换ftp目录失败");
                callback.uploadResult(false, "切换ftp目录失败");
            }

        } else {
            logw("连接ftp服务器失败");
            callback.uploadResult(false, "连接ftp服务器失败");
        }
    }

    private static void logi(String msg) {
        if (!bIsLogOn) {
            return;
        }
        Log.w(TAG, msg);
    }

    private static void logw(String msg) {
        if (!bIsLogOn) {
            return;
        }
        Log.w(TAG, msg);
    }

}
