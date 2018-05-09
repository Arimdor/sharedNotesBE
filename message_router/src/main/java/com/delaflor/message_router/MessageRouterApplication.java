package com.delaflor.message_router;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootApplication
public class MessageRouterApplication {

    private static final Log LOGGER = LogFactory.getLog(MessageRouterApplication.class);
    final String url = "http://13.59.36.111/index.php/api/firebase";

    public static void main(String[] args) {
        SpringApplication.run(MessageRouterApplication.class, args);
    }

    // Configuracion para recibir los datos de la cola mediante la subscripción pedidos.SUB
    @Bean
    public MessageChannel notificationInputChanel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("notificationInputChanel") MessageChannel inputChannel,
            PubSubOperations pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "notifications.SUB");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "notificationInputChanel")
    public MessageHandler messageReceiver() {
        return message -> {
            LOGGER.info("Message arrived! Payload: " + message.getPayload());
            AckReplyConsumer consumer = (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);
            LOGGER.debug("Message: " + message.getPayload());
            message.getPayload();
            assert consumer != null;
            consumer.ack();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("message", "Nueva libreta");
            map.add("token", message.getPayload().toString());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            LOGGER.debug("Response: " + response);
        };
    }
}
