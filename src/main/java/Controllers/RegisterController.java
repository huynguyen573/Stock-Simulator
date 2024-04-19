package Controllers;

import UseCases.RegisterUseCase.RegisterError;
import UseCases.RegisterUseCase.RegisterInteractor;
import UseCases.RegisterUseCase.RegisterRequest;
import UseCases.RegisterUseCase.RegisterResponse;

public class RegisterController {
    private final RegisterInteractor interactor;

    public RegisterController() {
        interactor = new RegisterInteractor();
    }

    public RegisterResponse signUpUser(RegisterRequest request) {
        RegisterError userSignedUp = interactor.signUpUser(
                request.username(),
                request.password(),
                request.passwordConfirm(),
                request.loginDate());

        return new RegisterResponse(userSignedUp);
    }
}