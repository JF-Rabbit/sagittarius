package org.sagittarius.uitest.web.page.dataAnalysis;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sagittarius.uitest.util.PageInitUtil;

public enum ComponentEnum {
	KMX_TIMESERIES_DATASOURC {
		public WebElement getElement(WebDriver driver) {
			CreateProjectPage createProjectPage = new CreateProjectPage();
			PageInitUtil.initPages(driver, createProjectPage);
			return createProjectPage.kmxTimeSeriesDataSourc;
		}
	},
	HDFS_DATASOURC {
		public WebElement getElement(WebDriver driver) {
			CreateProjectPage createProjectPage = new CreateProjectPage();
			PageInitUtil.initPages(driver, createProjectPage);
			return createProjectPage.hdfsDataSourc;
		}
	},
	KMX_OBJECT_DATASOURC {
		public WebElement getElement(WebDriver driver) {
			CreateProjectPage createProjectPage = new CreateProjectPage();
			PageInitUtil.initPages(driver, createProjectPage);
			return createProjectPage.kmxObjectDataSourc;
		}
	},
	SCRIPT {
		public WebElement getElement(WebDriver driver) {
			CreateProjectPage createProjectPage = new CreateProjectPage();
			PageInitUtil.initPages(driver, createProjectPage);
			return createProjectPage.script;
		}
	};

	public abstract WebElement getElement(WebDriver driver);
}
