package com.ikkikki.guestbook.service;

import com.ikkikki.guestbook.dto.GuestbookDTO;
import com.ikkikki.guestbook.dto.PageRequestDTO;
import com.ikkikki.guestbook.dto.PageResponseDTO;
import com.ikkikki.guestbook.entity.Guestbook;
import com.ikkikki.guestbook.entity.QGuestbook;
import com.ikkikki.guestbook.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class GuestbookServiceImpl implements GuestbookService {
  // DDD(domain driven development) 도메인 주도 개발

  private GuestbookRepository repository;

  public Long write(GuestbookDTO guestbookDTO) {
    return repository.save(toEntity(guestbookDTO)).getGno();
  }

  public GuestbookDTO read(Long gno) {
    return toDto(repository.findById(gno).orElseThrow(()-> new RuntimeException("없는 글입니다")));
  }

  @Transactional(readOnly = true)
  public List<GuestbookDTO> readAll() {
    return repository.findAll(Sort.by(Sort.Direction.DESC, "gno")).stream().map(this::toDto).toList();
  }

  @Override
  public PageResponseDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {
    return new PageResponseDTO<>(repository.findAll(getSearch(pageRequestDTO), pageRequestDTO.getPageable(Sort.by(Sort.Direction.DESC,"gno"))), this::toDto);
  }

  public void modify(GuestbookDTO guestbookDTO) {
    repository.save(toEntity(guestbookDTO));
  }

  public void remove(Long gno) {
      repository.deleteById(gno);
  }

  private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
    String type = requestDTO.getType();

    BooleanBuilder builder = new BooleanBuilder();

    QGuestbook qGuestbook = QGuestbook.guestbook;
    String keyword = requestDTO.getKeyword();
    builder.and(qGuestbook.gno.gt(0));

    if(type == null || type.trim().length() == 0){
      return builder;
    }

    BooleanBuilder conditionBuilder = new BooleanBuilder();

    if(type.contains("t")){
      conditionBuilder.or(qGuestbook.title.contains(keyword));
    }
    if(type.contains("c")){
      conditionBuilder.or(qGuestbook.content.contains(keyword));
    }
    if(type.contains("w")){
      conditionBuilder.or(qGuestbook.writer.contains(keyword));
    }
    builder.and(conditionBuilder);

    return builder;
  }



}
