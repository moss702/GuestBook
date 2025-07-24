package com.ikkikki.guestbook.dto.sample;

import com.ikkikki.guestbook.entity.Guestbook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestbookWriteDTO {
  private Long gno;
  private String title;
  private String content;
  private String writer;

//  public GuestbookWriteDTO(Guestbook guestbook) { // Entity로 DTO 만들기
//    this.gno = guestbook.getGno();
//    this.title = guestbook.getTitle();
//    this.content = guestbook.getContent();
//    this.writer = guestbook.getWriter();
//  }

  public Guestbook toEntity() { // guestbook이라는 Entity를 리턴하는
    return Guestbook.builder().title(title).content(content).writer(writer).build();
  } // 여기서 유효성 검증을 해야 한다. (*서비스 쪽 코드는 짧아짐.)


}
