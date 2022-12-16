package pl.edu.pw.mini.projektZPOIF.Other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import pl.edu.pw.mini.projektZPOIF.Services.UdpService;

@Configuration
public class Configs {


    final UdpService udpService;
    final UnicastReceivingChannelAdapter unicastReceivingChannelAdapter;



    @Autowired
    public Configs(UdpService udpService ,UnicastReceivingChannelAdapter unicastReceivingChannelAdapter) {
        this.udpService = udpService;
        this.unicastReceivingChannelAdapter = unicastReceivingChannelAdapter;
    }


    @Bean
    public IntegrationFlow processUniCastUdpMessage() {
        return IntegrationFlows
                .from(unicastReceivingChannelAdapter)
                .handle(udpService::receiveBulb)
                .get();
    }




}
