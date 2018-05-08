package home.nkavtur.movie.repository;

import home.nkavtur.movie.Movie;
import home.nkavtur.movie.QMovie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;


    @Test
    void getById() {
        // Given:
        var expected = movieRepository.save(new Movie().setName("Forrest Gump")).setYear(1994);

        // When:
        var actual = movieRepository.findById(expected.getGuid()).orElse(null);

        // Then:
        assertAll("Movie assertion",
                () -> assertEquals(expected.getGuid(), actual.getGuid()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getYear(), actual.getYear())
        );
    }

    @Test
    void findAllByPredicate() {
        // Given:
        var madMax1 = movieRepository.save(new Movie().setGuid("81fd82f9-cf94-4d12-8d92-1dae33ef2abe").setName("Mad Max").setYear(1979));
        var madMax2 = movieRepository.save(new Movie().setGuid("d42d318d-7702-40f4-a45b-644438795e0b").setName("Mad Max 2").setYear(1981));
        var madMax3 = movieRepository.save(new Movie().setGuid("4d0c2459-3ae9-4b87-aaf5-cc4504f41820").setName("Mad Max Beyond Thunderdome").setYear(1985));

        // When:
        var moviesList = movieRepository.findAll(new WhereClauseBuilder()
                .optionalAnd("%Mad Max%", QMovie.movie.name::like)
                .optionalAnd(1986, QMovie.movie.year::lt)
        );

        // Then:
        assertThat(moviesList, hasItem(madMax1));
        assertThat(moviesList, hasItem(madMax2));
        assertThat(moviesList, hasItem(madMax3));
    }


}
