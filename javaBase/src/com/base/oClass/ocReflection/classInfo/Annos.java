package com.base.oClass.ocReflection.classInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



//@Repeatable(Annos.class)
@interface Anno{}
@Retention(value=RetentionPolicy.RUNTIME)
@interface Annos {
	Anno[] value();
}
