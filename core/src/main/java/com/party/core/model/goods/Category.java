package com.party.core.model.goods;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * Category
 *
 * @author Wesley
 * @data 16/9/7 15:40 .
 */
public class Category  extends BaseModel implements Serializable {

    private static final long serialVersionUID = 7903507565471597734L;

    private String name;		// 类型名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Category category = (Category) o;

        return name != null ? name.equals(category.name) : category.name == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
