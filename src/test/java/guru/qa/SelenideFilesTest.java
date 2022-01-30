package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFilesTest {

    private ClassLoader cl = SelenideFilesTest.class.getClassLoader();

    @Test
    void downloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/LICENSE.md");
        File downloadedFile = Selenide.$("#raw-url").download();
        try (InputStream is = new FileInputStream(downloadedFile)) {
            assertThat(new String(is.readAllBytes(), StandardCharsets.UTF_8))
                    .contains("Eclipse Public License - v 2.0");
        }
    }

    @Test
    void  uploadFile() {
        Selenide.open("https://the-internet.herokuapp.com/upload");
        Selenide.$("input[type='file']").uploadFromClasspath("files/upload.txt");
        Selenide.$("#file-submit").click();
        Selenide.$("#uploaded-files").shouldHave(Condition.text("upload.txt"));
    }
}
