package home.nkavtur.movie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "movie_guid")
    private UUID guid;

    @Column
    private String name;

    @Column
    private Integer year;

    public Movie() {}

    public UUID getGuid() {
        return guid;
    }

    public Movie setGuid(UUID guid) {
        this.guid = guid;
        return this;
    }

    public Movie setGuid(String guid) {
        this.guid = UUID.fromString(guid);
        return this;
    }

    public String getName() {
        return name;
    }

    public Movie setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Movie setYear(Integer year) {
        this.year = year;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(name, movie.name) &&
                Objects.equals(year, movie.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year);
    }

    public String toString() {
        return String.format("{guid: %s, name: %s, year: %s}", guid, name, year);
    }
}
