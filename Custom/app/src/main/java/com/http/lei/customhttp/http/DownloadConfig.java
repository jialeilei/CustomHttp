package com.http.lei.customhttp.http;

/**
 * Created by lei on 2017/5/9.
 */
public class DownloadConfig {

    private int coreThreadSize;
    private int maxThreadSize;
    private int localProgressThreadSize;

    private DownloadConfig(Builder builder){

        coreThreadSize = builder.coreThreadSize == 0 ? DownloadManager.CORE_POOL_SIZE : builder.coreThreadSize;
        maxThreadSize = builder.maxThreadSize == 0 ? DownloadManager.MAX_POOL_SIZE : builder.maxThreadSize;
        localProgressThreadSize = builder.localProgressThreadSize == 0 ? DownloadManager.PROGRESS_POOL_SIZE : builder.localProgressThreadSize;
    }

    public int getLocalProgressThreadSize() {
        return localProgressThreadSize;
    }

    public void setLocalProgressThreadSize(int localProgressThreadSize) {
        this.localProgressThreadSize = localProgressThreadSize;
    }

    public int getCoreThreadSize() {
        return coreThreadSize;
    }

    public void setCoreThreadSize(int coreThreadSize) {
        this.coreThreadSize = coreThreadSize;
    }

    public int getMaxThreadSize() {
        return maxThreadSize;
    }

    public void setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
    }

    public static class Builder{
        private int coreThreadSize;
        private int maxThreadSize;
        private int localProgressThreadSize;


        public DownloadConfig build(){
            return new DownloadConfig(this);
        }

        public int getMaxThreadSize() {
            return maxThreadSize;
        }

        public Builder setMaxThreadSize(int maxThreadSize) {
            this.maxThreadSize = maxThreadSize;
            return this;
        }

        public int getCoreThreadSize() {
            return coreThreadSize;
        }

        public Builder setCoreThreadSize(int coreThreadSize) {
            this.coreThreadSize = coreThreadSize;
            return this;
        }

        public int getLocalProgressThreadSize() {
            return localProgressThreadSize;
        }

        public Builder setLocalProgressThreadSize(int localProgerssThreadSize) {
            this.localProgressThreadSize = localProgerssThreadSize;
            return this;
        }
    }
}
