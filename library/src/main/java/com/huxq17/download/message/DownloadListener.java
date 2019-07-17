package com.huxq17.download.message;

import android.text.TextUtils;

import com.huxq17.download.DownloadInfo;
import com.huxq17.download.DownloadInfoSnapshot;
import com.huxq17.download.Pump;

public class DownloadListener {
    private final String url;
    private DownloadInfo.Status status;
    private boolean enable;

    public DownloadListener() {
        url = null;
    }

    public DownloadListener(String url) {
        this.url = url;
    }

    /**
     * Disable this observer and Pump will remove this observer later.
     */
    public final void disable() {
        Pump.unSubscribe(this);
    }

    void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * Enable this Observer.
     */
    public final void enable() {
        if (!enable) {
            Pump.subscribe(this);
        }
    }

    public final boolean isEnable() {
        return enable;
    }

    private DownloadInfo downloadInfo;


    public final DownloadInfo.Status getStatus() {
        return status;
    }

    public final DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public final void downloading(DownloadInfoSnapshot downloadInfoSnapshot) {
        DownloadInfo downloadInfo = downloadInfoSnapshot.downloadInfo;
        long completedSize = downloadInfoSnapshot.completedSize;
        DownloadInfo.Status status = downloadInfoSnapshot.status;
        this.downloadInfo = downloadInfo;
        this.status = status;
        long contentLength = downloadInfo.getContentLength();
        int progress = contentLength == 0 ? 0 : (int) (completedSize * 1f / contentLength * 100);
        onProgress(progress);
        if (progress == 100) {
            onSuccess();
        } else if (status == DownloadInfo.Status.FAILED) {
            onFailed();
        }
    }

    public String getUrl() {
        return url;
    }

    /**
     * Filter the download information to be received, all received by default.
     *
     * @param downloadInfo The download info.
     * @return Receive if return true, or not receive.
     */
    public boolean filter(DownloadInfo downloadInfo) {
        if (!TextUtils.isEmpty(url)) {
            return url.contains(downloadInfo.getUrl());
        }
        return true;
    }

    public void onProgress(int progress) {
    }


    public void onSuccess() {
    }

    public void onFailed() {
    }

    @Override
    public int hashCode() {
        if (!TextUtils.isEmpty(url)) {
            return url.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (DownloadListener.class.isAssignableFrom(obj.getClass())) {
            DownloadListener that = (DownloadListener) obj;
            String thatUrl = that.getUrl();
            if (!TextUtils.isEmpty(thatUrl) && thatUrl.equals(getUrl())) {
                return true;
            }
        }
        return super.equals(obj);
    }
}