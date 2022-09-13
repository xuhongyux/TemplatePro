package javaApi.beanUtils;

import org.springframework.beans.BeanUtils;

/**
 * @author xuhongyu
 * @create 2022-07-12 10:59 上午
 */
public class CopyTest {

    public static void main(String[] args) {
        User user = new User();
        Dog dog = new Dog();
        dog.setName("dogName");
        user.setUserName("userName");
        user.setDog(dog);

        User user1 = new User();

        BeanUtils.copyProperties(user,user1);

        System.out.println(user1);
        dog.setName("newName");

        System.out.println(user1);

    }
}
