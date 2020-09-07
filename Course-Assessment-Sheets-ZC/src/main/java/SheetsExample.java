import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AddNamedRangeRequest;
import com.google.api.services.sheets.v4.model.AddSheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.NamedRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateSpreadsheetPropertiesRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SheetsExample {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(new String[] {"https://www.googleapis.com/auth/drive"});
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  public static void main(String args[]) throws IOException, GeneralSecurityException {
    // The spreadsheet to apply the updates to.
    final String spreadsheetId = "1Mbd4aFcmZzf_77NaePsdIB_W9Z2dBJJU4DZzykXLTjY"; // TODO: Update placeholder value.

    // A list of updates to apply to the spreadsheet.
    // Requests will be applied in the order they are specified.
    // If any request is not valid, no requests will be applied.
    AddSheetRequest myReq = new AddSheetRequest().setProperties(new SheetProperties());
    List<Request> requests = new ArrayList<>(); // TODO: Update placeholder value.
    requests.add(new Request().setAddSheet(myReq));
    requests.add(new Request()
            .setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
                    .setProperties(new SpreadsheetProperties()
                            .setTitle("BTATES"))
                    .setFields("title")));
   AddNamedRangeRequest namedreq = new AddNamedRangeRequest().setNamedRange(new NamedRange().setName("thetheRange")
   		.setRange(new GridRange().setSheetId(1367432319).setStartRowIndex(0).
				setEndRowIndex(2).setStartColumnIndex(0).setEndColumnIndex(2)));
    requests.add(new Request().setAddNamedRange(namedreq));

    // TODO: Assign values to desired fields of `requestBody`:
    BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest();
    requestBody.setRequests(requests);

    Sheets sheetsService = createSheetsService();
    BatchUpdateSpreadsheetResponse response =
            sheetsService.spreadsheets().batchUpdate(spreadsheetId, requestBody).execute();
    

//    BatchUpdateSpreadsheetResponse response = request.execute();

    // TODO: Change code below to process the `response` object:
    System.out.println(response);
  }

  public static Sheets createSheetsService() throws IOException, GeneralSecurityException {
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    Credential credential = SheetsTest.getCredentials(httpTransport);
    return new Sheets.Builder(httpTransport, jsonFactory, credential)
        .setApplicationName("Google-SheetsSample/0.1")
        .build();
  }
  
}