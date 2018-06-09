package umlAssociation.Association;

import umlAssociation.Dependency.Car;

public class Association {
   
    //使用成员变量形式实现关联   
    Car mycar;   
    public void drive(){   
        mycar.run();   
    }   
     
    //使用方法参数形式实现关联   
    public void drive(Car car){   
        car.run();   
    }   
}  