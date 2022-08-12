package laz.example.urlshortener;

import laz.example.urlshortener.services.CrumbService;
import laz.example.urlshortener.services.InMemoryHashMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class InMemoryHashMapShould {

    @MockBean
    private CrumbService crumbService;

    private InMemoryHashMapService inMemoryHashMapService;

    @BeforeEach
    void initUseCase() {
        inMemoryHashMapService = new InMemoryHashMapService(crumbService);
    }

    @Test
    public void whenGivenURL_ThenAssignItACrumb() {
        when(crumbService.getCrumb(anyInt())).thenReturn("ExampleCrumb");

        assertThat(inMemoryHashMapService.genAndStoreURLForCrumb("https://url.example.com")).asString()
                .isEqualTo("ExampleCrumb");
    }

    @Test
    public void defaultCrumbLengthTo3() {
        when(crumbService.getCrumb(eq(3))).thenReturn("Defaulted to three");
        when(crumbService.getCrumb(not(eq(3)))).thenReturn("Not defaulted to three");

        assertThat(inMemoryHashMapService.genAndStoreURLForCrumb("https://url.example.com")).asString()
                .isEqualTo("Defaulted to three");
    }

    @Test
    public void whenGivenConflictingCrumb_ThenRequestACrumbOneLarger() {
        when(crumbService.getCrumb(eq(3))).thenReturn("ExampleCrumb3");
        when(crumbService.getCrumb(eq(4))).thenReturn("ExampleCrumb4");
        when(crumbService.getCrumb(eq(5))).thenReturn("ExampleCrumb5");
        when(crumbService.getCrumb(eq(6))).thenReturn("ExampleCrumb6");

        assertThat(inMemoryHashMapService.genAndStoreURLForCrumb("https://url.example.com")).asString()
                .isEqualTo("ExampleCrumb3");
        assertThat(inMemoryHashMapService.genAndStoreURLForCrumb("https://url.example2.com")).asString()
                .isEqualTo("ExampleCrumb4");
        assertThat(inMemoryHashMapService.genAndStoreURLForCrumb("https://url.example3.com")).asString()
                .isEqualTo("ExampleCrumb5");
        assertThat(inMemoryHashMapService.genAndStoreURLForCrumb("https://url.example4.com")).asString()
                .isEqualTo("ExampleCrumb6");
    }

    @Test
    public void returnCrumbForExistingURL() {
        // Not implemented
    }

    @Test
    public void returnEmptyOptionalForNonExistingURL() {
        // Not implemented
    }
}