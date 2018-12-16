package com.dms.custom.property;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import com.dms.Enum.TaskDeliveryStageEnum;

public class CustomTaskDeliveryStageEnumEditor extends PropertyEditorSupport {
	
	private static final Logger log = Logger.getLogger(CustomTaskDeliveryStageEnumEditor.class);
	
	@Override
    public String getAsText() {
        TaskDeliveryStageEnum text = (TaskDeliveryStageEnum) getValue();
        return text == null ? "" : text.getDisplayName();
    }
     
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    	log.info("CustomTaskDeliveryStageEnumEditor called for"+text);
       super.setValue(TaskDeliveryStageEnum.getTaskDeliveryEnumFor(text));
    }

}
