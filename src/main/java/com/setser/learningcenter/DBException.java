package com.setser.learningcenter;

public class DBException extends Exception {
	private static final long serialVersionUID = 5421770935032087431L;
	public DBException(String string) { super(string); }
    public DBException(Throwable throwable) {
        super(throwable);
    }
}
