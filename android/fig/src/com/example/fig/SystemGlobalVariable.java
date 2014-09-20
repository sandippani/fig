package com.example.fig;

public class SystemGlobalVariable {
	
	private static Integer CURRENT_USER_ID=0;
	
	private SystemGlobalVariable(){
		
	}

	public static Integer getCurrentUserId() {
		return CURRENT_USER_ID;
	}

	public static void setCurrentUserId(Integer currentUserId) {
		CURRENT_USER_ID = currentUserId;
	}
	
	
	
	

}
