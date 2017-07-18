package org.sagittarius.uitest.web.page.dataAnalysis.editProject.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sagittarius.uitest.util.PageInitUtil;

public enum ComponentEnum {
	KMX_TIMESERIES_DATASOURC {
		public WebElement getElement(WebDriver driver) {
			ComponentPage componentPage = new ComponentPage();
			PageInitUtil.initPages(driver, componentPage);
			return componentPage.kmxTimeSeriesDataSourc;
		}
	},
	HDFS_DATASOURC {
		public WebElement getElement(WebDriver driver) {
			ComponentPage componentPage = new ComponentPage();
			PageInitUtil.initPages(driver, componentPage);
			return componentPage.hdfsDataSourc;
		}
	},
	KMX_OBJECT_DATASOURC {
		public WebElement getElement(WebDriver driver) {
			ComponentPage componentPage = new ComponentPage();
			PageInitUtil.initPages(driver, componentPage);
			return componentPage.kmxObjectDataSourc;
		}
	},
	SCRIPT {
		public WebElement getElement(WebDriver driver) {
			ComponentPage componentPage = new ComponentPage();
			PageInitUtil.initPages(driver, componentPage);
			return componentPage.script;
		}
	};

	public abstract WebElement getElement(WebDriver driver);
}
