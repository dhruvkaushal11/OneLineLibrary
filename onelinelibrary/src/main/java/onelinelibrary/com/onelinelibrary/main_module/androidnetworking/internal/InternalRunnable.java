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
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.ANResponse;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.Priority;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.ResponseType;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.core.Core;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.error.ANError;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.utils.SourceCloseUtil;
import onelinelibrary.com.onelinelibrary.main_module.androidnetworking.utils.Utils;

import okhttp3.Response;

import static onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.RequestType.DOWNLOAD;
import static onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.RequestType.MULTIPART;
import static onelinelibrary.com.onelinelibrary.main_module.androidnetworking.common.RequestType.SIMPLE;
public class InternalRunnable implements Runnable {

    private final Priority priority;
    public final int sequence;
    public final OneLineRequest request;

    public InternalRunnable(OneLineRequest request) {
        this.request = request;
        this.sequence = request.getSequenceNumber();
        this.priority = request.getPriority();
    }

    @Override
    public void run() {
        ANLog.d("execution started : " + request.toString());
        switch (request.getRequestType()) {
            case SIMPLE:
                executeSimpleRequest();
                break;
            case DOWNLOAD:
                executeDownloadRequest();
                break;
            case MULTIPART:
                executeUploadRequest();
                break;
        }
        ANLog.d("execution done : " + request.toString());
    }

    private void executeSimpleRequest() {
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performSimpleRequest(request);

            if (okHttpResponse == null) {
                deliverError(request, Utils.getErrorForConnection(new ANError()));
                return;
            }

            if (request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                request.deliverOkHttpResponse(okHttpResponse);
                return;
            }
            if (okHttpResponse.code() >= 400) {
                deliverError(request, Utils.getErrorForServerResponse(new ANError(okHttpResponse),
                        request, okHttpResponse.code()));
                return;
            }

            ANResponse response = request.parseResponse(okHttpResponse);
            if (!response.isSuccess()) {
                deliverError(request, response.getError());
                return;
            }
            response.setOkHttpResponse(okHttpResponse);
            request.deliverResponse(response);
        } catch (Exception e) {
            deliverError(request, Utils.getErrorForConnection(new ANError(e)));
        } finally {
            SourceCloseUtil.close(okHttpResponse, request);
        }
    }

    private void executeDownloadRequest() {
        Response okHttpResponse;
        try {
            okHttpResponse = InternalNetworking.performDownloadRequest(request);
            if (okHttpResponse == null) {
                deliverError(request, Utils.getErrorForConnection(new ANError()));
                return;
            }
            if (okHttpResponse.code() >= 400) {
                deliverError(request, Utils.getErrorForServerResponse(new ANError(okHttpResponse),
                        request, okHttpResponse.code()));
                return;
            }
            request.updateDownloadCompletion();
        } catch (Exception e) {
            deliverError(request, Utils.getErrorForConnection(new ANError(e)));
        }
    }

    private void executeUploadRequest() {
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performUploadRequest(request);

            if (okHttpResponse == null) {
                deliverError(request, Utils.getErrorForConnection(new ANError()));
                return;
            }

            if (request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                request.deliverOkHttpResponse(okHttpResponse);
                return;
            }

            if (okHttpResponse.code() >= 400) {
                deliverError(request, Utils.getErrorForServerResponse(new ANError(okHttpResponse),
                        request, okHttpResponse.code()));
                return;
            }
            ANResponse response = request.parseResponse(okHttpResponse);
            if (!response.isSuccess()) {
                deliverError(request, response.getError());
                return;
            }
            response.setOkHttpResponse(okHttpResponse);
            request.deliverResponse(response);
        } catch (Exception e) {
            deliverError(request, Utils.getErrorForConnection(new ANError(e)));
        } finally {
            SourceCloseUtil.close(okHttpResponse, request);
        }
    }

    public Priority getPriority() {
        return priority;
    }

    private void deliverError(final OneLineRequest request, final ANError anError) {
        Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
            public void run() {
                request.deliverError(anError);
                request.finish();
            }
        });
    }
}
