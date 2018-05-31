# Regex
自己写一个正则匹配器, 跟用相比，确实没那么简单.又要考虑代码结构问题，又要考虑算法问题，单单是解决贪婪和非贪婪问题就花费了比较长的时间

在参考了这两篇文章后

* https://swtch.com/~rsc/regexp/regexp1.html
* https://www.cs.princeton.edu/courses/archive/spr09/cos333/beautiful.html

我考虑又重新实现了一遍代码

## 目前实现的功能
|符号|功能|
|:---:|:---:|
|^|以开头|
|$|以结尾|
|*|匹配0-∞次|
|+|匹配1-∞次|
|?|匹配0-1次|
|{n,m}|匹配n-m次|
|{n,}|匹配n-∞次|
|{n}|匹配n次|
|[abc]|匹配abc中任意一个字符|
|[^abc]|匹配除了abc以外的字符|
|[a-z]|匹配a-z之间的字符|
|\w|匹配字母、数字，下划线|
|\W|匹配非字母、数字，下划线|
|\s|匹配空白|
|\S|匹配非空白|
|\d|匹配数字|
|\D|匹配非数字|


# 用法

```java
       Pattern p = Pattern.compile("<div class=\"post-title\">(.+?)</div>");
       Matcher m = p.matcher(str);
       //获取所有分组 
       while (m.find()) {
           System.out.println(m.group());
       }
       //匹配
       boolean ok = m.match();
```

# 自定义表达式

自定义表达式分为两步

* 自定义解析器(用于分析正则和比较的字符串)

例如 . 的解析器

```java

public class DotSolver extends AbstractSolver {
    //ms为字符串解析单元,富含丰富的查询操作
    //solve函数用于单纯解析当前正则表达式，不继续进行下一个
    @Override
    public boolean solve(MetaString ms) {
        if (ms.notEnd()) {
            ms.incr();
            return true;
        }
        return false;
    }
    
    //solveAndNext用于解析当前表达式之后, 解析下一个表达式
    @Override
    public boolean solveAndNext(MetaString ms) {
        if (solve(ms)) {
            //next()函数获取下一个节点
            //super.solveAndNext用于解析下一个节点，并且返回结果
            boolean result = super.solveAndNext(next(), ms);
            ms.decr();
            return result;
        }
        return false;
    }
}

```
* 自定义分析器(用于分析正则的字符串，以生成正则解析器)

自定义分析器满足以下的接口

```java
    /**
     * 分析器用来灵活的定义属于自己的正则表达式
     */
    public interface Analyzer  {
        /**
         * 自定义条件
         * @param mp 正则单词解析器
         * @return 是否满足条件
         */
        boolean satisfy(MetaPattern mp);
    
        /**
             * 满足条件后返回的解析器
             * @param mp 正则单词解析器
             * @param r 正则栈, 包含当前链中的已经生成的Solver        
             * @return 自定义解析器
         */
        AbstractSolver gain(MetaPattern mp, CoreSolver r);
    }
    
```

例如 . 的分析器

```java
public class DotAnalyzer implements Analyzer {
    @Override
    public boolean satisfy(MetaPattern mp) {
        //判断mp中的单词还没有结束, 并且mp当前的单词为.
        return mp.notEnd() && mp.cur().equals(".");
    }
    
    @Override
    public AbstractSolver gain(MetaPattern mp, CoreSolver r) {
        AbstractSolver solver = new DotSolver();
        //用户应该根据自己的情况向后偏移
        //incr()为向后偏移一格
        mp.incr();
        return solver;
    }
}
```

* 注册

```java
    Matcher m = new Matcher("hello.*");
    m.register(new DotRegister());
```