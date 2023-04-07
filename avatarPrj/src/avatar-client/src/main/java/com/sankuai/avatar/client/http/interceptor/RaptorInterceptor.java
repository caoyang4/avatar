package com.sankuai.avatar.client.http.interceptor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.spi.MessageTree;
import com.sankuai.avatar.client.http.utils.UrlUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * raptor打点 拦截器
 * @author xk
 */
public class RaptorInterceptor implements Interceptor {
    private final String remoteDomain;

    public RaptorInterceptor(String remoteDomain) {
        this.remoteDomain = remoteDomain;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        HttpUrl url = request.url();
        Transaction t = Cat.newTransaction("HttpCall", UrlUtils.getRequestURI(url.uri().getPath()));
        try {
            request = logTrace(request, url);
            response = chain.proceed(request);
        } catch (IOException e) {
            Cat.logError(e);
            throw e;
        } finally {
            t.complete();
        }
        return response;
    }

    private Request logTrace(Request request, HttpUrl url) {
        MessageTree tree = Cat.getManager().getThreadLocalMessageTree();
        String messageId = tree.getMessageId();

        if (messageId == null) {
            messageId = Cat.getProducer().createMessageId();
            tree.setMessageId(messageId);
        }

        String childId = Cat.getProducer().createRpcServerId(remoteDomain);

        String root = tree.getRootMessageId();

        if (root == null) {
            root = messageId;
        }

        Cat.logEvent("HttpCall.Domain", remoteDomain);
        Cat.logEvent("HttpCall.Host", url.host());
        Cat.logEvent("RemoteCall", "", Message.SUCCESS, childId);

        Request.Builder builder = request.newBuilder();
        builder.addHeader("HttpCall", "true")
                .addHeader("RootMessageId", root)
                .addHeader("MessageId", messageId)
                .addHeader("ChildMessageId", childId);

        return builder.build();
    }
}
