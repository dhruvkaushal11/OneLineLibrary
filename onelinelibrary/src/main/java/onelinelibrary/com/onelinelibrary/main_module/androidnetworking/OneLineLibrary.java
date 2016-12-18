/*
 *    Copyright (C) 2016 Amit Shekhar
 *    Copyright (C) 2011 Android Open Source Project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package onelinelibrary.com.onelinelibrary.main_module.androidnetworking;

import android.content.Context;

import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.ANConstants;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.ANLog;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.ANRequest;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.ConnectionClassManager;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.ConnectionQuality;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.core.Core;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.interfaces.ConnectionQualityChangeListener;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.interfaces.Parser;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.internal.ANImageLoader;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.internal.ANRequestQueue;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.internal.InternalNetworking;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.utils.ParseUtil;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.utils.Utils;

import okhttp3.OkHttpClient;

/**
 * Created by Dhruv Kaushal on 24/11/16.
 */

@SuppressWarnings("unused")
public class OneLineLibrary {
    private OneLineLibrary() {
    }

    public static void initialize(Context context) {
        InternalNetworking.setClientWithCache(context.getApplicationContext());
        ANRequestQueue.initialize();
        ANImageLoader.initialize();
    }


    public static void initialize(Context context, OkHttpClient okHttpClient) {
        if (okHttpClient != null && okHttpClient.cache() == null) {
            okHttpClient = okHttpClient
                    .newBuilder()
                    .cache(Utils.getCache(context.getApplicationContext(),
                            ANConstants.MAX_CACHE_SIZE, ANConstants.CACHE_DIR_NAME))
                    .build();
        }
        InternalNetworking.setClient(okHttpClient);
        ANRequestQueue.initialize();
        ANImageLoader.initialize();
    }


    public static void setConnectionQualityChangeListener(ConnectionQualityChangeListener connectionChangeListener) {
        ConnectionClassManager.getInstance().setListener(connectionChangeListener);
    }


    public static void removeConnectionQualityChangeListener() {
        ConnectionClassManager.getInstance().removeListener();
    }


    public static ANRequest.GetRequestBuilder load(String url) {
        return new ANRequest.GetRequestBuilder(url);
    }


    public static ANRequest.HeadRequestBuilder head(String url) {
        return new ANRequest.HeadRequestBuilder(url);
    }


    public static ANRequest.PostRequestBuilder post(String url) {
        return new ANRequest.PostRequestBuilder(url);
    }

    /**
     * Method to make PUT request
     *
     *
     */
    public static ANRequest.PutRequestBuilder put(String url) {
        return new ANRequest.PutRequestBuilder(url);
    }

    /**
     * Method to make DELETE request
     *
     */
    public static ANRequest.DeleteRequestBuilder delete(String url) {
        return new ANRequest.DeleteRequestBuilder(url);
    }


    /**
     * Method to make download request
     *
     */
    public static ANRequest.DownloadBuilder download(String url, String dirPath, String fileName) {
        return new ANRequest.DownloadBuilder(url, dirPath, fileName);
    }

    /**
     * Method to make upload request
     *
     *
     */
    public static ANRequest.MultiPartBuilder upload(String url) {
        return new ANRequest.MultiPartBuilder(url);
    }

    /**
     * Method to cancel requests with the given tag
     *
     *
     */
    public static void cancel(Object tag) {
        ANRequestQueue.getInstance().cancelRequestWithGivenTag(tag, false);
    }

    /**
     * Method to force cancel requests with the given tag
     *
     *
     */
    public static void forceCancel(Object tag) {
        ANRequestQueue.getInstance().cancelRequestWithGivenTag(tag, true);
    }

    /**
     * Method to cancel all given request
     */
    public static void cancelAll() {
        ANRequestQueue.getInstance().cancelAll(false);
    }

    /**
     * Method to force cancel all given request
     */
    public static void forceCancelAll() {
        ANRequestQueue.getInstance().cancelAll(true);
    }

    /**
     * Method to enable logging
     */
    public static void enableLogging() {
        ANLog.enableLogging();
    }

    /**
     * Method to enable logging with tag
     *
     * @param tag The tag for logging
     */
    public static void enableLogging(String tag) {
        ANLog.enableLogging();
        ANLog.setTag(tag);
    }

    /**
     * Method to disable logging
     */
    public static void disableLogging() {
        ANLog.disableLogging();
    }

    /**
     * Method to evict a bitmap with given key from LruCache
     *
     *
     */
    public static void evictBitmap(String key) {
        final ANImageLoader.ImageCache imageCache = ANImageLoader.getInstance().getImageCache();
        if (imageCache != null && key != null) {
            imageCache.evictBitmap(key);
        }
    }

    /**
     * Method to clear LruCache
     */
    public static void evictAllBitmap() {
        final ANImageLoader.ImageCache imageCache = ANImageLoader.getInstance().getImageCache();
        if (imageCache != null) {
            imageCache.evictAllBitmap();
        }
    }

    /**
     * Method to set userAgent globally
     *
     *
     */
    public static void setUserAgent(String userAgent) {
        InternalNetworking.setUserAgent(userAgent);
    }

    /**
     * Method to get currentBandwidth
     *
     * @return currentBandwidth
     */
    public static int getCurrentBandwidth() {
        return ConnectionClassManager.getInstance().getCurrentBandwidth();
    }
    /**
     * Method to get currentConnectionQuality
     *
     *
     */
    public static ConnectionQuality getCurrentConnectionQuality() {
        return ConnectionClassManager.getInstance().getCurrentConnectionQuality();
    }
    /**
     * Method to set ParserFactory
     *
     */
    public static void setParserFactory(Parser.Factory parserFactory) {
        ParseUtil.setParserFactory(parserFactory);
    }

    /**
     * Shuts AndroidNetworking down
     */
    public static void shutDown() {
        Core.shutDown();
        evictAllBitmap();
        ConnectionClassManager.getInstance().removeListener();
        ConnectionClassManager.shutDown();
        ParseUtil.shutDown();
    }
}
