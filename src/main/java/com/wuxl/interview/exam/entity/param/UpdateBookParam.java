package com.wuxl.interview.exam.entity.param;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateBookParam {

    private String name; // 原名字

    private String update; // 新名字
}
