package com.example.crackhash.utils;

import jakarta.validation.constraints.NotNull;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.ArrayList;
import java.util.List;

import static org.paukov.combinatorics.CombinatoricsFactory.createPermutationWithRepetitionGenerator;
import static org.paukov.combinatorics.CombinatoricsFactory.createVector;


public class HashCracker {

    // TODO: должен принимать номер воркера и их число.
    public static List<String> matchHash(@NotNull String alphabet, @NotNull String hash, int length) {
        ICombinatoricsVector<String> vector = createVector(alphabet.split(""));
        Generator<String> gen = createPermutationWithRepetitionGenerator(vector, length);
        List<String> result = new ArrayList<>();

        for (ICombinatoricsVector<String> comb : gen) {
            String mess = String.join("" , comb.getVector());
            if(hash.equals(MD5Encoder.getMD5Hash(mess))) {
                result.add(mess);
            }
        }

        return result;
    }
}
