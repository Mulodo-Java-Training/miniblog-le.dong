package com.mulodo.miniblog.genarate;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class GentableHibernate {
	public static void main(String[] args) {
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernate.xml");
		new SchemaExport(config).create(true, true);
	}
}
