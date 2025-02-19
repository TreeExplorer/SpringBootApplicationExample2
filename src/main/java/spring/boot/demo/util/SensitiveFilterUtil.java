package spring.boot.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 敏感词过滤器：利用DFA算法，进行敏感词过滤
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022/3/20 18:46
 */
public class SensitiveFilterUtil {


    private Map sensitiveWordMap;

    // 最小匹配规则
    public static int minMatchType = 1;

    // 最大匹配规则
    public static int maxMatchType = 2;

    // 单例
    private static SensitiveFilterUtil instance = null;

    /**
     * 构造函数，初始化敏感词库
     */
    private SensitiveFilterUtil() {
        sensitiveWordMap = new SensitiveWordInit().initKeyWord();
    }

    /**
     * 获取单例
     */
    public static SensitiveFilterUtil getInstance() {
        if (null == instance) {
            instance = new SensitiveFilterUtil();
        }
        return instance;
    }

    /**
     * 获取文字中的敏感词
     * @param txt
     * @param matchType
     * @return
     */
    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<String>();
        for (int i = 0; i < txt.length(); i++) {
            // 判断是否包含敏感字符
            int length = CheckSensitiveWord(txt, i, matchType);
            // 存在,加入list中
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));
                // 减1的原因，是因为for会自增
                i = i + length - 1;
            }
        }

        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符
     * @param txt
     * @param matchType
     * @param replaceChar
     * @return
     */
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
        String resultTxt = txt;
        // 获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            // word 敏感词
            // replaceString 将敏感词替换
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        return resultTxt;
    }

    public Boolean hasSensitive(String txt){
        return this.hasSensitive(txt,1);
    }

    public Boolean hasSensitive(String txt, int matchType){
        String resultTxt = txt;
        // 获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            // word 敏感词
            int wordIndex = resultTxt.indexOf(word);
            if (wordIndex >= 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取替换字符串
     * @param replaceChar
     * @param length
     * @return
     */
    private String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }
        return resultReplace;
    }


    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     * 如果存在，则返回敏感词字符的长度，不存在返回0
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return
     */
    public int CheckSensitiveWord(String txt, int beginIndex, int matchType) {

        // 敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        // 匹配标识数默认为0
        int matchFlag = 0;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            char word = txt.charAt(i);
            // 获取指定key
            nowMap = (Map) nowMap.get(word);

            // 存在，则判断是否为最后一个
            if (nowMap != null) {

                // 找到相应key，匹配标识+1
                matchFlag++;
                // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {
                    // 结束标志位为true
                    flag = true;
                    // 最小规则，直接返回,最大规则还需继续查找
                    if (SensitiveFilterUtil.minMatchType == matchType) {
                        break;
                    }
                }
            } else {
                // 不存在，直接返回
                break;
            }
        }
        if (SensitiveFilterUtil.maxMatchType == matchType) {
            //长度必须大于等于1，为词
            if (matchFlag < 2 || !flag) {
                matchFlag = 0;
            }
        }
        if (SensitiveFilterUtil.minMatchType == matchType) {
            //长度必须大于等于1，为词
            if (matchFlag < 2 && !flag) {
                matchFlag = 0;
            }
        }
        return matchFlag;
    }

    /**
     * 屏蔽敏感词初始化
     * @author Tree
     * @version V1.0
     * @Title:
     * @Description:
     * @date 2022/3/20 18:47
     */
    class SensitiveWordInit {

        private final Logger log = LoggerFactory.getLogger(SensitiveWordInit.class);

        // 字符编码
        private String ENCODING = "UTF-8";

        /**
         * 初始化敏感字库
         * @return
         */
        public Map initKeyWord() {

            // 读取敏感词库 ,存入Set中
            Set<String> wordSet = readSensitiveWordFile();
            // 将敏感词库加入到HashMap中//确定有穷自动机DFA
            return addSensitiveWordToHashMap(wordSet);
        }

        /**
         * 读取敏感词库 ,存入HashMap中
         * @return
         */
        private Set<String> readSensitiveWordFile() {
            Set<String> wordSet = null;
            Resource resource = new ClassPathResource("static\\censorwords.txt");
            try {
                log.info("-------resource.getURL()：{}", resource.getURL());
                InputStream is = resource.getInputStream();
                // File resource = new File("static\\censorwords.txt");
                // InputStream is = resource.getInputStream();
                // 读取文件输入流
                InputStreamReader read = new InputStreamReader(is, ENCODING);
                // 文件是否是文件 和 是否存在
                if (resource.exists()) {
                    wordSet = new HashSet<>();
                    // StringBuffer sb = new StringBuffer();
                    // BufferedReader是包装类，先把字符读到缓存里，到缓存满了，再读入内存，提高了读的效率。
                    BufferedReader br = new BufferedReader(read);
                    String txt = null;
                    // 读取文件，将文件内容放入到set中
                    while ((txt = br.readLine()) != null) {
                        wordSet.add(txt);
                    }
                    log.info("-------敏感字：{}", wordSet.toString());
                    br.close();
                }
                // 关闭文件流
                read.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return wordSet;
        }


        /**
         * 将HashSet中的敏感词,存入HashMap中
         * @param wordSet
         * @return
         */
        private Map addSensitiveWordToHashMap(Set<String> wordSet) {

            // 初始化敏感词容器，减少扩容操作
            Map wordMap = new HashMap(wordSet.size());
            for (String word : wordSet) {
                Map nowMap = wordMap;
                for (int i = 0; i < word.length(); i++) {
                    // 转换成char型
                    char keyChar = word.charAt(i);
                    // 获取
                    Object tempMap = nowMap.get(keyChar);
                    // 如果存在该key，直接赋值
                    if (tempMap != null) {
                        nowMap = (Map) tempMap;
                    } else {
                        // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                        // 设置标志位
                        Map<String, String> newMap = new HashMap<String, String>();
                        newMap.put("isEnd", "0");
                        // 添加到集合
                        nowMap.put(keyChar, newMap);
                        nowMap = newMap;
                    }
                    // 最后一个
                    if (i == word.length() - 1) {
                        nowMap.put("isEnd", "1");
                    }
                }
            }
            return wordMap;
        }

    }

}


