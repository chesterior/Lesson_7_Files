package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class zipFileTest {

    @Test
    void zipFile() throws Exception {
        ZipFile zf = new ZipFile("src/test/resources/files/homework_file.zip");

        ZipEntry zipEntryCsv = zf.getEntry("example.csv");
        try (InputStream inputStream = zf.getInputStream(zipEntryCsv)) {
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            List<String[]> list = reader.readAll();
            assertThat(list)
                    .hasSize(3)
                    .contains(
                            new String[] {"Author", "Book"},
                            new String[] {"Block", "Apteka"},
                            new String[] {"Esenin", "Cherniy Chelovek"}
                    );
        }

        ZipEntry zipEntryPdf = zf.getEntry("LICENSE.md.pdf");
        try (InputStream pdfStream = zf.getInputStream(zipEntryPdf)) {
            PDF parsed = new PDF(pdfStream);
            assertThat(parsed.text).contains("Eclipse Public License - v 2.0");
        }

        ZipEntry zipEntryXls = zf.getEntry("sample-xlsx-file.xls");
        try (InputStream pdfStream = zf.getInputStream(zipEntryXls)) {
            XLS parsed = new XLS(pdfStream);
            assertThat(parsed.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue())
                    .isEqualTo("БЮДЖЕТ-СЕМЬИ.РФ");
        }
    }
}
