package runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements ApplicationRunner {
    @Value("${myprop.username}")
    private String name;

    @Value("${myprop.port}")
    private int age;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        System.out.println("myprop.name = " + name);
        System.out.println("myprop.port = " + age);

        args.getOptionNames()  //Set<String>
                .forEach(name -> System.out.println(name));
    }
}
