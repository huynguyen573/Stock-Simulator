package Controllers;

import UseCases.LoginUseCase.UserLoginInteractor;
import UseCases.LoginUseCase.UserLoginRequest;
import UseCases.LoginUseCase.UserLoginResponse;

public class UserLoginController {
    private final UserLoginInteractor interactor;

    public UserLoginController() {
        interactor = new UserLoginInteractor();
    }

    /**
     * This method is used to login a user by taking in a param of type UserLoginRequest and returning a UserLoginResponse
     * @param request the request to login a user
     * @return UserLoginResponse
     */
    public UserLoginResponse loginUser(UserLoginRequest request) {
        return new UserLoginResponse(
                interactor.loginUser(
                        request.username(),
                        request.password(),
                        request.loginDate()));
    }

}