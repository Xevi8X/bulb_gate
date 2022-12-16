package pl.edu.pw.mini.projektZPOIF.Other;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;

@Configuration
public class Configs {
    @Bean
    public IntegrationFlow processUniCastUdpMessage() {
        return IntegrationFlows
                .from(new UnicastReceivingChannelAdapter(11111))
                .handle("UdpService", "receiveBulb")
                .get();
    }
}
