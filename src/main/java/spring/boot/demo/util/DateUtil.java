package spring.boot.demo.util;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * @author JMP
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2024-01-10 14:05
 */
public class DateUtil {

    public static void main(String[] args) {
        System.out.println("Tips：输入\"-1\"退出程序\n");
        while (true){
            System.out.println("请输入生日：");
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextLine()) {
                String inputBirthday = sc.next();
                if ("-1".equals(inputBirthday)){
                    System.out.println("===程序退出===");
                    System.exit(0);
                }
                int age = ageCalculator(inputBirthday);
                System.out.println("生日：" + inputBirthday + "，年龄：" + age + "\n----------------------\n");
            }
        }
    }

    /**
     * 准确地计算年龄
     * @param birthdayStr yyyy-MM-dd 格式字符串
     * @return 年龄精确值
     */
    public static int ageCalculator (String birthdayStr) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 将字符串转换成日期对象
        LocalDate birthDate = LocalDate.parse(birthdayStr);
        // 根据年份计算年龄
        int age = currentDate.getYear() - birthDate.getYear();

        // 若今天还未到达生日那一天，则年龄应该减1
        if (currentDate.isBefore(birthDate)) {
            return --age;
        }
        // 若已经超过生日月份，则年龄也应该减1
        if ((currentDate.getMonthValue() < birthDate.getMonthValue())) {
            return --age;
        }
        // 生日月份但是没有超过生日日子，则年龄也应该减1
        if (currentDate.getMonthValue() == birthDate.getMonthValue() && currentDate.getDayOfMonth() < birthDate.getDayOfMonth()){
            return --age;
        }
        return age;
    }
}
