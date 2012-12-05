package org.catrobat.html5Player.client.common;

public class CostumeData implements Comparable<CostumeData> {

	private String name;

	private String filename;

	private int width;

	private int height;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int compareTo(CostumeData other) {
		if (other == null) {
			return -255;
		}
		if (this.name == null && other.name == null) {
			return 0;
		} else if (this.name == null) {
			return 128;
		} else if (other.name == null) {
			return -128;
		} else if (this == other || this.equals(other)) {
			return 0;
		}
		return this.name.compareTo(other.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CostumeData other = (CostumeData) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
