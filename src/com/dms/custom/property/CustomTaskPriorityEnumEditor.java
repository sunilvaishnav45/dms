package com.dms.custom.property;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import com.dms.Enum.TaskPriorityEnum;

public class CustomTaskPriorityEnumEditor extends PropertyEditorSupport{

private static final Logger log = Logger.getLogger(CustomTaskPriorityEnumEditor.class);
	
	@Override
    public String getAsText() {
        TaskPriorityEnum text = (TaskPriorityEnum) getValue();
        return text == null ? "" : text.getDisplayName();
    }
     
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    	log.info("CustomTaskPriorityEnumEditor called for"+text);
       super.setValue(TaskPriorityEnum.getTaskPriorityEnumFor(text));
       
    }
}
