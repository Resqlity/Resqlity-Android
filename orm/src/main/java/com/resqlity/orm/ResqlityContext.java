package com.resqlity.orm;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.resqlity.orm.consts.Endpoints;
import com.resqlity.orm.helpers.JsonHelper;
import com.resqlity.orm.inapp.BaseMessage;
import com.resqlity.orm.inapp.MessageType;
import com.resqlity.orm.queries.DeleteQuery;
import com.resqlity.orm.queries.InsertQuery;
import com.resqlity.orm.queries.SelectQuery;
import com.resqlity.orm.queries.UpdateQuery;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Resqlity Context
 */
public class ResqlityContext {
    String apiKey;
    Context context;
    int iconCode;

    /**
     * @param apiKey   Resqlity Api Key
     * @param context  Activity Context
     * @param iconCode Icon Code To use In Push Notification
     */
    public ResqlityContext(String apiKey, Context context, int iconCode) {
        this.apiKey = apiKey;
        this.context = context;
        this.iconCode = iconCode;
        try {
            Connect(apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param obj Query On Class
     * @return SelectQuery
     */
    public SelectQuery Select(Class<? extends Object> obj) {
        return new SelectQuery(obj, this);
    }

    /**
     * @param obj Query On Class
     * @return UpdateQuery
     */
    public UpdateQuery Update(Class<? extends Object> obj) {
        return new UpdateQuery(obj, this);
    }

    /**
     * @param obj Query On Class
     * @return
     */
    public DeleteQuery Delete(Class<? extends Object> obj) {
        return new DeleteQuery(obj, this);
    }

    /**
     * @param obj Query On Class
     * @return InsertQuery
     */
    public InsertQuery Insert(Class<? extends Object> obj) {
        return new InsertQuery(obj, this);
    }

    /**
     * @param notification Notification message
     */
    private void PushNotification(BaseMessage notification) {
        Notification builder = new NotificationCompat.Builder(context, "ResqlityPushNotificationActivities")
                .setContentTitle("Hey!")
                .setContentText(notification.getMessage())
                .setSmallIcon(iconCode)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder);
    }

    /**
     * @param apiKey Resqlity API Key
     * @throws IOException
     * @throws URISyntaxException
     */
    private void Connect(String apiKey) throws IOException, URISyntaxException {
        WebSocketClient client = new WebSocketClient(new URI(Endpoints.WEB_SOCKET_CLIENT_URL + apiKey)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
            }

            @Override
            public void onMessage(String message) {
                BaseMessage baseMessage = JsonHelper.Deserialize(message, BaseMessage.class);
                if (baseMessage.getType() == MessageType.PushNotification) {
                    PushNotification(baseMessage);
                } else if (baseMessage.getType() == MessageType.DbChanged) {
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
            }

            @Override
            public void onError(Exception ex) {

            }
        };
        client.connect();
    }

}
