package net.iskandar.murano_example.gwt.ui.client;

import java.util.ArrayList;
import java.util.List;

import net.iskandar.murano_example.dto.SelectOption;
import net.iskandar.murano_example.gwt.ui.client.widget.ComboBox;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;


public class Utils {

	public static ComboBox createSelectOptionComboBox(List<SelectOption> options){
		return createComboBox(convertSelectOptionsToModelData(options));
	}
	
	public static ComboBox createComboBox(List<BaseModelData> options){

		ListStore store = new ListStore();

		store.add(options);
		ComboBox comboBox = new ComboBox();
		comboBox.setStore(store);
		comboBox.setValueField(SelectOption.VALUE_FIELD);
		comboBox.setDisplayField(SelectOption.LABEL_FIELD);
		comboBox.setEditable(false);

		return comboBox;
	}

	
	public static List<BaseModelData> convertSelectOptionsToModelData(
			List<SelectOption> result) {
		List<BaseModelData> positions = new ArrayList<BaseModelData>();
		for (SelectOption selectOption : result) {
			BaseModelData baseModelData = new BaseModelData();
			baseModelData
					.set(SelectOption.VALUE_FIELD, selectOption.getValue());
			baseModelData
					.set(SelectOption.LABEL_FIELD, selectOption.getLabel());
			positions.add(baseModelData);
		}
		return positions;
	}
	
}
