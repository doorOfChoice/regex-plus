package regexp;


import regexp.born.Matcher;

/**
 * Regexp类负责正则匹配和解析
 * 目前只支持:
 * ^ 匹配开头
 * $ 匹配结尾
 * . 匹配所有字符
 * * 匹配0~n个字符
 * + 匹配1~n个字符
 * ? 匹配0~1个字符
 * \s 匹配空白字符串  \S 非空白
 * \d 匹配数字 \D 非数字
 * \w 匹配数字字母_ \W 不匹配数字字母_
 */
public class Regex {
    public static boolean match(String regex, String input) {
        Matcher matcher = new Matcher(regex);
        return matcher.match(input);
    }
}
