package org.sagittarius.uitest.web.page.dataAnalysis.editProject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class ProjectCanvasPage implements PageUI {
	
	@FindBy(id = "v-2")
	public WebElement editBackground;
	
	@FindBy(xpath = "//input[@type='text']")
	public WebElement nameInput;
	
	@FindBy(xpath = "//span[text()='取 消']")
	public WebElement cancelBtn;
	
	@FindBy(xpath = "//span[text()='确 定']")
	public WebElement confirmBtn;
	
	@FindBy(className = "fa-reply")
	public WebElement backBtn;
	
	@FindBy(className = "fa-floppy-o")
	public WebElement saveBtn;
	
	@FindBy(className = "fa-play")
	public WebElement runBtn;
}
