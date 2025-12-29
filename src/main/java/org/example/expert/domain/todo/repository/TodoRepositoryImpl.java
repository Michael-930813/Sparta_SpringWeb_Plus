package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoCustomRepository {
// - Properties
    private final JPAQueryFactory queryFactory;

// - Methods
    @Override
    public Page<Todo> searchTodos(String weather, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        List<Todo> content = queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(
                        weatherEq(weather),
                        modifiedAtGoe(start),
                        modifiedAtLoe(end)
                )
                .orderBy(todo.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(todo.count())
                .from(todo)
                .where(
                        weatherEq(weather),
                        modifiedAtGoe(start),
                        modifiedAtLoe(end)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression weatherEq(String weather) {
        return (weather == null) ? null : todo.weather.eq(weather);
    }
    private BooleanExpression modifiedAtGoe(LocalDateTime start) {
        return (start == null) ? null : todo.modifiedAt.goe(start);
    }
    private BooleanExpression modifiedAtLoe(LocalDateTime end) {
        return (end == null) ? null : todo.modifiedAt.loe(end);
    }
}
