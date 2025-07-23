package com.ikkikki.guestbook.repository;

import com.ikkikki.guestbook.entity.Guestbook;
import com.ikkikki.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.LongStream;

@Log4j2
@SpringBootTest
public class GuestbookRepositoryTest {
  @Autowired
  private GuestbookRepository repository;

  @Test
  public void testExist() {
    log.info(repository);
  }

  @Test
  public void testInsert() {
    // repository.save(Guestbook.builder().title("제목").content("내용").writer("작성자").build());
    LongStream.rangeClosed(1,100).forEach(i -> repository.save(Guestbook.builder().title("제목" + i % 10).content("내용" + (i + 5) % 10).writer("작성자").build()));
  }

  @Test
  public void testQuerydsl() {
    Pageable pageable = PageRequest.of(0, 10);
    QGuestbook guestbook = QGuestbook.guestbook;
    String keyword = "1";
    BooleanBuilder builder = new BooleanBuilder();
    BooleanExpression expression = guestbook.title.contains(keyword);
    builder.and(expression);
    Page<Guestbook> guestbooks = repository.findAll(builder, pageable);
    guestbooks.forEach(log::info);
  }

  @Test
  @DisplayName("복합 조건 테스트")
  public void testQuerydsl2() {
    Pageable pageable = PageRequest.of(0, 10);
    QGuestbook guestbook = QGuestbook.guestbook;
    String keyword = "1";
    BooleanBuilder builder = new BooleanBuilder();
    BooleanExpression expression1 = guestbook.title.contains(keyword);
    BooleanExpression expression2 = guestbook.content.contains(keyword);
    BooleanExpression be = expression1.or(expression2);
    builder.and(be);
    builder.and(guestbook.gno.gt(0));

    Page<Guestbook> guestbooks = repository.findAll(builder, pageable);


  }


  @Test
  public void testFindById() {
    Guestbook guestbook = repository.findById(1L).orElseThrow(()-> new RuntimeException("없는 글입니다"));
    log.info("GuestBook findById = {}", guestbook);
  }

  @Test
  public void testUpdate() {
    Guestbook guestbook = repository.findById(1L).orElseThrow(()-> new RuntimeException("없는 글입니다"));
    guestbook.setContent("내용수정");
    repository.save(guestbook);
  }

  @Test
  public void testDelete() {
    repository.deleteById(2L);
  }

}
