package com.storyreadingbe.common;

public class URLConst {
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String CREATE_USER_DETAIL = "/create-user";
    public class User {
        public static final String USER = "/user";
        public static final String ALL_BOOK = USER + "/all-book";
        public static final String GET_BOOK = USER + "/book";
        public static final String FIND_BOOK = USER + "/find-book";
        public static final String GET_CHAPTER = GET_BOOK + "/chapter";
        public static final String CHAPTER_DETAIL = GET_BOOK + "/detail-chapter";
        public static final String CHAPTER_DETAIL_COUNT = GET_BOOK + "/detail-chapter-count";
        public static final String NEXT_CHAPTER = GET_CHAPTER + "/next";
        public static final String LAST_CHAPTER = GET_CHAPTER + "/last";
        public static final String BOOKSHELF = USER + "/bookshelf";
        public static final String ADD_BOOKSHELF = BOOKSHELF + "/add";
        public static final String GET_DETAIL_BOOK = USER + "/detail-book" ;
        public static final String GET_BOOK_LAST_UPDATE = GET_BOOK + "/last-update";
        public static final String GET_BOOK_LAST_UPDATE_COMPLETE = GET_BOOK + "/last-update-complete";
        public static final String GET_BOOK_LAST_UPDATE_NOT_COMPLETE = GET_BOOK + "/last-update-not-complete";
        public static final String DETAIL_CHAPTER =  "/user/chapter/detail";
        public static final String ALL_REVIEW = GET_BOOK + "/review";
    }

    public class Author {
        public static final String AUTHOR = "/author";
        public static final String CREATE_BOOK = AUTHOR + "/create-book";
        public static final String CREATE_CHAPTER = AUTHOR + "/book/create-chapter";
        public static final String GET_HISTORY_BOOK = "/bookshelf-history";
        public static final String GET_FAVORITE_BOOK = "/bookshelf-favorite";
        public static final String GET_BOOKSHELF_DETAIL = "/bookshelf/detail";
        public static final String REMOVE_BOOKSHELF = "/bookshelf/remove";
        public static final String UPDATE_STATUS_READ =  "/bookshelf/update-status";
        public static final String DETAIL_CHAPTER_USER =  "/chapter/detail";
        public static final String CREATE_REVIEW_BOOK = "/book/create-review";
    }
}
