package home.nkavtur.movie.rest.mapper;

import home.nkavtur.movie.Movie;
import home.nkavtur.movie.rest.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MovieToDtoMapper {

    public MovieDto toDto(Movie movie) {
        if (movie == null) {
            return null;
        }

        return new MovieDto()
                .setGuid(movie.getGuid())
                .setName(movie.getName())
                .setYear(movie.getYear());
    }

    public Iterable<MovieDto> toDtos(Iterable<Movie> movies) {
        return StreamSupport.stream(movies.spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Movie fromDto(MovieDto movieDto) {
        if (movieDto == null) {
            return null;
        }

        return new Movie()
                .setGuid(movieDto.getGuid())
                .setName(movieDto.getName())
                .setYear(movieDto.getYear());
    }

    public Iterable<Movie> fromDtos(Iterable<MovieDto> movieDtos) {
        return StreamSupport.stream(movieDtos.spliterator(), false)
                .map(this::fromDto)
                .collect(Collectors.toList());
    }
}
