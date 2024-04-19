package UseCases.RegisterUseCase;

public enum RegisterError {
    /**
     * Enum for the different types of register use cases
     */
    NONE,
    USERNAME,
    PASSWORD_NOT_MATCH,
    PASSWORD_INVALID
}