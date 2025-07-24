package com.ikkikki.guestbook.service;

import com.ikkikki.guestbook.dto.GuestbookDTO;
import com.ikkikki.guestbook.dto.PageRequestDTO;
import com.ikkikki.guestbook.dto.PageResponseDTO;
import com.ikkikki.guestbook.entity.Guestbook;

import java.util.List;

public interface GuestbookService {
  Long write(GuestbookDTO guestbookDTO);
  GuestbookDTO read(Long gno);
  List<GuestbookDTO> readAll();

  PageResponseDTO<GuestbookDTO, Guestbook> getList (PageRequestDTO pageRequestDTO);


  void modify(GuestbookDTO guestbookDTO);
  void remove(Long gno);

  default Guestbook toEntity(GuestbookDTO guestbookDTO) {
    return Guestbook.builder()
            .gno(guestbookDTO.getGno())
            .title(guestbookDTO.getTitle())
            .content(guestbookDTO.getContent())
            .writer(guestbookDTO.getWriter())
            .build();
  }
  default GuestbookDTO toDto(Guestbook guestbook) {
    return guestbook == null ? null : GuestbookDTO.builder()
            .gno(guestbook.getGno())
            .title(guestbook.getTitle())
            .content(guestbook.getContent())
            .writer(guestbook.getWriter())
            .regDate(guestbook.getRegDate())
            .modDate(guestbook.getModDate())
            .build();
  }

}
