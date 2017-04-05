package com.mshvdvskgmail.technoparkmessenger.network;

import android.util.Log;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.network.model.*;


/**
 * Created by Calc on 18.09.2016.
 *
 * @deprecated use service for release
 */
@Deprecated
public class RabbitMQ {
    private final static String TAG = RabbitMQ.class.toString();

    private Connection connection;
    private Channel channel;
    private String queue;
    private Gson gson = new Gson();
    private String uri;
    private String unique_id;

    private int prefetchCount = 20;

    public RabbitMQ(String uri, String unique_id){
        this.uri = uri;
        this.unique_id = unique_id;
    }

    public void connect() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            Log.i(TAG, "connected");

            queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, unique_id, "");

            Log.d(TAG, "queue created: " + queue);

            channel.basicQos(prefetchCount);
            channel.basicConsume(queue, false, new MessageConsumer(channel));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException, TimeoutException {
        if(channel.isOpen()) channel.close();
        if(connection.isOpen()) connection.close();
    }

    public String getQueue() {
        return queue;
    }

//    public void sendChinChin(User user2, Message message){
//        try {
//            String json = gson.toJson(message);
//            Log.d(TAG, json);
//            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
//                    .replyTo(queue)
//                    .build();
//            channel.basicPublish("conversation.outgoing", "chin-chin.0." + user2.unique_id, properties, json.getBytes());
//        } catch (IOException e) {
//            Log.w(TAG, e.getMessage());
//        }
//    }
//
    public void sendMessage(User other, Message message){
        try {
            String json = gson.toJson(message);
            Log.d(TAG, json);
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .replyTo(queue)
                    .build();
            channel.basicPublish("conversation.outgoing", "dialog." + message.sender.id + "." + other.unique_id, properties, json.getBytes());
        } catch (IOException e) {
            Log.w(TAG, e.getMessage());
        }
    }

    /**
     *
     * @param me
     * @param other
     * @param result 1 or -1
     */
    public void sendChinAccept(User me, User other, int result) {
        Message message = new Message();
        message.sender = me;
        message.user_token = me.token;
//        message.wifi_token = Controller.getInstance().getAuth().wifiToken;
        message.message = Integer.toString(result);

        try {
            String json = gson.toJson(message);
            Log.d(TAG, json);
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .replyTo(queue)
                    .build();
            channel.basicPublish("conversation.outgoing", "chin-accept." + me.id + "." + other.unique_id, properties, json.getBytes());
        } catch (IOException e) {
            Log.w(TAG, e.getMessage());
        }

    }

    private class MessageConsumer extends DefaultConsumer {
        /**
         * Constructs a new instance and records its association to the passed-in channel.
         *
         * @param channel the channel to which this consumer is attached
         */
        public MessageConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//            properties.getR
            String msg = new String(body, "UTF-8");
            Log.d(TAG, msg);

            try {
                Message message = gson.fromJson(msg, Message.class);
                String routingKey = envelope.getRoutingKey();
                Log.d(TAG, "Routing key " + routingKey);
                Log.d(TAG, "msg: " + msg);
//                String id = properties.getCorrelationId();
//                String data = gson.toJson(transport.data);
//                String token = transport.user_token;
//                String event = transport.event;
//                log.d(String.format("<-- %s %s %s", id, event, data));

//                switch (message.headers.type){
//                    case "system":
////                        User user = gson.fromJson(data, User.class);
////                        authCallback.exec(user, id);
//                        break;
//                    case "user":
////                        if(systemCallback != null) systemCallback.execute(transport);
//                        break;
//                    default:
//                        Log.w(TAG, "unknown type " + message.headers.type);
//                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception e){
                channel.basicNack(envelope.getDeliveryTag(), false, true);
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            } finally {
                Log.d(TAG, " [x] Done.");
            }
        }
    }
}
