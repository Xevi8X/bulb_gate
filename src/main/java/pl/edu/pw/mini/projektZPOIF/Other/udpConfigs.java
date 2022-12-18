package pl.edu.pw.mini.projektZPOIF.Other;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;

import java.net.DatagramSocket;

@Configuration
public class udpConfigs {

    private final UnicastReceivingChannelAdapter unicastReceivingChannelAdapter;

    public udpConfigs(@Qualifier("getUdpPort") int udpPort) {
        this.unicastReceivingChannelAdapter = new UnicastReceivingChannelAdapter(udpPort);
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
