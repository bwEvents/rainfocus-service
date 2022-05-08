package com.servicenow.servicenowrf;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class ServicenowRfApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicenowRfApplication.class, args);
    }

    /**
     * This is introduced in order to outbound all the http requests through static IP's with custom proxy
     * Fixie is used as add-on in heroku
     * Rest template will now get initialized with this proxy
     * 54.173.229.200 & 54.175.230.252 are two static IP's this app will cycle through
     */
    @Bean
    public RestTemplate getRestTemplate() {
        String proxyUrl = "http://fixie:qIaBGXMqttWhP54@velodrome.usefixie.com:80";
        String[] splitValues = proxyUrl.split("[/(:@)]+");
        String username = splitValues[1];
        String password = splitValues[2];
        String host = splitValues[3];
        int port = Integer.parseInt(splitValues[4]);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(host, port),
                new UsernamePasswordCredentials(username, password));

        HttpHost proxy = new HttpHost(host, port);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        clientBuilder.setProxy(proxy).setDefaultCredentialsProvider(credentialsProvider).disableCookieManagement();

        HttpClient httpClient = clientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }
}
