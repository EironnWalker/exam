package com.wuxl.interview.exam.service;

import java.util.concurrent.ExecutionException;

public interface ICalService {

    String calFibSeq() throws ExecutionException, InterruptedException;

    String permutationLetter();
}
