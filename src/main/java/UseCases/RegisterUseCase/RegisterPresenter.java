package UseCases.RegisterUseCase;

import Controllers.RegisterController;
import UseCases.LoginUseCase.UserLoginPresenter;
import main.OuterLayerFactory;

import java.sql.Date;
import java.time.LocalDate;

/**
 * This class is responsible for controlling the logic of
 * the user registration view
 */
public class RegisterPresenter {
    private final RegisterView view;
    private final RegisterController controller;

    /**
     * @param view not-null input
     *  initializes the presenter and adds button action observers
     */
    public RegisterPresenter(RegisterView view) {
        this.view = view;
        controller = new RegisterController();

        view.addRegisterAction(this::onRegister);
        view.addBackAction(this::onBack);
    }

    /**
     * Contains logic for when the user clicks on the register button
     */
    private void onRegister() {
        RegisterRequest request = new RegisterRequest(
                view.getUsername(),
                view.getPassword(),
                view.getPasswordConfirm(),
                Date.valueOf(LocalDate.now()));

        RegisterResponse response = controller.signUpUser(request);
        RegisterError userSignedUp = response.userSignedUp();

        if (userSignedUp == RegisterError.USERNAME) {
            view.presentUsernameError();
        } else if (userSignedUp == RegisterError.PASSWORD_INVALID) {
            view.presentPasswordInvalidError();
        } else if (userSignedUp == RegisterError.PASSWORD_NOT_MATCH) {
            view.presentPasswordNotMatchError();
        } else {
            onBack();
        }
    }

    /**
     * contains logic for when the user clicks on the back button, ie moves to the login view
     */
    private void onBack() {
        view.close();
        new UserLoginPresenter(OuterLayerFactory.instance.getUserLoginGUI());
    }
}