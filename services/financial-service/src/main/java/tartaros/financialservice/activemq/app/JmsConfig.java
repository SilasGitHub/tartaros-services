package tartaros.financialservice.activemq.app;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import jakarta.jms.ConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@EnableJms
@Configuration
public class JmsConfig {

    @Value("${activemq.broker-url}")
    private String brokerUrl;

    @Value("${activemq.broker-username}")
    private String brokerUsername;

    @Value("${activemq.broker-password}")
    private String brokerPassword;

//    @Bean
//    public ActiveMQConnectionFactory activeMQConnectionFactory() {
//        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//
//        activeMQConnectionFactory.setBrokerURL(brokerUrl);
//
//        if(brokerUsername != null && !brokerUsername.isEmpty() && brokerPassword != null && !brokerPassword.isEmpty()) {
//            activeMQConnectionFactory.setUserName(brokerUsername);
//            activeMQConnectionFactory.setPassword(brokerPassword);
//        }
//
//        return activeMQConnectionFactory;
//    }


//    @Bean
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new SimpleMessageConverter());
//        factory.setConcurrency("5-10");
//        return factory;
//    }


}
