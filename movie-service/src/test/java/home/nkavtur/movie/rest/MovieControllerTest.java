package home.nkavtur.movie.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.nkavtur.movie.Movie;
import home.nkavtur.movie.repository.MovieRepository;
import home.nkavtur.movie.rest.mapper.MovieToDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"hello="})
class MovieControllerTest {

    @Autowired MovieRepository movieRepository;
    @Autowired MovieToDtoMapper mapper;

    MockMvc mockMvc;

    @BeforeEach
    void setup(@Autowired WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getByGuid() throws Exception {
        // Given:
        var movie = movieRepository.save(new Movie().setName("Test Movie").setYear(2018));

        // Expect:
        mockMvc.perform(get("/movie/{guid}", movie.getGuid().toString())
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guid").value(movie.getGuid().toString()))
                .andExpect(jsonPath("$.name").value("Test Movie"))
                .andExpect(jsonPath("$.year").value("2018"));
    }

    @Test
    void findAll() throws Exception {
        // Given:
        movieRepository.saveAll(List.of(
                new Movie().setName("Mad Max").setYear(1979),
                new Movie().setName("Mad Max 2").setYear(1981),
                new Movie().setName("Mad Max Beyond Thunderdome").setYear(1985)
        ));

        // Expect:
        mockMvc.perform(get("/movie/?name={name}", "Mad%")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].guid").isNotEmpty())
                .andExpect(jsonPath("$[0].name").value("Mad Max"))
                .andExpect(jsonPath("$[0].year").value("1979"))
                .andExpect(jsonPath("$[1].guid").isNotEmpty())
                .andExpect(jsonPath("$[1].name").value("Mad Max 2"))
                .andExpect(jsonPath("$[1].year").value("1981"))
                .andExpect(jsonPath("$[2].guid").isNotEmpty())
                .andExpect(jsonPath("$[2].name").value("Mad Max Beyond Thunderdome"))
                .andExpect(jsonPath("$[2].year").value("1985"));
    }

    @Test
    void removeByGuid() throws Exception {
        // Given:
        var movie = movieRepository.save(new Movie().setName("Existing Movie").setYear(2018));

        // Expect:
        mockMvc.perform(delete("/movie/{guid}", movie.getGuid().toString())
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void save() throws Exception {
        // Given:
        var newMovie = movieRepository.save(new Movie().setName("Some new Movie").setYear(2010));

        // Expect:
        mockMvc.perform(post("/movie/")
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(asJsonString(newMovie))
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "/movie/" + newMovie.getGuid().toString()))
                .andExpect(jsonPath("$.guid").value(newMovie.getGuid().toString()))
                .andExpect(jsonPath("$.name").value("Some new Movie"))
                .andExpect(jsonPath("$.year").value("2010"));
    }

    @Test
    void update() throws Exception {
        // Given:
        var existingMovie = movieRepository.save(new Movie().setName("My Existing Movie").setYear(2013));

        // Expect:
        mockMvc.perform(put("/movie/{guid}", existingMovie.getGuid().toString())
                .contentType(APPLICATION_JSON_VALUE)
                .content(asJsonString(existingMovie.setName("My Updated Movie")))
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guid").value(existingMovie.getGuid().toString()))
                .andExpect(jsonPath("$.name").value("My Updated Movie"))
                .andExpect(jsonPath("$.year").value("2013"));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

