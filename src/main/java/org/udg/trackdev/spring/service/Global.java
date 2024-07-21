package org.udg.trackdev.spring.service;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

/**
 * The type Global.
 */
@Service
public class Global {
    /**
     * The constant SIMPLE_DATE_FORMAT.
     */
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * The constant SIMPLE_LOCALDATE_FORMAT.
     */
    public static final String SIMPLE_LOCALDATE_FORMAT = "yyyy-MM-dd";

    /**
     * The constant dateFormat.
     */
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);

    private MinioClient minioClient;

    private final Logger logger = LoggerFactory.getLogger(Global.class);

    /**
     * The Demo data seeder.
     */
    @Autowired
    DemoDataSeeder demoDataSeeder;

    @Value("${todospring.minio.url:}")
    private String minioURL;

    @Value("${todospring.minio.access-key:}")
    private String minioAccessKey;

    @Value("${todospring.minio.secret-key:}")
    private String minioSecretKey;

    @Value("${todospring.minio.bucket:}")
    private String minioBucket;

    @Value("${todospring.base-url:#{null}}")
    private String BASE_URL;

    @Value("${todospring.base-port:8080}")
    private String BASE_PORT;

    private BCryptPasswordEncoder encoder;

    private SCryptPasswordEncoder encoderScrypt;

    /**
     * Init.
     */
    @PostConstruct
    void init() {

        logger.info(String.format("Starting Minio connection to URL: %s", minioURL));
        try {
            minioClient = MinioClient.builder()
                                     .endpoint(minioURL)
                                     .credentials(minioAccessKey, minioSecretKey)
                                     .build();
        } catch (Exception e) {
            logger.warn("Cannot initialize minio service with url:" + minioURL + ", access-key:" + minioAccessKey + ", secret-key:" + minioSecretKey);
        }

        if (minioBucket.equals("")) {
            logger.warn("Cannot initialize minio bucket: " + minioBucket);
            minioClient = null;
        }

        if (BASE_URL == null) BASE_URL = "http://localhost";
        BASE_URL += ":" + BASE_PORT;

        encoder = new BCryptPasswordEncoder();

        encoderScrypt = new SCryptPasswordEncoder();

        demoDataSeeder.seedDemoData();
    }

    /**
     * Gets minio client.
     *
     * @return the minio client
     */
    public MinioClient getMinioClient() {
        return minioClient;
    }

    /**
     * Gets minio bucket.
     *
     * @return the minio bucket
     */
    public String getMinioBucket() {
        return minioBucket;
    }

    /**
     * Gets base url.
     *
     * @return the base url
     */
    public String getBaseURL() {
        return BASE_URL;
    }

    /**
     * Gets password encoder.
     *
     * @return the password encoder
     */
    public PasswordEncoder getPasswordEncoder() { return encoderScrypt; }
}
