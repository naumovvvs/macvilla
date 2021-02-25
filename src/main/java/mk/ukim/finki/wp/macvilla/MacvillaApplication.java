package mk.ukim.finki.wp.macvilla;

import mk.ukim.finki.wp.macvilla.model.*;
import mk.ukim.finki.wp.macvilla.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

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

        CityRepository cityRepository =
                configurableApplicationContext.getBean(CityRepository.class);

        PlaceRepository placeRepository =
                configurableApplicationContext.getBean(PlaceRepository.class);

        CategoryRepository categoryRepository =
                configurableApplicationContext.getBean(CategoryRepository.class);

        ImageRepository imageRepository =
                configurableApplicationContext.getBean(ImageRepository.class);

//        CoordinatesRepository coordinatesRepository =
//                configurableApplicationContext.getBean(CoordinatesRepository.class);

        User user = new User("sn", "pas1", "Strasho", "Naumov", "strase10naumov@outlook.com", "#URL");

        User client = new Client("sn", "pas1", "Strasho", "Naumov", "strase10naumov@outlook.com", "#URL", null,"Vano Gunev");

        User hotelier = new Hotelier("sn", "pas1", "Strasho", "Naumov", "strase10naumov@outlook.com", "#URL");

        User administrator = new Administrator("sn", "pas1", "Strasho", "Naumov", "strase10naumov@outlook.com", "#URL");


        userRepository.save(user);
        clientRepository.save(client);
        hotelierRepository.save(hotelier);
        administratorRepository.save(administrator);
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Category category = new Category("Kategorija 1", "OPIS za cat 1");

        City city1 = new City("Kavadarci", "Grad numero uno in Macedonia");
        City city2 = new City("Gostivar", "Ne sum bil ne mozam numero uno da kazam");

        Image image1 = new Image("img/places/place-1-1.jpg", "Beautiful image");
        Image image2 = new Image("img/places/place-1-2.jpg", "Beautiful image");
        Image image3 = new Image("img/places/place-1-3.jpg", "Beautiful image");
        Image image4 = new Image("img/places/place-1-1.jpg", "Beautiful image");

//        Place place5 = new Place((Hotelier) hotelier, city2, "Place5 Name", "Place5 Description", "Adresa 5",
//                "076/742-222", 1799, category, images,
//                image4, new Coordinates());


        categoryRepository.save(category);
        cityRepository.save(city1);
        cityRepository.save(city2);


//        placeRepository.save(place5);

//        Coordinates coordinates = new Coordinates(1.323F, 31.3F, new Place());

//        coordinatesRepository.save(coordinates);

        imageRepository.save(image1);
        imageRepository.save(image2);
        imageRepository.save(image3);
        imageRepository.save(image4);

        List<Image> images1 = new ArrayList<>();
        images1.add(image1);

        List<Image> images2 = new ArrayList<>();
        images2.add(image2);

        List<Image> images3 = new ArrayList<>();
        images3.add(image3);

        List<Image> images4 = new ArrayList<>();
        images4.add(image4);






        Place place1 = new Place((Hotelier) hotelier, city1, "Place1 Name", "Place1 Description", "Adresa 1",
                "076/742-222", 599, category, images1,
                image1);

        Place place2 = new Place((Hotelier) hotelier, city1, "Place2 Name", "Place2 Description", "Adresa 2",
                "076/742-222", 799, category, images2,
                image2);

        Place place3 = new Place((Hotelier) hotelier, city2, "Place3 Name", "Place3 Description", "Adresa 3",
                "076/742-222", 299, category, images3,
                image3);

        Place place4 = new Place((Hotelier) hotelier, city2, "Place4 Name", "Place4 Description", "Adresa 4",
                "076/742-222", 1799, category, images4,
                image4);

        placeRepository.save(place1);
        placeRepository.save(place2);
        placeRepository.save(place3);
        placeRepository.save(place4);
    }

//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }

}
