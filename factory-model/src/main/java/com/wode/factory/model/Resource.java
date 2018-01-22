package com.wode.factory.model;

/**
 * Created by zoln on 2016/1/15.
 */
public class Resource {

	private Integer id;

	private String name;

	private String uri;

	private String description;

	private String parent;

	private String ancestor;

	public Resource(){}

	public Resource(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getAncestor() {
		return ancestor;
	}

	public void setAncestor(String ancestor) {
		this.ancestor = ancestor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Resource) {
			Resource resource = (Resource) obj;
			String name = getName();
			return name.equals(resource.getName()) || name.equals(resource.getAncestor()) || name.equals(resource.getParent());
		}
		return super.equals(obj);
	}
}
