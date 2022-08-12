package laz.example.urlshortener;

import laz.example.urlshortener.services.CrumbService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CrumbServiceShould {

    private CrumbService crumbService = new CrumbService();

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 0, 15})
    public void whenGivenLength_thenReturnCrumbOfLength(final int length) {
        assertThat(crumbService.getCrumb(length)).asString().hasSize(length);
    }

    @ParameterizedTest
    @ValueSource(ints = {-3})
    public void whenGivenBadLength_thenThrowIllegalArgument(final int length) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> crumbService.getCrumb(length));
    }
}