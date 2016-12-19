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

package onelinelibrary.com.onelinelibrary.main_module.androidnetworking.internal;

import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.ANLog;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.OneLineRequest;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.Priority;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.core.Core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amitshekhar on 22/03/16.
 */
public class ANRequestQueue {

    private final static String TAG = ANRequestQueue.class.getSimpleName();
    private final Set<OneLineRequest> mCurrentRequests = new HashSet<>();
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    private static ANRequestQueue sInstance = null;

    public static void initialize() {
        getInstance();
    }

    public static ANRequestQueue getInstance() {
        if (sInstance == null) {
            synchronized (ANRequestQueue.class) {
                if (sInstance == null) {
                    sInstance = new ANRequestQueue();
                }
            }
        }
        return sInstance;
    }

    public interface RequestFilter {
        boolean apply(OneLineRequest request);
    }


    private void cancel(RequestFilter filter, boolean forceCancel) {
        synchronized (mCurrentRequests) {
            try {
                for (Iterator<OneLineRequest> iterator = mCurrentRequests.iterator(); iterator.hasNext(); ) {
                    OneLineRequest request = iterator.next();
                    if (filter.apply(request)) {
                        request.cancel(forceCancel);
                        if (request.isCanceled()) {
                            request.destroy();
                            iterator.remove();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelAll(boolean forceCancel) {
        synchronized (mCurrentRequests) {
            try {
                for (Iterator<OneLineRequest> iterator = mCurrentRequests.iterator(); iterator.hasNext(); ) {
                    OneLineRequest request = iterator.next();
                    request.cancel(forceCancel);
                    if (request.isCanceled()) {
                        request.destroy();
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelRequestWithGivenTag(final Object tag, final boolean forceCancel) {
        try {
            if (tag == null) {
                return;
            }
            cancel(new RequestFilter() {
                @Override
                public boolean apply(OneLineRequest request) {
                    if (request.getTag() instanceof String && tag instanceof String) {
                        final String tempRequestTag = (String) request.getTag();
                        final String tempTag = (String) tag;
                        return tempRequestTag.equals(tempTag);
                    }
                    return request.getTag().equals(tag);
                }
            }, forceCancel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSequenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }

    public OneLineRequest addRequest(OneLineRequest request) {
        synchronized (mCurrentRequests) {
            try {
                mCurrentRequests.add(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            request.setSequenceNumber(getSequenceNumber());
            if (request.getPriority() == Priority.IMMEDIATE) {
                request.setFuture(Core.getInstance()
                        .getExecutorSupplier()
                        .forImmediateNetworkTasks()
                        .submit(new InternalRunnable(request)));
            } else {
                request.setFuture(Core.getInstance()
                        .getExecutorSupplier()
                        .forNetworkTasks()
                        .submit(new InternalRunnable(request)));
            }
            ANLog.d("addRequest: after addition - mCurrentRequests size: " + mCurrentRequests.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    public void finish(OneLineRequest request) {
        synchronized (mCurrentRequests) {
            try {
                mCurrentRequests.remove(request);
                ANLog.d("finish: after removal - mCurrentRequests size: " + mCurrentRequests.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
