package com.lyk.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOs;
    private boolean showFirstPage;//首页
    private boolean showPrevious;//上一页
    private boolean showNext;//下一页
    private boolean showLastPage;//尾页
    private int page;//当前页数
    private int totalPage;//总页数
    private List<Integer> pages;//显示的页码

}
