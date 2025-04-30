package runner;

import config.MyEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import property.MyPropProperties;

@Component
public class MyPropRunner implements ApplicationRunner {
    @Value("${myprop.username}")
    private String name;

    @Value("${myprop.port}")
    private int port;

    @Autowired
    private Environment environment;

    @Autowired
    private MyEnvironment myEnvironment;

    @Autowired
    private MyPropProperties properties;

    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception{
        logger.debug("myprop.name = {}", name);
        logger.debug("myprop.port = {}", port);
        logger.debug("${myprop.fullName} = {}", environment.getProperty("myprop.fullName"));


        logger.info("MyPropProperties getName() = {}", properties.getName());
        logger.info("MyPropProperties getPort() = {}",  properties.getPort());
        logger.info("설정된 Port 번호 = {}", environment.getProperty("local.server.port"));

        logger.info("현재 활성화된 Bean = {}", myEnvironment);

        args.getOptionNames()  //Set<String>
                .forEach(name -> System.out.println(name));
    }
}
