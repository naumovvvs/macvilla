package mk.ukim.finki.wp.macvilla;

import mk.ukim.finki.wp.macvilla.model.Administrator;
import mk.ukim.finki.wp.macvilla.model.Client;
import mk.ukim.finki.wp.macvilla.model.Hotelier;
import mk.ukim.finki.wp.macvilla.model.User;
import mk.ukim.finki.wp.macvilla.repository.AdministratorRepository;
import mk.ukim.finki.wp.macvilla.repository.ClientRepository;
import mk.ukim.finki.wp.macvilla.repository.HotelierRepository;
import mk.ukim.finki.wp.macvilla.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MacvillaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
            SpringApplication.run(MacvillaApplication.class, args);

        UserRepository userRepository =
                configurableApplicationContext.getBean(UserRepository.class);

        UserRepository clientRepository =
                configurableApplicationContext.getBean(ClientRepository.class);

        UserRepository hotelierRepository =
                configurableApplicationContext.getBean(HotelierRepository.class);

        UserRepository administratorRepository =
                configurableApplicationContext.getBean(AdministratorRepository.class);

        User user = new User("sn", "pas1", "Strasho", "Naumov", "strase10naumov@outlook.com", "#URL");

        User client = new Client("sn", "pas1", "Strasho", "Naumov", "strase10naumov@outlook.com", "#URL", null,"Vano Gunev");

        User hotelier = new Hotelier("sn", "pas1", "Strasho", "Naumov", "strase10naumov@outlook.com", "#URL");

        User administrator = new Administrator("sn", "pas1", "Strasho", "Naumov", "strase10naumov@outlook.com", "#URL");


        userRepository.save(user);
        clientRepository.save(client);
        hotelierRepository.save(hotelier);
        administratorRepository.save(administrator);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
