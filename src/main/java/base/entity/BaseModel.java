package base.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.lang.reflect.Field;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class BaseModel {

    /**
     * 使用Apache Commons lang 自动化实现toString，避免使用Object类手写toString 方法太繁琐，属性修改后修改麻烦。
     * 使用 ToStringBuilder.reflectionToString() 代码简洁，无需任何配置。
     * Model属性变化，无需修改配置
     *
     * 缺陷：安全性问题，反射私有属性值也会暴露
     * 改进：利用ToStringBuilder的子类ReflectionToStringBuilder，覆盖其accept方法进行筛选。
     * */
    @Override
    public String toString(){
        return(new ReflectionToStringBuilder(this){
            @Override
            protected boolean accept(Field field){
                return super.accept(field) && !field.getName().equals("password");
            }
        }).toString();

        //return ToStringBuilder.reflectionToString(this);
    }

    /**
     * 如果hashCode取决于该class的所有field时需要使用反射机制产生一个hashCode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * 如果两个对象相等当且仅当每个属性值都相等
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
