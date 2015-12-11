package com.diandi.klob.sdk.okhttp;


import com.diandi.klob.sdk.common.Global;
import com.diandi.klob.sdk.okhttp.callback.ResultCallback;
import com.diandi.klob.sdk.okhttp.request.OkHttpDownloadRequest;
import com.diandi.klob.sdk.util.FileUtils;
import com.squareup.okhttp.Request;


import java.io.File;
import java.util.List;


public class FileDownloader {

    public static void download(String uri) {
        File file = FileUtils.getDestinationInExternalPublicDir(FileUtils.getFileDownloadPath(), uri.replaceAll(".*/(.*?)", "$1"));
        new OkHttpDownloadRequest.Builder().url(uri).destFileDir(file.getParent()).destFileName(uri.replaceAll(".*/(.*?)", "$1")).download(new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Global.showMsg("保存失败");
            }


            @Override
            public void onResponse(String response) {
                Global.showMsg("图片已保存到:" + response);
            }
        });
    }

    public static void download(String uri, ResultCallback<String> callback) {
        File file = FileUtils.getDestinationInExternalPublicDir(FileUtils.getFileDownloadPath(), uri.replaceAll(".*/(.*?)", "$1"));
        new OkHttpDownloadRequest.Builder().url(uri).destFileDir(file.getParent()).destFileName(uri.replaceAll(".*/(.*?)", "$1")).download(callback);
    }

    public static void saveImgs(String dirName, final List<String> uris) {
        for (final String uri : uris) {
            File mFile = FileUtils.getDestinationInExternalPublicDir(FileUtils.getFileDownloadPath() + File.separator + dirName, uri.replaceAll(".*/(.*?)", "$1"));
            new OkHttpDownloadRequest.Builder().url(uri).destFileDir(mFile.getParent()).destFileName(uri.replaceAll(".*/(.*?)", "$1")).download(new ResultCallback<String>() {
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

    }

}
