package home.nkavtur.movie.repository;

import home.nkavtur.movie.Movie;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface MovieRepository extends CrudRepository<Movie, UUID>, QuerydslPredicateExecutor<Movie> {

}
