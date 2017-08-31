package com.garyxuan.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 很容易地创建一个正则表达式 而不用再去翻阅繁杂的语法
 * @version 1.0.0
 * @author garyxuan
 */
public class RegexUtil {
	
	/**
	 * 记录已拼接的表达式
	 */
	private  StringBuffer sBuffer =  new StringBuffer();
	
	/**
	 * 正则表达式的特殊字符，需要进行转义处理
	 *	java \\代表一个反斜杠 其后的字符具有特殊意义
	 * \\\\ 表示一个普通的反斜杠 不影响其后的字符
	 * 这些字符前都要加上 \\才能正确表达一个正则串
	 */
	private static final String specialChar = ".+*\\$^?{}()[]\\|";
	
	/**
	 * 匹配汉字
	 * 汉字的unicode值范围是\u4e00-\u9fa5
	 */
	public static RegexUtil chinese = new  RegexUtil("[\u4e00-\u9fa5]");
	
	/**
	 * 匹配行首
	 * ^ 定义了以什么开始
	 */
	public static RegexUtil lineHead  = new RegexUtil("^");
	
	/**
	 * 匹配行尾部
	 * $匹配输入字符串结尾的位置
	 */
	public static RegexUtil lineTail  = new RegexUtil("$");
	
	/**
	 * .匹配除换行外的所有字符
	 */
	public static RegexUtil anyButNewLine = new RegexUtil(".");
	
	/**
	 * [0-9]匹配数字
	 */
	public static RegexUtil num = new RegexUtil("[0-9]");
	
	/**
     * [A-Z]匹配大写字母
     */
    public static RegexUtil upperLetter = new RegexUtil("[A-Z]");
    
    /**
     * [a-z]匹配小写字母
     */
    public static RegexUtil lowLetter = new RegexUtil("[a-z]");
    
    /**
     * [a-zA-Z]匹配大小写字母
     */
    public static RegexUtil letter = new RegexUtil("[a-zA-Z]");
    
    /**
     *[a-z0-9] 匹配小写字母和数字
     */
    public static RegexUtil lowLetterAndNum = new RegexUtil("[a-z0-9]");
    
    /**
     * [A-Z0-9]匹配大写字母和数字
     */
    public static RegexUtil upperLetterAndNum = new RegexUtil("[A-Z0-9]");
	
    /**
     * [a-zA-Z0-9]匹配大小写字母和数字
     */
    public static RegexUtil letterAndNum = new RegexUtil("[a-zA-Z0-9]");
    
    /**
     * [a-zA-Z0-9_]匹配大小写字母、数字、下划线
     */
    public static RegexUtil letterAndNumAndUnderLine = new RegexUtil("[a-zA-Z0-9_]");
    
    /**
     * \\b匹配一个单词的边界
     */
    public static RegexUtil boundary = new RegexUtil("\\b");
    
    /**
     * \\B匹配一个非单词的边界
     */
    public static RegexUtil notBoundary = new RegexUtil("\\B");
    
    /**
     * \\s匹配任何空白字符，包括空格、制表符、换页符等。与 [ \f\n\r\t\v] 等效。
     */
    public static RegexUtil blank = new RegexUtil("\\s");
    
    /**
     *  \\S匹配任何非空白字符。与 [^ s\f\n\r\t\v] 等效。
     */
    public static RegexUtil notBlank = new RegexUtil("\\S");
    
    /**
     *     匹配任何字类字符，包括下划线。与"[A-Za-z0-9_]"等效。
     */
    public static RegexUtil anyChar = new RegexUtil("\\w");
    
    /**
     * 与任何非单词字符匹配。与"[^A-Za-z0-9_]"等效。
     */
    public static RegexUtil notAnyChar=new RegexUtil("\\W");
    
    /**
     * 最短匹配
     */
    public RegexUtil minMatch() {
		//判断最外面是不是中括号 不是则在两边加上小括号
    		sBuffer = addMinBracketIfNoMidBracket(sBuffer);
    		//?表示零次或一次匹配前面的字符或子表达式
    		sBuffer.append("?");
    		return this;
	}
    
