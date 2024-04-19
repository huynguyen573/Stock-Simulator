package UseCases.SellStockUseCase;

public class SellOutputResponse {

    /**
     * This class is used to create responses for the sell stock use case
     */
    private final boolean success;
    private final String message;

    /**
     * This constructor is used to create a response for the sell stock use case
     * @param success whether the sell was successful
     * @param message the message to be displayed to the user
     */
    public SellOutputResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean possible() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}