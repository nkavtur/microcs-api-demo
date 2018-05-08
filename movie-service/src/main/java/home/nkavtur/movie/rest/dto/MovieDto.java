package home.nkavtur.movie.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.util.UUID;

@ApiModel
public class MovieDto {

    private UUID guid;
    private String name;
    private Integer year;

    public UUID getGuid() {
        return guid;
    }

    public MovieDto setGuid(UUID guid) {
        this.guid = guid;
        return this;
    }

    public String getName() {
        return name;
    }

    public MovieDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public MovieDto setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String toString() {
        return String.format("{guid: %s, name: %s, year: %s}", guid, name, year);
    }

}