    /**
     * 重复0-N次，等效于{0,}
     */
    	public RegexUtil repeatZeroOrMore() {
    		//判断最外面是不是中括号 不是则在两边加上小括号
    		sBuffer = addMinBracketIfNoMidBracket(sBuffer);
    		// 零次或多次匹配前面的字符或子表达式
    		sBuffer.append("*");
    		return this;
	}
    	
    /**
     * 重复0-1次，等效于{0,1}或？
     */
    	public RegexUtil repeatZeroOrOne(){
    		return minMatch();
    	}
    	
    	/**
    	 * 重复1-N次，等效于{1,}
    	 */
    	public RegexUtil repeatOneOrMore	() {
    		//判断最外面是不是中括号 不是则在两边加上小括号
    		sBuffer = addMinBracketIfNoMidBracket(sBuffer);
    		sBuffer.append("+");
    		return this;
    	}
    	
  /** 重复num次
    * @param num 次数
    */
    public RegexUtil repeat(int num){
    	//判断最外面是不是中括号 不是则在两边加上小括号
    		sBuffer=addMinBracketIfNoMidBracket(sBuffer);
    		sBuffer.append("{"+num+"}");
    		return this;
    }
    	
    /**
     * 重复min-max次
     * @param min 最小
     * @param max 最大
     */
    public RegexUtil repeat(int min,int max){
	    	//判断最外面是不是中括号 不是则在两边加上小括号
	    	sBuffer=addMinBracketIfNoMidBracket(sBuffer);
	    	sBuffer.append("{"+min+","+max+"}");
	    	return this;
    }
    
    /**
     * 至少重复num次
     * @param num 次数
     */
    public RegexUtil repeatMin(int num){
    		//判断最外面是不是中括号 不是则在两边加上小括号
    		sBuffer=addMinBracketIfNoMidBracket(sBuffer);
    		sBuffer.append("{"+num+",}");
    		return this;
    }
    
    
    /**
     * 若字符串两边不是中括号增加上小括号
     * @param sb 原来的串
     * @return  StringBuffer
     */
    private StringBuffer addMinBracketIfNoMidBracket(StringBuffer sb) {
        if(!checkMidBracket(sb)){
            return addMinBrackets(sb);
        }else{
            return sb;
        }
    }
    
