package com.ikkikki.guestbook.service;

import com.ikkikki.guestbook.dto.GuestbookDTO;
import com.ikkikki.guestbook.entity.Guestbook;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class GuestbookServiceTest {
  @Autowired
  private GuestbookService service;
  // 서비스는 인터페이스지만 연결된 Impl을 알아서 찾아간다

  @Test
  public void testExist(){
    log.info(service);
  }

  @Test
  public void testWrite(){
    Long gno = service.write(GuestbookDTO.builder().title("테스트 제목").content("테스트 내용").writer("테스트 작성자").build());
    Assertions.assertNotNull(gno);
    log.info(gno);
  }

  @Test
  public void testRead(){
//    service.read();
  }

  @Test
  public void testReadAll(){
    service.readAll();
  }

  @Test
  public void testModify(){
    service.modify(GuestbookDTO.builder().title("수정").content("수정 내용").writer("수정 작성자").build());
  }

  @Test
  public void testRemove(){
//    service.remove();
  }



}
