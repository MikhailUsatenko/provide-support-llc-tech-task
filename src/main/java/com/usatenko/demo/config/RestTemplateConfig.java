package com.usatenko.demo.config;

import com.usatenko.demo.utils.JacksonUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

@Configuration
public class RestTemplateConfig {
    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int REST_TEMPLATE_MAX_CONNECTIONS = 200;
    private static final int REST_TEMPLATE_MAX_CONNECTIONS_PER_ROUTE = 50;

    @Primary
    @Bean(name = "mappingJackson2HttpMessageConverter")
    public MappingJackson2HttpMessageConverter jsonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(JacksonUtils.getMapper());
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        return converter;
    }

    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1.2", "TLSv1.1", "TLSv1"}, null, new NoopHostnameVerifier());

        // PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

        RequestConfig requestConfig = RequestConfig.custom().setStaleConnectionCheckEnabled(true).setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECT_TIMEOUT).setSocketTimeout(CONNECT_TIMEOUT).build();

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setSSLSocketFactory(csf).setMaxConnTotal(REST_TEMPLATE_MAX_CONNECTIONS).setMaxConnPerRoute(REST_TEMPLATE_MAX_CONNECTIONS_PER_ROUTE)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectionRequestTimeout(CONNECT_TIMEOUT);
        requestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        requestFactory.setReadTimeout(CONNECT_TIMEOUT);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        MappingJackson2HttpMessageConverter converter = jsonConverter();
        restTemplate.getMessageConverters().add(0, converter);
//        restTemplate.getInterceptors().add(loggingInterceptor);

        return restTemplate;
    }
}
