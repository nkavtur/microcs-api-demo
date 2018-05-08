package home.nkavtur.movie;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMovie is a Querydsl query type for Movie
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMovie extends EntityPathBase<Movie> {

    private static final long serialVersionUID = 2084338164L;

    public static final QMovie movie = new QMovie("movie");

    public final ComparablePath<java.util.UUID> guid = createComparable("guid", java.util.UUID.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QMovie(String variable) {
        super(Movie.class, forVariable(variable));
    }

    public QMovie(Path<? extends Movie> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMovie(PathMetadata metadata) {
        super(Movie.class, metadata);
    }

}

