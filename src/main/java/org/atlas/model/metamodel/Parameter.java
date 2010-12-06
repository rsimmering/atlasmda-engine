package org.atlas.model.metamodel;

import org.apache.commons.lang.StringUtils;

public class Parameter extends Element {

    private String type;
    private boolean many = false;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMany() {
        return many;
    }

    public void setMany(boolean many) {
        this.many = many;
    }

    @Override
    public String getName() {
        if(super.getName()==null) {
            setName(StringUtils.uncapitalize(type));
        }

        return super.getName();
    }

}
