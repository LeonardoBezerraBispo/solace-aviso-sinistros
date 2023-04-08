package com.aberturaavisoservice.aberturasinistro.view;

import java.util.Properties;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.SolaceProperties.AuthenticationProperties;
import com.solace.messaging.config.SolaceProperties.ServiceProperties;
import com.solace.messaging.config.SolaceProperties.TransportLayerProperties;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.publisher.OutboundMessage;
import com.solace.messaging.publisher.OutboundMessageBuilder;
import com.solace.messaging.publisher.PersistentMessagePublisher;
import com.solace.messaging.resources.Topic;

public class AberturaAvisoService {
    private static final String prefix = "santander/seguros/v1/aviso";

    public void aberturaSin (String payload) {
        final Properties properties = new Properties();
        properties.setProperty(TransportLayerProperties.HOST,
                "tcps://mr-connection-ofkana94oce.messaging.solace.cloud:55443");
        properties.setProperty(ServiceProperties.VPN_NAME, "avisosinistros");
        properties.setProperty(AuthenticationProperties.SCHEME_BASIC_USER_NAME, "solace-cloud-client");
        properties.setProperty(AuthenticationProperties.SCHEME_BASIC_PASSWORD, "dm11o23q99oplqm4pmqquro0s1");
        final MessagingService messagingService = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(properties)
                .build()
                .connect();

        final PersistentMessagePublisher publisher = messagingService.createPersistentMessagePublisherBuilder()
                .onBackPressureWait(1).build().start();

        OutboundMessageBuilder messageBuilder = messagingService.messageBuilder();
        OutboundMessage message = messageBuilder.build(payload.toString());
        String topicString = prefix + "/aberto";
        publisher.publish(message, Topic.of(topicString));
        
    }

}
