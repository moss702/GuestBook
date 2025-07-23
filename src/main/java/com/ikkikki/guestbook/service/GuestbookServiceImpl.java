package com.ikkikki.guestbook.service;

import com.ikkikki.guestbook.dto.GuestbookDTO;
import com.ikkikki.guestbook.repository.GuestbookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class GuestbookServiceImpl implements GuestbookService {
  private GuestbookRepository repository;

  public Long write(GuestbookDTO guestbookDTO) {
    return repository.save(toEntity(guestbookDTO)).getGno();
  }

  public GuestbookDTO read(Long gno) {
    return toDto(repository.findById(gno).orElseThrow(()-> new RuntimeException("없는 글입니다")));
  }

  public List<GuestbookDTO> readAll() {
    return repository.findAll().stream().map(this::toDto).toList();
  }

  public int modify(GuestbookDTO guestbookDTO) {
    repository.save(toEntity(guestbookDTO));
    return repository.existsById(guestbookDTO.getGno()) ? 1 : 0;
  }

  public int remove(Long gno) {
    try {
      repository.deleteById(gno);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }
}
