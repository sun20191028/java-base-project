package com.base.oClass.ocReflection.instance;

public class EntityClass {
	
	private String name;
	public int age;
	
	private EntityClass(){
		
	}
	
	private EntityClass(String name){
		this.name = name;
	}

	@Override
	public String toString() {
		return "EntityClass [name=" + name + ", age=" + age + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
