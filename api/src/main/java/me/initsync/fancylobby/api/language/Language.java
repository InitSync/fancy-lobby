package me.initsync.fancylobby.api.language;

public enum Language {
	ENGLISH ("en_us", "English"),
	SPANISH ("es_es", "Español");
	
	private final String id;
	private final String name;
	
	Language(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
