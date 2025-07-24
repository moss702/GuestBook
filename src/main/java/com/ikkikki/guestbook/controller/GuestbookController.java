package com.ikkikki.guestbook.controller;

import com.ikkikki.guestbook.dto.GuestbookDTO;
import com.ikkikki.guestbook.dto.PageRequestDTO;
import com.ikkikki.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("guestbook")
@RequiredArgsConstructor
public class GuestbookController {
  private final GuestbookService service;

  @GetMapping
  public String index() {
    return "redirect:/guestbook/list";
  }

  @GetMapping("list")
  public void list(@ModelAttribute("requestDto") PageRequestDTO dto, Model model) {
    model.addAttribute("dto", service.getList(dto));
  }

  @GetMapping("register")
  public void register(){}

  @PostMapping("register")
  public String register(GuestbookDTO dto, RedirectAttributes rttr) {
    rttr.addFlashAttribute("msg", service.write(dto));
    return "redirect:/guestbook/list";
  }

  @GetMapping("read")
  public void read(@ModelAttribute("pageDto") PageRequestDTO pageDto, Long gno, Model model) {
    GuestbookDTO dto = service.read(gno);
    model.addAttribute("dto", dto);
    model.addAttribute("pageDto", pageDto);
  }

  @GetMapping("modify")
  public void modify(@ModelAttribute("pageDto") PageRequestDTO dto, Model model, Long gno) {
    model.addAttribute("dto", service.read(gno));
  }

  @PostMapping("modify")
  public String modify(PageRequestDTO dto, Long gno, GuestbookDTO guestbookDTO, RedirectAttributes rttr){
    service.modify(guestbookDTO);
    rttr.addAttribute("gno", guestbookDTO.getGno());
    rttr.addAttribute("page", dto.getPage());
    rttr.addAttribute("size", dto.getSize());
    return "redirect:/guestbook/read";
  }

  @PostMapping("remove")
  public String remove(PageRequestDTO dto, Long gno, RedirectAttributes rttr){
    service.remove(gno);
    rttr.addAttribute("page", dto.getPage());
    rttr.addAttribute("size", dto.getSize());
    return "redirect:/guestbook/list";
  }
}
