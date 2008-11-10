package net.iskandar.murano_example.dto;

import java.io.Serializable;

public class PagingSettings implements Serializable {
	
	private int start = 1;
	private int count = 10;
	
	public PagingSettings() {
		super();
	}

	public PagingSettings(int start, int count) {
		super();
		this.start = start;
		this.count = count;
	}

	public int getStart() {
		return start;
	}
	
	/**
	 * Number of record to start selection with.
	 * One based.
	 * @param start
	 */
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

}
