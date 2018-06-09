package com.zzl.baEnumTest;

public class Product {
	public enum Status{
		BUYY("BUYY"),CHECK("CHECK");
		private String value="";
		private Status(String value){
			this.value=value;
		}
		public String getValue(){
			return value;
		}
	}
//	public enum InnerChgType {
//		CCY("CCY"), CARD_TYPE("CARD_TYPE"), ID_TYPE("ID_TYPE"), TEL_TYPE("TEL_TYPE");
//		private String value = "";
//
//		private InnerChgType(String value) {
//			this.value = value;
//		}
//
//		public String getValue() {
//			return value;
//		}
//
//	}

}
