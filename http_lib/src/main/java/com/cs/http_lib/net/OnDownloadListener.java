package com.cs.http_lib.net;

/**
 * Created by admin on 2018/1/9.
 */

public interface OnDownloadListener {

    /**
     * 下载成功,返回文件路径
     */
    void onDownloadSuccess(String path);

    /**
     * @param progress 下载进度
     */
    void onDownloading(int progress);

    /**
     * 下载失败
     */
    void onDownloadFailed();
}
