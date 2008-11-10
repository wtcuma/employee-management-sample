package net.iskandar.murano_example.dto;

import java.io.Serializable;
import java.util.List;

public class PagingResult<T> implements Serializable {

	private PagingSettings settings;
	private long total;
	private List<T> result;
	
	public PagingSettings getSettings() {
		return settings;
	}
	
	public void setSettings(PagingSettings settings) {
		this.settings = settings;
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getList() {
		return result;
	}
	
	public void setResult(List<T> result) {
		this.result = result;
	}
	
}
