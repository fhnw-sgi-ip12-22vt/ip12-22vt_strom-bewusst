package ch.fhnw.strombewusst;

import com.almasb.fxgl.app.services.FXGLAssetLoaderService;
import com.almasb.fxgl.dsl.FXGL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeamNameTest {
    private static MockedStatic<FXGL> fxgl;

    private static List<String> firstNameStrings = List.of(
            "First1",
            "First2",
            "First3",
            "First4",
            "First5"
    );
    private static List<String> secondNameStrings = List.of(
            "Second1",
            "Second2",
            "Second3",
            "Second4",
            "Second5"
    );

    @InjectMocks
    private static TeamName teamName;

    @BeforeAll
    static void setUpDSLMock() {
        FXGLAssetLoaderService assetLoaderService = mock(FXGLAssetLoaderService.class);
        when(assetLoaderService.loadText(Config.TEAM_NAMES_FIRST_LIST_PATH)).thenReturn(firstNameStrings);
        when(assetLoaderService.loadText(Config.TEAM_NAMES_SECOND_LIST_PATH)).thenReturn(secondNameStrings);

        fxgl = Mockito.mockStatic(FXGL.class);
        fxgl.when(FXGL::getAssetLoader).thenReturn(assetLoaderService);
    }

    @BeforeEach
    void setUp() {
        teamName = new TeamName();
    }

    @Test
    void ctorName() {
        assertTrue(firstNameStrings.contains(teamName.toString().split(" ")[0]));
        assertTrue(secondNameStrings.contains(teamName.toString().split(" ")[1]));
    }

    @Test
    void updateFirst() {
        String[] nameSplitBefore = teamName.toString().split(" ");
        teamName.updateFirst();
        String[] nameSplitAfter = teamName.toString().split(" ");

        assertNotEquals(nameSplitBefore[0], nameSplitAfter[0]);
        assertEquals(nameSplitBefore[1], nameSplitAfter[1]);
    }

    @Test
    void updateSecond() {
        String[] nameSplitBefore = teamName.toString().split(" ");
        teamName.updateSecond();
        String[] nameSplitAfter = teamName.toString().split(" ");

        assertEquals(nameSplitBefore[0], nameSplitAfter[0]);
        assertNotEquals(nameSplitBefore[1], nameSplitAfter[1]);
    }

    @AfterAll
    static void tearDown() {
        fxgl.close();
    }
}
