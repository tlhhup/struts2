#struts2
1. 类型转换器<br>
  1、继承DefaultTypeConverter类重写convertValue方法<br>
  2、局部类型转换器(针对某个action中的属性)-->在action所在的包中编写ActionClassName(action的简单类名)-conversion.properties<br>
  	文件，文件内容为：属性名称=类型转换器的全类名(如：date=com.struts2.action.convertor.DateConvertor)<br>
  3、全局类型转换器(针对具体类型的数据)-->在src根目录编写xwork-conversion.properties文件<br>
          文件内容为：待转换的类型全类名=类型转换器的全类名
2. 关于struts配置文件说明：<br>
  1、文档说明：<br>
  `
  <!DOCTYPE struts PUBLIC
    "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN"
    "http://struts.apache.org/dtds/struts-2.0.dtd">
  `
