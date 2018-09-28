# android_hiftputillib
androidftp上传工具类,对apache的重新封装

调用方法示例：

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
