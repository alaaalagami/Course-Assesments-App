import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AddSheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.CreateDeveloperMetadataRequest;
import com.google.api.services.sheets.v4.model.DeveloperMetadata;
import com.google.api.services.sheets.v4.model.DuplicateSheetRequest;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SheetsAPI {
    private static final String APPLICATION_NAME = "Google Sheets API Java";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String SPREADSHEAT_ID = "1Mbd4aFcmZzf_77NaePsdIB_W9Z2dBJJU4DZzykXLTjY";
    private static Sheets SHEETS_SERVICE;
    
    SheetsAPI() throws IOException, GeneralSecurityException {
    	createSheetsService();
    }


    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(new String[] {"https://www.googleapis.com/auth/drive"});
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsTest.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    public static void createSheetsService() throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = SheetsTest.getCredentials(httpTransport);
        SHEETS_SERVICE = new Sheets.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
      }
    
    private static void PostRequest(Request request) throws IOException, GeneralSecurityException {
        BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest();
        requestBody.setRequests(Collections.singletonList(request));
        SHEETS_SERVICE.spreadsheets().batchUpdate(SPREADSHEAT_ID, requestBody).execute();
    }
    
    public int getNumberOfCourses() throws IOException {
    	return (int) SHEETS_SERVICE.spreadsheets().values().get(SPREADSHEAT_ID, "NumberCourses").execute().getValues().get(0).get(0);
    }
    
    public void CreateNewSheet(String courseID) throws IOException, GeneralSecurityException {
  //  	int NumCourses = getNumberOfCourses();
        DuplicateSheetRequest myReq = new DuplicateSheetRequest().setSourceSheetId(0)
        		.setNewSheetName(courseID).setInsertSheetIndex(2);
        		//.setNewSheetId(NumCourses + 1);
        Request rq = new Request().setDuplicateSheet(myReq);
        PostRequest(rq);

        //Sets the CourseID cell
    	List<List<Object>> values = Arrays.asList(Arrays.asList(courseID));
    	ValueRange body = new ValueRange().setValues(values);
    	SHEETS_SERVICE.spreadsheets().values().update(SPREADSHEAT_ID, courseID + "!CourseID", body)
    	.setValueInputOption("RAW").execute();
    }
    
    public void setInstructorUsername(String username, String courseID) throws IOException, GeneralSecurityException {
    	List<List<Object>> values = Arrays.asList(Arrays.asList(username));
    	ValueRange body = new ValueRange().setValues(values);
    	SHEETS_SERVICE.spreadsheets().values().update(SPREADSHEAT_ID, courseID + "!InstructorUsername", body)
    	.setValueInputOption("RAW").execute();
    }
    
    public static List<String> getCoursesByUsername(String username) throws IOException {
       	Spreadsheet spread = SHEETS_SERVICE.spreadsheets().get(SPREADSHEAT_ID).execute();
    	List<Sheet> sheets = spread.getSheets();
    	List<String> courses = new ArrayList<String>();
    	for (Sheet sheet : sheets) {
            ValueRange response = SHEETS_SERVICE.spreadsheets().values()
                    .get(SPREADSHEAT_ID, sheet.getProperties().getTitle() + "!InstructorUsername")
                    .execute();
    		if (response.getValues().get(0).get(0).toString().equals(username)) {
    			courses.add(sheet.getProperties().getTitle());
    		}
    	}
    	return courses;
    }
    
    public static void main(String... args) throws IOException, GeneralSecurityException {
    	createSheetsService();
        System.out.println(getCoursesByUsername("moemen"));
    }
    
    
}