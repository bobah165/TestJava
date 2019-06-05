package com.answer.Calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Double.NaN;

public class Calculator {

    private String output;
    private List<Double> arrayOfDigits = new ArrayList<>();
    private List<Character> arrayOfSigns = new ArrayList<>();
    private static boolean flag;
    private static int resFlag;


    public String evaluate(String statement) {

        if(statement==null||statement=="") return null;

        int countOfOpenPerh1 = 0;
        int countOfClosePerh1 = 0;
        int positionOfFirstOpenPerh = 0;
        int positionOfFirstClosePerh = 0;

        Calculator calculatorGeneral = new Calculator();
        char[] arr = statement.toCharArray();

        for (int i = 0;i<arr.length;i++) {
            if (arr[i]=='(') {
                countOfOpenPerh1++;
                positionOfFirstOpenPerh = i;
            }
            if (arr[i]==')'){
                countOfClosePerh1++;
                positionOfFirstClosePerh=i;}

            if(positionOfFirstClosePerh>0&&(positionOfFirstClosePerh<positionOfFirstOpenPerh)) return null;
        }

        StringBuilder myNewString = new StringBuilder();
        myNewString.append(statement);

        if (countOfOpenPerh1>0&&countOfClosePerh1 == countOfOpenPerh1) {

            for (int i = 0; i < countOfOpenPerh1; i++) {
                myNewString = calculatorGeneral.resultAfterOneDial(myNewString.toString());
            }
        }
        if (countOfClosePerh1 != countOfOpenPerh1) return null;

        output = Double.toString(calculatorGeneral.resultInPerhaps(myNewString.toString().toCharArray()));
        if (flag) return null;
        if (!Double.isFinite(Double.parseDouble(output))) return null;
        if (resFlag>0) {
            output = "-"+output;
           // resFlag=0;
        }

        char[] last = output.toCharArray();
        int indexOfDot= output.indexOf(".");
        if (last[indexOfDot+1]=='0'&&((indexOfDot+2)==last.length))  output=output.substring(0,indexOfDot);

        return output;
    }


    //Calculate expression if there are many expressions in parentheses
    private StringBuilder resultAfterOneDial (String newMyString) {

        StringBuilder newStatement = new StringBuilder();
        newStatement.append(newMyString);

        int placeInArrayLastOpenPerh = 0;
        int placeInArrayNearClosePerh = 0;

        char[] arr = newMyString.toCharArray();
        for (int i = 0;i<arr.length;i++) {
            if (arr[i]=='(') placeInArrayLastOpenPerh=i;
        }

        for (int i=placeInArrayLastOpenPerh;i<arr.length;i++)
        {
            if (arr[i]==')') {
                double tempRes = 0;
                placeInArrayNearClosePerh = i;
                char[] subArray = newStatement.substring(placeInArrayLastOpenPerh+1, placeInArrayNearClosePerh).toCharArray();
                Calculator calculator = new Calculator();
                tempRes = calculator.resultInPerhaps(subArray);
                if (tempRes<0) {tempRes = -tempRes; resFlag++;}
                newStatement = newStatement.replace(placeInArrayLastOpenPerh,placeInArrayNearClosePerh+1,Double.toString(tempRes));
            }
        }
        return newStatement;
    }



    //Calculate expression. If there are parentheses it calculates expression exactly in one parentheses
    private double resultInPerhaps(char[] arr){
        StringBuilder tempString = new StringBuilder();

        for (int j = 0;j<arr.length;j++) {

            if (Character.isDigit(arr[j])||(arr[j]=='.')) {
                if((j>0)&& (arr[j-1]=='.'&& arr[j]=='.')) flag = true;
                else tempString.append(arr[j]);}
            else {
                if(arr[j]==',') flag = true;
                if (j > 0 && arr[j - 1] == arr[j]) flag = true;
                else {
                    arrayOfDigits.add(Double.parseDouble(tempString.toString()));
                    tempString.setLength(0);

                    arrayOfSigns.add(arr[j]);
                    if (arr[j]=='-') tempString.append("-");
                }

            }

            if (j==(arr.length-1))
                arrayOfDigits.add(Double.parseDouble(tempString.toString()));
        }


        for (int i=0;i<arrayOfSigns.size();i++) {

            if (arrayOfSigns.get(i)=='*') {
                arrayOfDigits.set(i,arrayOfDigits.get(i)*arrayOfDigits.get(i+1));
                arrayOfDigits.remove(i+1);
                arrayOfSigns.remove(i);
                i=0;
            }

            if ((arrayOfSigns.size()>0)&&arrayOfSigns.get(i)=='/') {
                arrayOfDigits.set(i,arrayOfDigits.get(i)/arrayOfDigits.get(i+1));
                arrayOfDigits.remove(i+1);
                arrayOfSigns.remove(i);
                i=-1;
            }

        }

        return sum(arrayOfDigits,arrayOfSigns);
    }


    //Calculate sum
    private double sum(List<Double> doubleList, List<Character> characterList) {
        double result = doubleList.get(0);
        for (int i=0;i<characterList.size();i++) {
            switch (characterList.get(i)) {
                case '+':
                    result = result + doubleList.get(i + 1);
                    break;
                case '-':
                    result = result+doubleList.get(i+1);
            }
        }

        return result;
    }

}