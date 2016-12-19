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

import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.OneLineRequest;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.internal.ANRequestQueue;

/**
 * Created by Dhruv Kaushal on 24/11/16.
 */

public class OneLineLibrary {
    private OneLineLibrary() {
    }



    public static OneLineRequest.GetRequestBuilder load(String url) {
        return new OneLineRequest.GetRequestBuilder(url);
    }


    public static OneLineRequest.HeadRequestBuilder head(String url) {
        return new OneLineRequest.HeadRequestBuilder(url);
    }


    public static OneLineRequest.PostRequestBuilder post(String url) {
        return new OneLineRequest.PostRequestBuilder(url);
    }

    /**
     * Method to make PUT request
     *
     *
     */
    public static OneLineRequest.PutRequestBuilder put(String url) {
        return new OneLineRequest.PutRequestBuilder(url);
    }

    /**
     * Method to make DELETE request
     *
     */
    public static OneLineRequest.DeleteRequestBuilder delete(String url) {
        return new OneLineRequest.DeleteRequestBuilder(url);
    }



    /**
     * Method to make upload request
     *
     *
     */
    public static OneLineRequest.MultiPartBuilder upload(String url) {
        return new OneLineRequest.MultiPartBuilder(url);
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
     * Method to cancel all given request
     */
    public static void cancelAll() {
        ANRequestQueue.getInstance().cancelAll(false);
    }





}
