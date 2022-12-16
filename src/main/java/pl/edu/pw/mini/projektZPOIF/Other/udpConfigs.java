package pl.edu.pw.mini.projektZPOIF.Other;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;

import java.net.DatagramSocket;

@Configuration
public class udpConfigs {

    private final UnicastReceivingChannelAdapter unicastReceivingChannelAdapter;

    public udpConfigs() {
        this.unicastReceivingChannelAdapter = new UnicastReceivingChannelAdapter(11111);
    }

    @Bean
    public UnicastReceivingChannelAdapter getUnicastReceivingChannelAdapter()
    {
        return unicastReceivingChannelAdapter;
    }

    @Bean
    public DatagramSocket getDatagramSocket () {
        return unicastReceivingChannelAdapter.getSocket();
    }
}
