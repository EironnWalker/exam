package com.wuxl.interview.exam.service.impl;

import com.wuxl.interview.exam.constant.Result;
import com.wuxl.interview.exam.service.ICalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class CalServiceImpl implements ICalService {

    @Qualifier("taskExecutor")
    @Autowired
    private Executor taskExecutor;

    private static final Logger log = LoggerFactory.getLogger(CalServiceImpl.class);

    private static final int init = 1;

    @Override
    public String calFibSeq() {
        CopyOnWriteArrayList<Integer> nums = new CopyOnWriteArrayList<>();
        List<String> list = Arrays.asList(new String[20]);
        list.stream().map(a -> CompletableFuture.supplyAsync(() -> {
            printFibSeq(nums);
            return a;
        }, taskExecutor)).collect(Collectors.toList()).stream().map(CompletableFuture::join).collect(Collectors.toList());
        for (Integer num : nums) {
            log.info("result:{}", num);
        }
        return null;
    }

    @Override
    public String permutationLetter() {
        String[] arrays = {"A", "B", "C", "D", "E"};
        // 从选区一个值开始算
        for (int i = 1; i <= 5; i++) {
            log.info("取{}个=====开始", i);
            List<List<String>> lists = generate(Arrays.asList(arrays), i);
            for (List<String> list : lists) {
                log.info(list.toString());
            }
            log.info("取{}个====结束",i);
        }
        return Result.SUCCESS;
    }

    private List<List<String>> generate(List<String> letters, int r) {
        List<List<String>> resp = new ArrayList<>();
        List<int[]> generate = this.generate2(letters.size(), r);
        for (int[] ints : generate) {
            List<String> data = new ArrayList<>();
            for (int anInt : ints) {
                data.add(letters.get(anInt));
            }
            resp.add(data);
        }
        return resp;
    }

    private List<int[]> generate2(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }

    CopyOnWriteArrayList<Integer> printFibSeq(CopyOnWriteArrayList<Integer> nums) {
        try {
            int currentNum;
            if (nums.size() < 2) {
                currentNum = init;
                nums.add(init);
            } else {
                int count = 0;
                for (int j = nums.size() - 2; j < nums.size(); j++) {
                    count += nums.get(j);
                }
                currentNum = count;
                nums.add(count);
            }
            log.info("Thread Id:{};index:{};num:{}", Thread.currentThread().getId(), nums.size(), currentNum);
            log.info("Thread Name:{}", Thread.currentThread().getName());
            return nums;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nums;
    }
}
