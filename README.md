# RegexUtil
正则表达式工具类,很容易地创建一个正则表达式 而不用再去翻阅繁杂的语法

##Usage
```java
//例子：验证是否为合法邮箱。
		
		//用法1：分部
		//邮箱名允许大小写字母数字下划线，域名可以是数字，大小写字母，点，下划线。
		RegexUtil emaiRegex = new RegexUtil();
		
		RegexUtil before = new RegexUtil(RegexUtil.letterAndNumAndUnderLine);
		//重复1-N次
		before.repeatOneOrMore();
		//将@前的规则追加到总规则上
		emaiRegex.append(before);
		//追加上@
		emaiRegex.append("@");
		
		//@之后到最后一个域名点之前的规则
		RegexUtil after = new RegexUtil(RegexUtil.letterAndNumAndUnderLine);
		after.or(".");//允许点，防止耳机域名 @vip.qq.com
		after.or("-");//允许中划线
		after.repeatOneOrMore();
		
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
		
		//用法二：一气呵成
		RegexUtil emaiRegex1 = new RegexUtil();
		emaiRegex1.append(RegexUtil.letterAndNumAndUnderLine)
							 .repeatOneOrMore()
							 .append("@")
							 .append(new RegexUtil(RegexUtil.letterAndNumAndUnderLine)
									 .or(".")
									 .or("-")
									 .repeatOneOrMore() )
							 .append(".")
							 .append(new RegexUtil(RegexUtil.lowLetter)
									 .repeatOneOrMore() );
		System.out.println(emaiRegex1);
		
		Pattern pattern1 = Pattern.compile(emaiRegex1.toString());
		Matcher matcher1 = pattern1.matcher("78gary@vip.qq.com");
		System.out.println(matcher1.matches());
