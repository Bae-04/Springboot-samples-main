package top.yxyan.springbootsamples.task;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.zip.GZIPInputStream;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .requestFactory(() -> {
                    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
                    factory.setConnectTimeout((int) Duration.ofSeconds(10).toMillis());
                    factory.setReadTimeout((int) Duration.ofSeconds(15).toMillis());
                    return factory;
                })
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .build();

        // 添加拦截器处理gzip解压
        restTemplate.setInterceptors(Collections.singletonList(new GzipInterceptor()));

        return restTemplate;
    }

    /**
     * 拦截器：处理gzip压缩的响应
     */
    static class GzipInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().add("Accept-Encoding", "gzip, deflate");
            request.getHeaders().add("User-Agent", "SpringBoot-WeatherApp/1.0");

            ClientHttpResponse response = execution.execute(request, body);

            HttpHeaders headers = response.getHeaders();
            String contentEncoding = headers.getFirst("Content-Encoding");
            if (contentEncoding != null && contentEncoding.contains("gzip")) {
                return new GzipDecompressingClientHttpResponse(response);
            }
            return response;
        }
    }

    /**
     * 解压gzip响应（Spring Boot 3.x 版本）
     */
    static class GzipDecompressingClientHttpResponse implements ClientHttpResponse {
        private final ClientHttpResponse originalResponse;
        private byte[] decompressedBody;

        GzipDecompressingClientHttpResponse(ClientHttpResponse response) throws IOException {
            this.originalResponse = response;
            try (GZIPInputStream gzipStream = new GZIPInputStream(originalResponse.getBody())) {
                this.decompressedBody = StreamUtils.copyToByteArray(gzipStream);
            }
        }

        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return originalResponse.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return originalResponse.getStatusText();
        }

        @Override
        public HttpHeaders getHeaders() {
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(originalResponse.getHeaders());
            headers.remove("Content-Encoding");
            headers.setContentLength(decompressedBody.length);
            return headers;
        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(decompressedBody);
        }

        @Override
        public void close() {
            originalResponse.close();
        }
    }
}