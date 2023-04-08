package com.notificationservice.notification.view;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.SolaceProperties.AuthenticationProperties;
import com.solace.messaging.config.SolaceProperties.ServiceProperties;
import com.solace.messaging.config.SolaceProperties.TransportLayerProperties;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.receiver.InboundMessage;
import com.solace.messaging.receiver.PersistentMessageReceiver;
import com.solace.messaging.resources.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NotificationService {

    private static final String QUEUE_NAME = "santander/seguros/v1/aviso";
    static final String TOPIC_PREFIX = "santander/seguros/v1/aviso";

    private static volatile boolean isShutdown = false; // are we done?
    private static final Logger logger = LogManager.getLogger();

    public List<String> payload = new ArrayList<String>();

    public String notificationPayload() throws IOException, InterruptedException {
        final Properties properties = new Properties();
        properties.setProperty(TransportLayerProperties.HOST,
                "tcps://mr-connection-ofkana94oce.messaging.solace.cloud:55443");
        properties.setProperty(ServiceProperties.VPN_NAME, "avisosinistros");
        properties.setProperty(AuthenticationProperties.SCHEME_BASIC_USER_NAME, "solace-cloud-client");
        properties.setProperty(AuthenticationProperties.SCHEME_BASIC_PASSWORD, "dm11o23q99oplqm4pmqquro0s1");
        final MessagingService messagingService = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(properties)
                .build();

        messagingService.connect();

        final PersistentMessageReceiver receiver = messagingService
                .createPersistentMessageReceiverBuilder()
                .build(Queue.durableExclusiveQueue(QUEUE_NAME));
                
        try {
            receiver.start();
        } catch (RuntimeException e) {
            logger.error(e);
            System.err.printf("%n*** Conexão não estabeliciada com a Fila '%s': %s%n", QUEUE_NAME, e.getMessage());
            return null;
        }

        while (System.in.available() == 0 && !isShutdown) {
            InboundMessage inboundMsg = receiver.receiveMessage(1000);
            if (inboundMsg == null) {
                break;
            }
            String inboundTopic = inboundMsg.getDestinationName();
            logger.info("Topico recebido - " + inboundTopic);
            receiver.ack(inboundMsg);
            System.out.println(inboundMsg.getPayloadAsString());
            this.payload.add(inboundMsg.getPayloadAsString());

        }
        System.out.println("Fim loop");
        receiver.terminate(1500L);
        Thread.sleep(1000);
        messagingService.disconnect();
        System.out.println(this.payload);
        return this.payload.toString();
    }
}
