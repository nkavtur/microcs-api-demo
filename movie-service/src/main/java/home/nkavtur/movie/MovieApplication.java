package home.nkavtur.movie;

import home.nkavtur.movie.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MovieApplication {

    private final MovieRepository movieRepository;

    public MovieApplication(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            movieRepository.save(new Movie().setYear(1939).setName("The wizard of oz"));
            movieRepository.save(new Movie().setYear(1941).setName("Citizen kane"));
            movieRepository.save(new Movie().setYear(1949).setName("The third man "));
            movieRepository.save(new Movie().setYear(2017).setName("Get out"));
            movieRepository.save(new Movie().setYear(1950).setName("All about eve"));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MovieApplication.class, args);
    }

}
