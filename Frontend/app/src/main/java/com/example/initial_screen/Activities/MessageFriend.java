package com.example.initial_screen.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.initial_screen.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MessageFriend extends AppCompatActivity {

    private OkHttpClient client;
    private WebSocket ws;
    private Button btnSendMsg;
    private EditText etMsg;
    private TextView tvMessages;

    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            ws.send("Connection succeeded");
            tvMessages.setText(response.message());
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            webSocket.send(text);
            output(text);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error: " + t.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_friend);

        client = new OkHttpClient();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        Request request = new Request.Builder()
                .url("https://localhost:8080")
                .build();
        ws = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                webSocket.send(etMsg.getText().toString());
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
            }
        });

        //listener.onMessage(ws, etMsg);

        etMsg = (EditText) findViewById(R.id.etMsg);
        tvMessages = (TextView) findViewById(R.id.tvMessages);

        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send(etMsg.getText().toString());
                sendMsg();
            }
        });
    }

    private void sendMsg() {
        //Request request = new Request.Builder().url("http://cs309-sd-4.misc.iastate.edu:8080/").build();
        /*
        Request request = new Request.Builder()
                .url("https://localhost:8080")
                .build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        listener.onMessage(ws, etMsg.getText().toString());
        */
        ws.send(etMsg.getText().toString());

        //client.dispatcher().executorService().shutdown();
    }

    private void output(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvMessages.setText(tvMessages.getText().toString() + "\n\n" + text);
            }
        });
    }
}
