package com.pooja;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pooja.comp.MyBean;

public class Main {

	public static void main(String[] args) {
		ApplicationContext appCntx = new ClassPathXmlApplicationContext("cfg.xml");
		MyBean mb = (MyBean)appCntx.getBean("my");
		System.out.println(mb.getMsgId());
		System.out.println(mb.getMsg());
	}

}


