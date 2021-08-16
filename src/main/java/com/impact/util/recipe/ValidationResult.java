package com.impact.util.recipe;

public class ValidationResult<T> {
	private final T result;
	
	public ValidationResult(T resultIn) {
		this.result = resultIn;
	}
	
	public T getResult() {
		return this.result;
	}
	
	public static <T> ValidationResult<T> newResult(T value) {
		return new ValidationResult<>(value);
	}
}