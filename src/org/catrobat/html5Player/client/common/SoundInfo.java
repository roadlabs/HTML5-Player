package org.catrobat.html5Player.client.common;

public class SoundInfo implements Comparable<SoundInfo> {

	private String id;

	private String title;

	private String fileName;

	public boolean playing;

	public boolean paused;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	@Override
	public int compareTo(SoundInfo other) {
		if (other == null) {
			return -255;
		}
		if (this.id == null && other.id == null) {
			return 0;
		} else if (this.id == null) {
			return 128;
		} else if (other.id == null) {
			return -128;
		} else if (this == other || this.equals(other)) {
			return 0;
		}
		return this.id.compareTo(other.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		SoundInfo other = (SoundInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
