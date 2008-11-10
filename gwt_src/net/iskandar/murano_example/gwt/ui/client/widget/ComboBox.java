package net.iskandar.murano_example.gwt.ui.client.widget;

import com.extjs.gxt.ui.client.data.ModelData;

public class ComboBox extends com.extjs.gxt.ui.client.widget.form.ComboBox<ModelData> {

	public void setKeyValue(Integer value){
		if(value != null){
			setValue(findModel(getValueField(), value.toString()));
		}
	}
	
	public Integer getKeyValue(){
		ModelData value = getValue();
		return value != null ? (Integer) value.get(getValueField()) : null;
	}
	

}
