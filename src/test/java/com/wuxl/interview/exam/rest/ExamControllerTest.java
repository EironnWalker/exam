package com.wuxl.interview.exam.rest;

import com.alibaba.fastjson.JSON;
import com.wuxl.interview.exam.config.AsyncConfiguration;
import com.wuxl.interview.exam.config.MongoConfig;
import com.wuxl.interview.exam.entity.param.DelBookParam;
import com.wuxl.interview.exam.entity.param.UpdateBookParam;
import com.wuxl.interview.exam.entity.po.Book;
import com.wuxl.interview.exam.security.CustomBasicAuthenticationEntryPoint;
import com.wuxl.interview.exam.service.ICalService;
import com.wuxl.interview.exam.service.impl.CalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({ExamController.class, CustomBasicAuthenticationEntryPoint.class,
        AsyncConfiguration.class, MongoConfig.class, ICalService.class})
class ExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Logger log = LoggerFactory.getLogger(CalServiceImpl.class);

    /**
     * 一次成功执行4条书入库
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", password = "123456", roles = "ADMIN")
    @Test
    void create() throws Exception {
        log.info("用户:{}，执行新增书本：{}", "admin", "Leviathan Wakes");
        Book data1 = new Book();
        data1.setName("Leviathan Wakes");
        data1.setAuthor("James S.A. Corey");
        data1.setReleaseDate("2011-06-02");
        data1.setPageCount(561);
        ResultActions resp1 = mockMvc.perform(post("/info/create")
                .content(JSON.toJSONString(data1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp1.andReturn().getResponse().getContentAsString());
        log.info("用户:{}，执行新增书本：{}", "admin", "Hyperion");
        Book data2 = new Book();
        data2.setName("Hyperion");
        data2.setAuthor("Dan Simmons");
        data2.setReleaseDate("1989-05-26");
        data2.setPageCount(482);
        ResultActions resp2 = mockMvc.perform(post("/info/create")
                .content(JSON.toJSONString(data2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp2.andReturn().getResponse().getContentAsString());
        log.info("用户:{}，执行新增书本：{}", "admin", "Dune");
        Book data3 = new Book();
        data3.setName("Dune");
        data3.setAuthor("Frank Herbert");
        data3.setReleaseDate("1965-06-01");
        data3.setPageCount(604);
        ResultActions resp3 = mockMvc.perform(post("/info/create")
                .content(JSON.toJSONString(data3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp3.andReturn().getResponse().getContentAsString());
        log.info("用户:{}，执行新增书本：{}", "admin", "delete_by");
        Book data4 = new Book();
        data4.setName("delete_by");
        data4.setAuthor("any");
        data4.setReleaseDate("2011-11-11");
        data4.setPageCount(1);
        ResultActions resp4 = mockMvc.perform(post("/info/create")
                .content(JSON.toJSONString(data4))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp4.andReturn().getResponse().getContentAsString());
    }

    /**
     * 执行新增书入库，没有admin角色，返回error600
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", password = "123456")
    @Test
    void createWithAuth() throws Exception {
        log.info("用户:{}，执行无权限新增书本：{}", "admin", "Leviathan Wakes");
        Book data1 = new Book();
        data1.setName("Leviathan Wakes");
        data1.setAuthor("James S.A. Corey");
        data1.setReleaseDate("2011-06-02");
        data1.setPageCount(561);
        ResultActions resp1 = mockMvc.perform(post("/info/create")
                .content(JSON.toJSONString(data1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp1.andReturn().getResponse().getContentAsString());
    }

    /**
     * 根据名字删除最后一次插入的记录。如果多条的话只删除最后一条
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", password = "123456", roles = "ADMIN")
    @Test
    void deleteLastRecord() throws Exception {
        log.info("用户:{}，根据名字删除最后一条记录：{}", "admin", "delete_by");
        DelBookParam data1 = new DelBookParam();
        data1.setName("delete_by");
        ResultActions resp1 = mockMvc.perform(post("/info/delete")
                .content(JSON.toJSONString(data1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp1.andReturn().getResponse().getContentAsString());
    }

    /**
     * 更新书名为新的名字，全部更新
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", password = "123456", roles = "ADMIN")
    @Test
    void update() throws Exception {
        log.info("更新全部书名为old：{}，new：{}", "Hyperion", "Dune");
        UpdateBookParam data1 = new UpdateBookParam();
        data1.setName("Hyperion");
        data1.setUpdate("Dune");
        ResultActions resp1 = mockMvc.perform(post("/info/update")
                .content(JSON.toJSONString(data1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp1.andReturn().getResponse().getContentAsString());
    }

    /**
     * 根据书名字，查询数量
     *
     * @throws Exception
     */
    @Test
    void bookCounts() throws Exception {
        log.info("根据书名，获取全部书的数量：{}", "Dune");
        String name = "Dune";
        ResultActions resp1 = mockMvc.perform(post("/info/group/" + name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp1.andReturn().getResponse().getContentAsString());
    }

    /**
     * 排列组合 ABCDE
     *
     * @throws Exception
     */
    @Test
    void permutation() throws Exception {
        log.info("排列组合ABCDE");
        ResultActions resp1 = mockMvc.perform(post("/info/group/permutation")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp1.andReturn().getResponse().getContentAsString());
    }

    /**
     * 多线程，计算斐波那契数列
     *
     * @throws Exception
     */
    @Test
    void calFibSeq() throws Exception {
        log.info("计算斐波那契数列");
        ResultActions resp1 = mockMvc.perform(post("/info/group/cal")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        log.info("执行结束，返回值：{}", resp1.andReturn().getResponse().getContentAsString());
    }


}