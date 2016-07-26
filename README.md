#struts2
1. struts2：基于MVC模式的框架,简化开发工作，提高效率--->界面交互
2. 使用：
3. 知识点：
	1. 环境搭建--->第一应用
		1. 下载：apache开源项目   https://struts.apache.org/download.cgi#struts252
		2. 第一个应用
			1. 创建项目
			2. 导入jar包
			3. 配置文件：
				1. web.xml配置：
				
						<!-- struts2的过滤器 -->
						<filter>
						    <filter-name>Struts2</filter-name>
						    <filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
						</filter>
						<filter-mapping>
						    <filter-name>Struts2</filter-name>
						    <url-pattern>/*</url-pattern>
						</filter-mapping>
				2. struts的配置文件：
					1. struts.xml
			2. 编写action
				1. 普通的class(url组成：namespace+ActionName)
				2. 继承ActionSupport
			3. Action配置中的各项默认值
				1. 如果没有为action指定class，默认是ActionSupport。
				2. 如果没有为action指定method，默认执行action中的execute() 方法。
				3. 如果没有指定result的name属性，默认值为success。
	2. struts的配置文件详解
		1. 常量：constant节点进行配置----->定义在default.properties文件中
			1. struts.i18n.encoding：国际化的
			2. struts.objectFactory：对象工厂(spring整合之后需要配置的常量)
			3. struts.action.extension：设置Action的后缀(多个后缀之前使用","分隔)
			4. struts.enable.DynamicMethodInvocation：动态方法调用开关
			5. struts.devMode：是否启用开发者模式(建议开发的时候启用，上线关闭)--->得到更详细错误信息
			6. struts.ui.theme：设置主题
			7. struts.configuration.xml.reload：当struts.xml配置文件改变则重新加载配置信息

					<constant name="struts.action.extension" value="go" />
					<constant name="struts.configuration.xml.reload" value="true" />
					<constant name="struts.devMode" value="true" />
		2. 返回类型<result type属性控制---->struts-default.xml文件定义的
			1. dispatcher：默认的
			2. redirectAction：重定向action
				1. 同包
				2. 不同包(namespace   actionName)
			3. stream:流--->输入流-->文件下载
			4. plainText：文本类型的数据
	3. Action中数据注入
		1. 普通属性
			1. 该action中定义简单的属性
			
					// 1、通过普通属性接受数据--->属性名和参数名一致
					private String userName;
					private String password;
				
					// 2、该属性的set方法不能少
					public void setUserName(String userName) {
						this.userName = userName;
					}
					
					public void setPassword(String password) {
						this.password = password;
					}
					//界面
					<input type="text" name="userName">
		2. 复合属性-->javabean
			1. 编写javabean对象
			
					public class User implements Serializable {
						private static final long serialVersionUID = 1L;
					
						private String userName;
						private String password;
						//get、set方法
			2. action中声明该javabean的属性

					public class LoginAction extends ActionSupport {
						private User user;
						//get、set方法
			3. 界面
					
					<input type="text" name="user.userName">

		3. 使用ModelDriven拦截器
			1. 实现ModelDriven接口

					public class UserAction extends ActionSupport implements ModelDriven<User> {

						private static final long serialVersionUID = 1L;
						
						private User user;
					
						@Override
						public User getModel() {
							user=new User();
							return user;
						}
			2. 界面
			
					<input type="text" name="userName">
	4. 动态方法调用
		1. 动态方法调用：url地址!方法的名称.后缀
			1. struts.enable.DynamicMethodInvocation：动态方法调用开关--->false
			
					<a href="UsersAction!logout.do">退出</a>
			
		2. 使用通配符：*---->格式 第一*位置从1开始

				 <action name="UserActionWildcard_*" class="com.paramreceive.action.UserActionWildcard" method="{1}">
			        <result name="logout">/login4.jsp</result>
			     </action>
				
				//将*改成具体的方法名称
				<a href="UserActionWildcard_logout.do">退出</a>
	3. 获取application、request、session对象
		1. 获取
		2. 存值
		3. 界面取值

				ActionContext context = ActionContext.getContext();//request范围
				context.put("req", "request范围的数据");
				context.getApplication().put("app", "application范围的数据");
				context.getSession().put("se", "session范围的数据");
				
				context.getSession().put("user", model);
	4. 获取ServletContext、httpServletRequest，httpSession对象
		1. ServletActionContext

				ServletContext servletContext = ServletActionContext.getServletContext();
				servletContext.setAttribute("app", "servletContext范围的数据");
				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("req", "request中的数据");
				HttpSession session = request.getSession();
				session.setAttribute("se", "session中的数据");
		2. 使用拦截器注入--->通过实现各种XXXaware的接口

				public class UserActionWildcard extends BaseAction<User> implements ServletContextAware,RequestAware,SessionAware{
	4. 拦截器---------->权限控制
		1. struts本身自带了一系列的拦截器--->modeldriven(注入数据)
		2. 自定义拦截器
			1. 定义一个类实现Interceptor接口

					public class RightInterceptor implements Interceptor {
						private static final long serialVersionUID = 1L;
					
						@Override
						public void destroy() {
					
						}
					
						@Override
						public void init() {
					
						}
					
						@Override
						public String intercept(ActionInvocation actionInvocation) throws Exception {
							System.out.println("RightInterceptor");
							return actionInvocation.invoke();
						}
					
					}
			2. 在struts.xml文件中进行注册

					<!-- 拦截器 -->
					<interceptors>
						<!-- 配置的拦截器 -->
						<interceptor name="RightInterceptor" class="com.conver.interceptor.RightInterceptor"/>
						<!-- 管理拦截器 -->
						<interceptor-stack name="myStack">
							<!-- struts的拦截器保留 -->
							<interceptor-ref name="defaultStack"/>
							<!-- 注入拦截器到拦截器栈中 -->
							<interceptor-ref name="RightInterceptor"/>
						</interceptor-stack>
					</interceptors>
					
					<!-- 指定使用的拦截器:针对于所有的action都有效 -->
					<default-interceptor-ref name="myStack"/>
	5. 类型转换器------>继承类DefaultTypeConverter 重写converter方法
				
				public class DateConverter extends DefaultTypeConverter {

					@Override
					public Object convertValue(Object source, Class target) {
						try {
							if (target == Date.class) {
								if (source != null) {
									DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
									return format.parse(source.toString());
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}
				
				}
			
		1. 局部--->针对某个action的
			1. 配置信息配置在该action所在的包中
			2. 配置文件名称为：ActionClassName-conversion.properties(actionClassName为Aciton的列名，后面为固定写法)
			3. 文件内容：属性名称=类型转换器的全类名

					#local converter
					user.birthday=com.conver.converter.DateConverter
		2. 全局---->针对某种数据类型
			1. 配置信息配置问class根目录下(src根目录)
			2. 配置文件的名称为：xwork-conversion.properties
			3. 文件内容：转换类型全类名=类型转换器的全类名

					java.util.Date=com.conver.converter.DateConverter
	3. 输入校验
		1. xml的方式
		2. 方法校验----->Action必须继承至ActionSupport
			1. 所有方法
				1. 重写ActionSupport的validate方法
				2. 校验失败addActionXXX的方法设置错误提示信息
				3. 当校验失败默认跳转到逻辑视图名为input的视图
				4. 界面通过struts标签输入提示信息
			2. 指定的方法--->某个特定的方法-->当调用该方法的时候才会触发该校验
				1. validate方法名称(方法名首字母大写)

						public void validateLogin() {
							System.out.println("validateLogin");
							//校验失败--->产生了错误
							if(!"admin".equals(model.getUserName())||!"admin".equals(model.getPassword())){
								this.addActionError("用户名或密码错误");//跳转input视图
							}
						}
	4. 文件上传
		1. jar包：commons-fileupload-1.3.1.jar、commons-io-2.2.jar
		2. form表单必须设置类型为

				<form action="FileUploadAction_upload.do" enctype="multipart/form-data" method="post">
		3. action属性的定义

				private String uploadFileContentType;//接受文件的类型(去掉)
				private String uploadFileFileName;//接受上传文件的名称
				private File uploadFile;//接受上传的文件
				//get set 方法
		4. 完成文件上传-->保存服务器中

				//文件上传保存项目根目录下的upload文件夹
				String path = ServletActionContext.getRequest().getRealPath("/upload");
				File dir=new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				//上传文件了
				//1、文件的后缀
				String sub=this.uploadFileFileName.substring(uploadFileFileName.lastIndexOf(".")+1);
				//2、构建新的文件名称
				String fileName=System.currentTimeMillis()+"."+sub;
				//3、保存的文件
				File dest=new File(dir, fileName);
				
				this.uploadFile.renameTo(dest);
	5. 文件下载
		1. action

				public InputStream getTextStream() {
					try {
						return new ByteArrayInputStream("hello world".getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
		2. 配置文件

				<action name="FileDownLoadAction_*" class="com.fileupload.action.FileDownLoadAction" method="{1}">
					<result name="download" type="stream">
					    <!-- 文件的类型 -->
						<param name="contentType">text/plain</param>
						<!-- 文件输入流的名称 
							  在该action查询方法名为：getXXX() 返回值类型为InputStream
							 如：getImageStream()
						-->
						<param name="inputName">textStream</param>
						<!-- 文件描述信息 -->
						<param name="contentDisposition">attachment;filename="document.text"</param>
						<!-- 缓冲区的大小 -->
						<param name="bufferSize">1024</param>
						<!-- 编码方式 -->
						<param name="contentCharSet">UTF-8</param>
					</result>
				</action>
	4. 标签-----