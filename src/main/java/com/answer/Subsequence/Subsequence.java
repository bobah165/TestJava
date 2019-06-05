package com.answer.Subsequence;

import java.util.List;

public class Subsequence {

    public boolean find(List x, List y) {

        if (x==null || y ==null) throw new IllegalArgumentException();

        int counter = 0;
        int index = 0;


        for (int i = 0;i<x.size();i++) {
            for (int j=0;j<y.size();j++){

                if ((index==0)&&x.get(i) ==y.get(j)) {
                    counter++;
                    index =j;
                    break;
                }

                if (x.get(i)==y.get(j)) {
                    if (index>j) return false;
                    else {
                        index = j;
                        counter++;
                        break;
                    }
                }
            }
        }

        if (counter==x.size()) return true;

        return false;
    }
}
