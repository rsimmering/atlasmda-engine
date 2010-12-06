package org.atlas.model.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Element {

    private String name;
    private Map<String, Tag> tags;
    private Map<String, List<Tag>> tagsByType;

    public Element() {
        tags = new HashMap<String, Tag>();
        tagsByType = new HashMap<String, List<Tag>>();
    }

    public void addTag(Tag tag) {
        tags.put(tag.getName(), tag);
        if(tag.getType() != null) {
            if(tagsByType.get(tag.getType()) == null) {
                tagsByType.put(tag.getType(), new ArrayList<Tag>());
            }
            tagsByType.get(tag.getType()).add(tag);
        }
    }

    public Tag getTag(String name) {
        return tags.get(name);
    }

    public String getTagValue(String name) {
        if(tags.get(name)==null) {
            return null;
        }
        return getTag(name).getValue();
    }

    public boolean hasTag(String name) {
        return tags.containsKey(name);
    }

    public List<Tag> getTagsOfType(String type) {
        return tagsByType.get(type);
    }

    public boolean hasTagsOfType(String type) {
        return tagsByType.containsKey(type);
    }

    public Map<String, Tag> getTags() {
        return tags;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
