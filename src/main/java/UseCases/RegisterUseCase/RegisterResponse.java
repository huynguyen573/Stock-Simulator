package UseCases.RegisterUseCase;

/**
 * Response model object for when user attempts to register, contains
 * returned user object (could be null)
 */
public final class RegisterResponse {
    private final RegisterError userSignedUp;

    public RegisterResponse(RegisterError userSignedUp) {
        this.userSignedUp = userSignedUp;
    }

    /**
     * A getter for the userSignedUp
     * @return RegisterError type of the userSignedUp
     */
    public RegisterError userSignedUp() {
        return userSignedUp;
    }
}