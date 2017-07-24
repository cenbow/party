package com.party.tools;

import com.party.core.model.user.User;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public class LambdaTest {

    public void test(){
        final Comparator<String> stringComparator = (String first, String second) -> Integer.compare(first.length(), second.length());
    }
}
