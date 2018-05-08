package home.nkavtur.movie.rest;

import home.nkavtur.movie.QMovie;
import home.nkavtur.movie.repository.MovieRepository;
import home.nkavtur.movie.repository.WhereClauseBuilder;
import home.nkavtur.movie.rest.dto.MovieDto;
import home.nkavtur.movie.rest.mapper.MovieToDtoMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.relativeTo;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;


@RestController
@RequestMapping("/movie")
public class MovieController {

    private MovieRepository movieRepository;
    private MovieToDtoMapper mapper;

    public MovieController(MovieRepository movieRepository, MovieToDtoMapper mapper) {
        this.movieRepository = movieRepository;
        this.mapper = mapper;
    }

    @GetMapping(path = "/{guid}", produces = APPLICATION_JSON_VALUE)
    public MovieDto getById(@PathVariable("guid") UUID guid) {
        var movie = movieRepository.findById(guid).orElse(null);
        return mapper.toDto(movie);
    }

    @GetMapping(path = "/", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Searches for movies list matching passed params.")
    public Iterable<MovieDto> findAll(@RequestParam(required = false) UUID guid,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) Integer year) {

        var movies = movieRepository.findAll(new WhereClauseBuilder()
                .optionalAnd(guid, QMovie.movie.guid::eq)
                .optionalAnd(name, QMovie.movie.name::like)
                .optionalAnd(year, QMovie.movie.year::eq));

        return mapper.toDtos(movies);
    }

    @DeleteMapping(path = "/{guid}", produces = APPLICATION_JSON_VALUE)
    public void removeByGuid(@PathVariable("guid") String guid) {
        movieRepository.deleteById(UUID.fromString(guid));
    }

    @PostMapping(path = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDto> create(@RequestBody MovieDto movieDto) {
        var movie = movieRepository.save(mapper.fromDto(movieDto));

        var uri = relativeTo(fromPath("/")).withMethodCall(on(MovieController.class).getById(movie.getGuid())).build().toUri();

        return ResponseEntity.created(uri).body(mapper.toDto(movie));
    }

    @PutMapping(path = "/{guid}", consumes = APPLICATION_JSON_VALUE)
    public MovieDto update(@RequestBody MovieDto movieDto) {
        var movie = movieRepository.save(mapper.fromDto(movieDto));
        return mapper.toDto(movie);
    }
}
