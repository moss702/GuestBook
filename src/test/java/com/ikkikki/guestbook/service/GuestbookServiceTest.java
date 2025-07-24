package com.ikkikki.guestbook.service;

import com.ikkikki.guestbook.dto.GuestbookDTO;
import com.ikkikki.guestbook.dto.PageRequestDTO;
import com.ikkikki.guestbook.dto.PageResponseDTO;
import com.ikkikki.guestbook.entity.Guestbook;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

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
    Long gno = 104L;
    GuestbookDTO dto = service.read(gno);
    GuestbookDTO expect = GuestbookDTO.builder().title("테스트 제목").content("테스트 내용").writer("테스트 작성자").gno(gno).build();

    Assertions.assertEquals(dto.getTitle(),expect.getTitle());
    Assertions.assertEquals(dto.getContent(),expect.getContent());
    Assertions.assertEquals(dto.getWriter(),expect.getWriter());
  }

  @Test
  public void testReadAll(){
    service.readAll().forEach(log::info);
  }

  @Commit
  @Transactional
  @Test
  public void testModify(){
    Long gno = 104L;
    GuestbookDTO dto = service.read(gno);
    dto.setContent("수정내용");
    service.modify(dto);
  }

  @Test
  public void testRemove(){
    service.remove(103L);
  }

  @Test
  public void testGetList() {
//    service.getList(PageRequestDTO.builder().build()).getList().forEach(log::info); // DTO에서 설정된 필드 기본값 사용
//    service.getList(PageRequestDTO.builder().page(2).size(5).build()).getList().forEach(log::info);
    PageResponseDTO<?,?> dto = service.getList(PageRequestDTO.builder().page(8).size(5).build());
    log.info(dto);
  }


}
