package net.iskandar.murano_example.domain;

public enum Status {
	
	ACTIVE, INACTIVE, ALL;
	
	public String getLabel(){
		switch(this){
			case ACTIVE:
				return "Active";
			case INACTIVE:
				return "Inactive";
			default:
				return "All";
		}
	}
	
}
