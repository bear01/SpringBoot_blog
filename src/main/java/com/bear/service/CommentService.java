package com.bear.service;

import com.bear.po.Comment;

import java.util.List;

/**
 * Created by bear on 2020/3/22.
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
