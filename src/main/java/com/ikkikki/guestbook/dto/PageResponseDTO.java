package com.ikkikki.guestbook.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.*;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<DTO, Entity> {

  private List<DTO> list;

  private int totalPage, page, size, start, end;
  private boolean prev, next;
  private List<Integer> pageList;

  public PageResponseDTO(Page<Entity> page, Function<Entity, DTO> mapper) {
    list = page.stream().map(mapper).toList(); // 스트림에 tolist하면? 리스트가 된다

    totalPage = page.getTotalPages();
    makePageList(page.getPageable());
  }

  private void makePageList(Pageable pageable) {
    final int PAGE_VIEW_COUNT = 10; // 한번에 볼 개수

    page = pageable.getPageNumber() + 1;
    size = pageable.getPageSize();

    int tempEnd = (int)(Math.ceil(page / 1d / PAGE_VIEW_COUNT)) * PAGE_VIEW_COUNT;
    start = tempEnd - (PAGE_VIEW_COUNT -1);
    prev = start > 1;
    end = totalPage > tempEnd ? tempEnd : totalPage;
    next = totalPage > tempEnd;

    pageList = IntStream.rangeClosed(start, end).boxed().toList();
    // toList는 참조형을 원함. intStream은 int타입 > boxed로 Integer로 만들어야 toList가 받아줌
  }



}
