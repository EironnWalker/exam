package com.wuxl.interview.exam.entity.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Book {

    @Id
    private String id; // 主键
    private String name;// 名称
    private String author;// 作者
    private String releaseDate;// 发布日期
    private Integer pageCount;// 页数
    private Long timestamp; // 时间戳 10位


}
