package com.storyreadingbe.service;

import com.storyreadingbe.dto.common.BookLastUpdateDTO;
import com.storyreadingbe.dto.response.ChapterDetailResponseDTO;
import com.storyreadingbe.dto.response.ChapterResponseDTO;
import com.storyreadingbe.entity.Bookshelf;
import com.storyreadingbe.entity.Chapter;
import com.storyreadingbe.repository.BookshelfRepository;
import com.storyreadingbe.repository.ChapterRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChapterServiceImpl {
    @Autowired
    ChapterRespository chapterRespository;
    @Autowired
    BookshelfRepository bookshelfRepository;
    @Autowired
    ModelMapper modelMapper;

    public void createChapter(Chapter chapter) {
        try {
            Integer numberical = chapterRespository.getNumbericalByBook(chapter.getIdBook());
            chapter.setNumerical((numberical == null) ? 1 : numberical + 1);
            chapterRespository.save(chapter);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't save the chapter");
        }
    }

    public List<ChapterResponseDTO> getChapterByBookId(Integer id) {
        try {
            List<ChapterResponseDTO> res = new ArrayList<>();
            List<Chapter> chapters = chapterRespository.findAllByIdBook(id); 
            chapters.forEach(chapter -> {
                res.add(modelMapper.map(chapter, ChapterResponseDTO.class));
            });
            return res;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't get the chapter");
        }
    }
    
    public Chapter getChapterById(Integer id) {
        try {
            return chapterRespository.findById(id).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't get the chapter");
        }
    }
    
    public List<BookLastUpdateDTO> getBookLastUpdate(Integer limit) {
        return chapterRespository.getLastBookUpdate().stream().limit(limit).collect(Collectors.toList());
    }

    public Chapter getNextChapter(Integer idBook, Integer idChapter) {
        try {
            return chapterRespository.getNextChapter(idBook, idChapter);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Something went wrong");
        }
    }

    public Chapter getLastChapter(Integer idBook, Integer idChapter) {
        try {
            return chapterRespository.getLastChapter(idBook, idChapter);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Something went wrong");
        }
    }

    public ChapterDetailResponseDTO getChapterDetailById(Integer id) {
        try {
            Chapter chapter = chapterRespository.findById(id).get();
            ChapterDetailResponseDTO dto = modelMapper.map(chapter, ChapterDetailResponseDTO.class);
            dto.setNumChapter(chapterRespository.countAllByIdBook(chapter.getIdBook()));
            return dto;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Something went wrong");
        }
    }

    public ChapterDetailResponseDTO getDetailChapterUser(String username, Integer idBook) {
        Bookshelf bookshelf = bookshelfRepository.findByIdUserAndIdBook(username, idBook);
        Integer id;
        if (bookshelf == null) {
            id = chapterRespository.getMinChapterByBook(idBook);
        } else {
            id = bookshelf.getReading();
        }
        Chapter chapter = chapterRespository.findById(id).get();
        ChapterDetailResponseDTO dto = modelMapper.map(chapter, ChapterDetailResponseDTO.class);
        dto.setNumChapter(chapterRespository.countAllByIdBook(idBook));
        return dto;
    }

    public ChapterDetailResponseDTO getDetailChapter(Integer idBook) {
        Integer id = chapterRespository.getMinChapterByBook(idBook);
        Chapter chapter = chapterRespository.findById(id).get();
        ChapterDetailResponseDTO dto = modelMapper.map(chapter, ChapterDetailResponseDTO.class);
        dto.setNumChapter(chapterRespository.countAllByIdBook(idBook));
        return dto;
    }

    public List<Chapter> getAllChapter(Integer idBook) {
        try {
            List<Chapter> res = chapterRespository.findAllByIdBook(idBook);
            return res;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Something went wrong");
        }
    }
}
