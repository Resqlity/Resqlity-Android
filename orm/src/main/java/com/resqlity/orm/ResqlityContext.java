package com.resqlity.orm;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.resqlity.orm.helpers.JsonHelper;
import com.resqlity.orm.inapp.BaseMessage;
import com.resqlity.orm.inapp.MessageType;
import com.resqlity.orm.inapp.PushNotification;
import com.resqlity.orm.queries.DeleteQuery;
import com.resqlity.orm.queries.InsertQuery;
import com.resqlity.orm.queries.SelectQuery;
import com.resqlity.orm.queries.UpdateQuery;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ResqlityContext {
    String apiKey;
    Context context;
    int iconCode;

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

    public SelectQuery Select(Class<? extends Object> obj) {
        return new SelectQuery(obj, this);
    }

    public UpdateQuery Update(Class<? extends Object> obj) {
        return new UpdateQuery(obj, this);
    }

    public DeleteQuery Delete(Class<? extends Object> obj) {
        return new DeleteQuery(obj, this);
    }

    public InsertQuery Insert(Class<? extends Object> obj) {
        return new InsertQuery(obj, this);
    }

    private void PushNotification(BaseMessage notification) {
        Notification builder = new NotificationCompat.Builder(context, "ResqlityPushNotificationActivities")
                .setContentTitle("Hey!")
                .setContentText(notification.getMessage())
                .setSmallIcon(iconCode)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder);
    }

    private void Connect(String apiKey) throws IOException, URISyntaxException {
        WebSocketClient client = new WebSocketClient(new URI("ws://10.0.2.2:55391/realtime?apiKey=" + apiKey)) {
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
