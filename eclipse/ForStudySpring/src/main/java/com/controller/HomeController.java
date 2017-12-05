package com.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.io.File;
import java.text.DateFormat;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.configs.AppConfig;
import com.interfaces.services.UserService;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private ApplicationContext context;
	@SuppressWarnings("unused")
	private UserService userService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		// TODO 2.1.2 ApplicationCONTEXTとBean定義
		// コンフィギュレーションクラスを渡してDIコンテナ（ApplicationContext）を生成。
//		context = new AnnotationConfigApplicationContext(AppConfig.class); // Javaベースコンフィギュレーションを使用する方法。
//		context = new AnnotationConfigApplicationContext("/FroStudySpring/src/main/java/com/configs"); //アノテーションベースコンフィギュレーションを使用する方法。
//																									   //パッケージ名以下をコンポーネントスキャンする。
//		context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml"); // XMLベースコンフィギュレーションを使用する方法。
//																		 					    // xmlがBean定義として使用される。
		ServletContext cs
		File contextXml = new File("applicationContext.xml");
		System.out.println("contextXML: " + contextXml);
		System.out.println("contextXML: " + contextXml.getPath());
		System.out.println("contextXML: " + contextXml.getAbsolutePath());
		if(!contextXml.exists()){
			System.out.println("DI: applicationContextの読み込みに失敗。JavaベースConfigurationを使用");
			context = new AnnotationConfigApplicationContext(AppConfig.class); // Javaベースコンフィギュレーションを使用する方法。
		} else {
			System.out.println("DI: xmlファイルのベースConfigurationを使用");
			context = new FileSystemXmlApplicationContext(contextXml.getAbsolutePath());
		}
		
		// UserServiceImplをルックアップ
		userService = context.getBean(UserService.class); // Beanの型を指定する方法
		userService = context.getBean("userService", UserService.class); // Beanの名前と型を指定する方法。指定する型が複数ある場合に使用
		userService = (UserService)context.getBean("userService"); // Beanの名前を指定する方法。
		
		// java8になっているかのテスト
		List<String> list = Arrays.asList("aaa", "bbb");
		Optional<String> result = list.stream()
				.filter(x -> x.startsWith("a"))
				.findAny();
		System.out.println("result: " + result.orElse("nothing"));
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
