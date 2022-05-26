package com.storyreadingbe.controller;

import com.storyreadingbe.common.URLConst;
import com.storyreadingbe.dto.common.BookLastUpdateDTO;
import com.storyreadingbe.dto.response.ChapterDetailResponseDTO;
import com.storyreadingbe.dto.response.ChapterResponseDTO;
import com.storyreadingbe.entity.Chapter;
import com.storyreadingbe.service.ChapterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChapterController {
    @Autowired
    ChapterServiceImpl chapterService;
    
    @GetMapping(value = URLConst.User.GET_CHAPTER + "/{id}")
    public List<ChapterResponseDTO> getChapterByBookId(@PathVariable Integer id) {
        return chapterService.getChapterByBookId(id);
    }

    @GetMapping(value = URLConst.User.CHAPTER_DETAIL + "/{id}")
    public Chapter getChapterById(@PathVariable Integer id) {
        return chapterService.getChapterById(id);
    }

    @GetMapping(value = URLConst.User.CHAPTER_DETAIL_COUNT + "/{id}")
    public ChapterDetailResponseDTO getChapterDetailById(@PathVariable Integer id) {
        return chapterService.getChapterDetailById(id);
    }

    @GetMapping(value = URLConst.User.NEXT_CHAPTER + "/{idBook}/{idChapter}")
    public Chapter getNextChapter(@PathVariable Integer idBook, @PathVariable Integer idChapter) {
        return chapterService.getNextChapter(idBook, idChapter);
    }

    @GetMapping(value = URLConst.User.LAST_CHAPTER + "/{idBook}/{idChapter}")
    public Chapter getLastChapter(@PathVariable Integer idBook, @PathVariable Integer idChapter) {
        return chapterService.getLastChapter(idBook, idChapter);
    }

    @PostMapping(value = URLConst.Author.CREATE_CHAPTER,
            produces = { "application/json" },
            consumes = { "multipart/form-data" })
    public void createChapter(@ModelAttribute Chapter chapter) {
        chapterService.createChapter(chapter);
    }

    @PostMapping(value = URLConst.Author.DETAIL_CHAPTER_USER)
    public ChapterDetailResponseDTO getDetailChapterUser(@RequestParam("username") String username, 
                                     @RequestParam("idBook") Integer idBook) {
        return chapterService.getDetailChapterUser(username, idBook);
    }
    
    @GetMapping(value = URLConst.User.DETAIL_CHAPTER + "/{idBook}")
    public ChapterDetailResponseDTO getDetailChapter(@PathVariable Integer idBook) {
        return chapterService.getDetailChapter(idBook);
    }
    
    @GetMapping(value = URLConst.User.ALL_CHAPTER + "/{idBook}")
    public List<Chapter> getAllChapter(@PathVariable Integer idBook) {
        return chapterService.getAllChapter(idBook);
    }
}
