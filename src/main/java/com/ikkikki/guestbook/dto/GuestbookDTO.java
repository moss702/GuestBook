package com.ikkikki.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestbookDTO {
  private Long gno;
  private String title;
  private String content;
  private String writer;
  private LocalDateTime regDate, modDate;
}
