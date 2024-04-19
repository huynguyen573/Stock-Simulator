package SearchStockTest;

import UseCases.SearchStockUseCase.ViewStockUseCaseInteractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SearchStockInteractorTest {
    private static ViewStockUseCaseInteractor incorrectInteractor;
    private static ViewStockUseCaseInteractor correctInteractor;
    private static final String incorrectSymbol = "incorrectSymbol";
    private static final String correctSymbol = "AAPL";

    @BeforeAll
    public static void setUp() {
        incorrectInteractor = new ViewStockUseCaseInteractor(incorrectSymbol);
        correctInteractor = new ViewStockUseCaseInteractor(correctSymbol);
    }

    /**
     * Inputs invalid symbol to receive an Exception
     */
    @Test
    public void incorrectSymbol(){
        Assertions.assertThrows(NullPointerException.class, ()-> incorrectInteractor.searchStock());
    }

    /**
     * Inputs valid symbol to receive a Stock object
     */
    @Test
    public void correctSymbol(){
        Assertions.assertDoesNotThrow(()-> correctInteractor.searchStock());
    }

}