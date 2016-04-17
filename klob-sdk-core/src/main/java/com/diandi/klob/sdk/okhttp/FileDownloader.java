package com.diandi.klob.sdk.okhttp;


import com.diandi.klob.sdk.common.Global;
import com.diandi.klob.sdk.okhttp.callback.ResultCallback;
import com.diandi.klob.sdk.util.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;


import java.io.File;
import java.util.List;

import okhttp3.Call;


public class FileDownloader {

    public static void download(String url) {
        download(url, null);
    }

    public static void download(String url, final ResultCallback<String> callback) {
        File file = FileUtils.getDestinationInExternalPublicDir(FileUtils.getFileDownloadPath(), url.replaceAll(".*/(.*?)", "$1"));
        download(file.getParent(), url, callback);
    }

    public static void download(final String dir, String url, final ResultCallback<String> callback) {
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(dir, url.replaceAll(".*/(.*?)", "$1")) {
            @Override
            public void inProgress(float v, long l) {

            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(File file) {
                if (callback != null) {
                    callback.onResponse(file.getPath());
                }
                Global.showMsg(file.getPath() + " 已经保存到" + dir);
            }
        });
    }

    public static void saveImgs(String dirName, final List<String> uris) {
        for (final String url : uris) {
            OkHttpUtils.get().url(url).build().execute(new FileCallBack(FileUtils.getFileDownloadPath() + File.separator + dirName, url.replaceAll(".*/(.*?)", "$1")) {
                @Override
                public void inProgress(float v, long l) {

                }

                @Override
                public void onError(Call call, Exception e) {
                    Global.showMsg("保存失败");
                }

                @Override
                public void onResponse(File file) {
                    Global.showMsg(uris.indexOf(url) + "/" + uris.size() + "图片已保存到:" + file.getPath());
                }
            });
        }

    }

   /* public static void download(String uri, ResultCallback<String> callback) {
        File file = FileUtils.getDestinationInExternalPublicDir(FileUtils.getFileDownloadPath(), uri.replaceAll(".*//*(.*?)", "$1"));
        new OkHttpDownloadRequest.Builder().url(uri).destFileDir(file.getParent()).destFileName(uri.replaceAll(".*//*(.*?)", "$1")).download(callback);
    }

    public static void download(String dir,String uri, ResultCallback<String> callback) {
        new OkHttpDownloadRequest.Builder().url(uri).destFileDir(dir).destFileName(uri.replaceAll(".*//*(.*?)", "$1")).download(callback);
    }

    public static void saveImgs(String dirName, final List<String> uris) {
        for (final String uri : uris) {
            File mFile = FileUtils.getDestinationInExternalPublicDir(FileUtils.getFileDownloadPath() + File.separator + dirName, uri.replaceAll(".*//*(.*?)", "$1"));
            new OkHttpDownloadRequest.Builder().url(uri).destFileDir(mFile.getParent()).destFileName(uri.replaceAll(".*//*(.*?)", "$1")).download(new ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {
                    Global.showMsg("保存失败");
                }


                @Override
                public void onResponse(String response) {
                    Global.showMsg(uris.indexOf(uri) + "/" + uris.size() + "图片已保存到:" + response);
                }
            });

        }

    }*/

}
