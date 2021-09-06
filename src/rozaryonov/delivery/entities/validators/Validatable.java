package rozaryonov.delivery.entities.validators;

public interface Validatable <T> {
	boolean validate(T entity);
}