    /**
     * 判断字符串最外围是否为中括号
     * @param sb
     * @return boolean 是 true，否则 false。
     */
    private boolean checkMidBracket(StringBuffer sb){
        if("[".equals(sb.substring(0, 1)) && "]".equals(sb.substring(sb.length()-1))){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 字符串两边加上()
     * @param str 字符串
     * @return StringBuffer
     */
    private StringBuffer addMinBrackets(StringBuffer sb){
        return new StringBuffer("("+sb+")");
    }
    
    
    /**
     * 字符串两边加上[]
     * @param str 字符串
     * @return StringBuffer
     */
    private StringBuffer addMidBrackets(StringBuffer sb){
        return new StringBuffer("["+sb+"]");
    }
    
    /**
     * 去掉字符串两边的[]
     * @param str 字符串
     * @return String
     */
    private String removeMidBrackets(StringBuffer sb){
        return sb.toString().replaceAll("^\\[", "").replaceAll("\\]$", "");
    }
    
    private String handleSpecialChar(String str) {
    		StringBuffer sbTemp = new StringBuffer();
    		char[] arr = str.toCharArray();
    		for (char c : arr) {
				if (specialChar.indexOf(c) != -1) {
					sbTemp.append("\\"+c);
				} else {
					sbTemp.append(c);
				}
		}
    		return sbTemp.toString();
    }
    
    /**
     * 追加一个正则
     * @param regex
     */
    public RegexUtil append(RegexUtil regex) {
    		sBuffer.append(regex.toString());
    		return this;
    }
    
    /**
     * 追加一个正则表达式
     * @param str
     */
    public RegexUtil append(String str) {
    		sBuffer.append(str);
    		return this;
    }
    
    /**
     * 对自己进行否处理
     */
    public RegexUtil not(){
        sBuffer = new StringBuffer("[^"+sBuffer+"]");
        return this;
    }
    
    /**
     * 或一个正则
     * @param regex
     */
    public RegexUtil or(RegexUtil regex) {
    		or(regex.toString());
    		return this;
    }
    
    /**
     * 或一个正则表达式
     */
    public RegexUtil  or(String str) {
    		//最外层为中括号
    		if (checkMidBracket(sBuffer)) {
			//首先去掉中括号
    			sBuffer =  new StringBuffer(removeMidBrackets(sBuffer));
		}
    		if (str.length() > 1) {
    			//字符串用|
    			sBuffer.append("|" + handleSpecialChar(str));
		} else {
			 //非字符串直接追加
			sBuffer.append(handleSpecialChar(str));
		}
    		sBuffer = new StringBuffer(addMidBrackets(sBuffer));
    		return this;
    }
	
	public RegexUtil(){};
	
	/**
	 * 构造器
	 * @param regex 正则表达式
	 */
	public RegexUtil (String regex) {
		sBuffer = new StringBuffer(regex);
	}
	
	/**
	 * 构造器
	 * @param regex RegexUtil对象
	 */
	public RegexUtil(RegexUtil regex) {
		sBuffer = new StringBuffer(regex.toString());
	}
	
	public String toString() {
		return sBuffer.toString();
	}
	
	public static void main(String[] args) {
		//验证邮箱总规则。
		//邮箱名允许大小写字母数字下划线，域名可以是数字，大小写字母，点，下划线。
		RegexUtil emaiRegex = new RegexUtil();
		
		RegexUtil before = new RegexUtil(RegexUtil.letterAndNumAndUnderLine);
		//重复1-N次
		before.repeatOneOrMore();
		//将@前的规则追加到总规则上
		emaiRegex.append(before);
		//追加上@
		emaiRegex.append("@");
		System.out.println("step 1 " + before.toString());
		
		//@之后到最后一个域名点之前的规则
		RegexUtil after = new RegexUtil(RegexUtil.letterAndNumAndUnderLine);
		after.or(".");//允许点，防止耳机域名 @vip.qq.com
		System.out.println("aaa" + after.toString());
		after.or("-");//允许中划线
		System.out.println("bbb" + after.toString());
		after.repeatOneOrMore();
		System.out.println("ccc" + after.toString());
		
		//追加到总规则
		emaiRegex.append(after);
		
		emaiRegex.append(".");
		
		//顶级域名规则 com site xin cn
 		RegexUtil last = new RegexUtil(RegexUtil.lowLetter);
		last.repeatOneOrMore();
		
		emaiRegex.append(last);
		//打印总规则 [a-zA-Z0-9_]+@[a-zA-Z0-9_\.-]+\.[a-z]+
		System.out.println(emaiRegex);
		
		Pattern pattern = Pattern.compile(emaiRegex.toString());
		Matcher matcher = pattern.matcher("78gary@vip.qq.com");
		System.out.println(matcher.matches());
		
//		RegexUtil emaiRegex = new RegexUtil();
//		emaiRegex.append(RegexUtil.letterAndNumAndUnderLine)
//							 .repeatOneOrMore()
//							 .append("@")
//							 .append(new RegexUtil(RegexUtil.letterAndNumAndUnderLine)
//									 .or(".")
//									 .or("-")
//									 .repeatOneOrMore() )
//							 .append(".")
//							 .append(new RegexUtil(RegexUtil.lowLetter)
//									 .repeatOneOrMore() );
//		System.out.println(emaiRegex);
//		
//		Pattern pattern = Pattern.compile(emaiRegex.toString());
//		Matcher matcher = pattern.matcher("78gary@vip.qq.com");
//		System.out.println(matcher.matches());			 
		
		
	}
	
	
}
