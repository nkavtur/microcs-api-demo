package home.nkavtur.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

public class WhereClauseBuilder implements Predicate, Cloneable {
    private BooleanBuilder delegate;

    public WhereClauseBuilder() {
        this.delegate = new BooleanBuilder();
    }

    public WhereClauseBuilder(Predicate pPredicate) {
        this.delegate = new BooleanBuilder(pPredicate);
    }

    public WhereClauseBuilder and(Predicate right) {
        return new WhereClauseBuilder(delegate.and(right));
    }

    public <V> WhereClauseBuilder optionalAnd(@Nullable V value, Function<V, BooleanExpression> expressionFunction) {
        if (value != null) {
            return and(expressionFunction.apply(value));
        }

        return this;
    }

    @Override
    public Predicate not() {
        return delegate.not();
    }

    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return delegate.accept(v, context);
    }

    @Override
    public Class<? extends Boolean> getType() {
        return delegate.getType();
    }

    @FunctionalInterface
    public interface LazyBooleanExpression {
        BooleanExpression get();
    }

}

