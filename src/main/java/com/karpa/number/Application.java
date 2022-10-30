package com.karpa.number;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static int leftNumber = 0;
    private static int rightNumber = 0;

    @SneakyThrows
    public static void main(String[] args) {

        for (int i = 4; i < 65536; i++) {

            logger.info("Test Number: {}", i);
            int b = removeRedundantChar(i);

            List<Integer> allNumberList = getNumberListToNumber(b);

            if (isCorrectNumber(allNumberList, i)) {
                logger.info(getFormattedMessage(i, b, leftNumber, rightNumber));
                Thread.sleep(5000);
            }
        }
    }

    private static List<Integer> getNumberList(List<Integer> allNumberList, String direction) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        int middle = (allNumberList.size()) / 2;

        for (int j = 0; j < allNumberList.size(); j++) {
            if (j < middle) {
                left.add(allNumberList.get(j));
            }
            if (j >= middle) {
                right.add(allNumberList.get(j));
            }
        }
        if (direction.equalsIgnoreCase("LEFT")) {
            return left;
        } else {
            return right;
        }
    }

    private static boolean isCorrectNumber(List<Integer> allNumberList, int i) {

        List<Integer> leftNumberList = getNumberList(allNumberList, "LEFT");
        List<Integer> rightNumberList = getNumberList(allNumberList, "RIGHT");

        leftNumber = convertToNumberFromNumberList(leftNumberList);
        rightNumber = convertToNumberFromNumberList(rightNumberList);

        return !allNumberList.isEmpty() && (allNumberList.size() % 2 == 0) && (i == (leftNumber + rightNumber));
    }

    private static int removeRedundantChar(int i) {

        int result = i * i;

        List<Integer> numberList = new ArrayList<>(Arrays.asList(String.valueOf(result).split("")))
            .stream()
            .filter(e -> Pattern.compile("-?\\d+(\\.\\d+)?").matcher(e).matches())
            .map(Integer::valueOf)
            .collect(Collectors.toList());

        return convertToNumberFromNumberList(numberList);
    }

    private static List<Integer> getNumberListToNumber(int c) {
        return new ArrayList<>(Arrays.asList(String.valueOf(c).split("")))
            .stream()
            .map(Integer::valueOf)
            .collect(Collectors.toList());
    }

    private static int convertToNumberFromNumberList(List<Integer> numberList) {

        int result = 0;
        int loopCount = 0;
        for (int i = numberList.size(); i > 0; i--) {
            result = result + (numberList.get(i - 1) * ((int) Math.pow(10, loopCount)));
            loopCount++;
        }
        return result;
    }

    private static String getFormattedMessage(int i, int b, int leftNumber, int rightNumber) {
        return String.format("Karpa Sayısı: %s %n(%s)^2 = %s %n%s == (%s--%s)%n%s = %s+%s",
            i,
            i,
            b,
            b,
            leftNumber,
            rightNumber,
            i,
            leftNumber,
            rightNumber
        );
    }
}
