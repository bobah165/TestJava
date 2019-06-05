package com.answer.Pyramid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class PyramidBuilder {

    public int[][] buildPyramid(List<Integer> inputNumbers) {


        int numberOfRows=0;
        int numberOfColumn = 0;
        int count=inputNumbers.size();
        int elementInList = 0;
        int[][] array;

        for(Integer to:inputNumbers){
            if(to==null)
                throw new CannotBuildPyramidException();
        }

        if (inputNumbers.isEmpty()) throw new CannotBuildPyramidException();



        while ((count=count-numberOfRows)!=0) {
            numberOfRows++;
        }

        if (numberOfColumn<0||numberOfRows<0) throw new CannotBuildPyramidException();

        numberOfColumn = 2*numberOfRows-1;

        //System.out.println("Rows is "+numberOfRows);
        //System.out.println("Column is "+numberOfColumn);



        try {
            array = new int[numberOfRows][numberOfColumn];
        } catch (OutOfMemoryError error) {
            throw new CannotBuildPyramidException();
        }

        Collections.sort(inputNumbers);

        for (int i=0;i<numberOfRows;i++){
            if (i==0) {
                array[i][numberOfRows-(i+1)] = inputNumbers.get(elementInList);
                elementInList++;
                continue;
            }
            else {
                array[i][numberOfRows-(i+1)] = inputNumbers.get(elementInList);
                elementInList++;
            }

            if(i!=numberOfRows-1) {
                for (int j = numberOfRows; j <= numberOfColumn - (numberOfRows - (i + 1)); j++) {
                    if (array[i][j - i] == 0) {
                        array[i][j - (i - 1)] = inputNumbers.get(elementInList);
                        elementInList++;
                    }
                }
                if (i > 2) {
                    array[i][numberOfColumn - (numberOfRows - i)] = inputNumbers.get(elementInList);
                    elementInList++;
                }
            }
            else {
                elementInList--;
                for(int j=0;j<numberOfColumn;j++){
                    if((j+1)%2!=0&&elementInList<inputNumbers.size()) {
                        array[i][j]=inputNumbers.get(elementInList);
                        elementInList++;
                    }
                }
            }
        }

       // System.out.println(Arrays.deepToString(array));

        return array;
    }
}

