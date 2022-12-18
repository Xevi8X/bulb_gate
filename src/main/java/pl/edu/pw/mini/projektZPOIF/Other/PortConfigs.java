package pl.edu.pw.mini.projektZPOIF.Other;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class PortConfigs {

    @Value("${tcpport.start:11000}")
    private int tcpStart;

    @Value("${udpport:10000}")
    private int udpPort;

    @Bean
    public int getTcpStart()
    {
        return tcpStart;
    }

    @Bean
    public int getUdpPort()
    {
        return udpPort;
    }
}
