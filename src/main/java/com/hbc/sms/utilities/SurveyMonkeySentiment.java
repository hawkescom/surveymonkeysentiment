package com.hbc.sms.utilities;


import com.hbc.sms.adapters.CognitiveServicesAdapter;
import com.hbc.sms.cognitiveservices.DocumentsPayload;
import com.hbc.sms.cognitiveservices.DocumentsRequest;
import com.hbc.sms.cognitiveservices.DocumentsResponse;
import com.hbc.sms.surveymonkey.SurveyQuestion;
import com.hbc.sms.surveymonkey.SurveyResponses;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SurveyMonkeySentiment {

    private String token;
    private String url;
    private String inputFile;
    private String outputFile;
    private int numOfQuestions;

    public SurveyMonkeySentiment(String[] args) {
        if (args.length == 5) {
            this.token = args[0];
            this.url = args[1];
            this.inputFile = args[2];
            this.outputFile = args[3];
            this.numOfQuestions = Integer.parseInt(args[4]);
        } else {
            System.err.println("This tool requires the following parameters: \n" +
                                "- token (Token used for Cognitive Services)\n" +
                                "- url (Url to Call for Sentiment API)\n" +
                                "- inputFile (File Path for Input from Survey Monkey\n" +
                                "- ouputFile (File Path for .xslx to Output\n" +
                                "-numberOfQuestions (Number of Survey Questions to analyze)");
        }
    }

        public void parseSurveyMonkeyXLSX() {
            CognitiveServicesAdapter csa = new CognitiveServicesAdapter(this.token, this.url);
            List<SurveyResponses> responsesList = processResponses(this.inputFile, csa);
            System.out.println("Number of responses -> " + responsesList.size());
            buildSentimentOutput(this.outputFile, responsesList, this.numOfQuestions);
        }



    private static List<SurveyResponses> processInputFile(String inputFilePath, int questionNumber, CognitiveServicesAdapter csa) {

        List<SurveyResponses> responses = new LinkedList<>();
        // reading csv file into stream, try-with-resources
        try {

            Stream<String> lineOne = Files.lines(Paths.get(inputFilePath));
            String[] questionsList = lineOne.map(s -> s.split(","))
                    .findFirst()
                    .get();

            Stream<String> lines = Files.lines(Paths.get(inputFilePath));
            responses = lines.map(temp -> {
                String[] answerList = temp.split(",");

                LinkedList<SurveyQuestion> sqList = new LinkedList<>();
                for (int i = 1; i < questionNumber + 2; i++) {

                    List<DocumentsPayload> dpList = new LinkedList<>();
                    DocumentsPayload dp = new DocumentsPayload("" + i, answerList[i], "en");
                    dpList.add(dp);
                    System.out.println(dp.toString());
                    String sentiment = "";
                    try {

                        if (answerList[i].trim().length() > 0) {
                            DocumentsResponse dr = csa.getSentiment("/sentiment", new DocumentsRequest(dpList));
                            sentiment = dr.getDocuments().get(0).getScore();
                        }

                        SurveyQuestion sq = new SurveyQuestion(i, answerList[i], sentiment);

                        sqList.add(sq);
                    } catch (IOException ioe2) {
                        ioe2.printStackTrace();
                    }
                }
                return new SurveyResponses(answerList[0], Arrays.asList(questionsList), sqList);
            }).collect(Collectors.toList());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return responses;
    }

    public static List<String> parseHeader (Iterator<Cell> cellIterator){

        List<String> questionList = new LinkedList<>();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getColumnIndex() > 0) {
                questionList.add(cell.getStringCellValue());
            }
        }
        return questionList;

    }

    public static SurveyResponses parseRow (Iterator<Cell> cellIterator, CognitiveServicesAdapter csa){

        SurveyResponses response = null;
        LinkedList<SurveyQuestion> sqList = new LinkedList<>();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            if (cell.getColumnIndex() == 0) {
                response = new SurveyResponses(cell.getNumericCellValue() + "",null,null);
            } else {

                List<DocumentsPayload> dpList = new LinkedList<>();
                DocumentsPayload dp = new DocumentsPayload(cell.getColumnIndex() + "" , cell.getStringCellValue(), "en");
                dpList.add(dp);
                String sentiment = "";
                try {

                    DocumentsResponse dr = csa.getSentiment("/sentiment", new DocumentsRequest(dpList));
                    sentiment = dr.getDocuments().get(0).getScore();
                    SurveyQuestion sq = new SurveyQuestion(cell.getColumnIndex()  , cell.getStringCellValue(), sentiment);

                    sqList.add(sq);
                } catch (IOException ioe2) {
                    ioe2.printStackTrace();
                }

            }
            response.setResponses(sqList);
        }


        return response;

    }

    public static List<SurveyResponses> processResponses(String inputFilePath, CognitiveServicesAdapter csa) {
        List<SurveyResponses> responseList = new LinkedList<>();

        try {
            FileInputStream fis = new FileInputStream(inputFilePath);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIterator = mySheet.iterator();

            List<String> questionList = new LinkedList<>();
            LinkedList<SurveyQuestion> sqList = new LinkedList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                if (row.getRowNum() == 0) {
                    questionList = parseHeader(cellIterator);
                } else {
                    SurveyResponses response = parseRow(cellIterator,csa);
                    response.setQuestions(questionList);
                    responseList.add(response);
                }

            }
        }catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return responseList;
    }

    public static void buildSentimentOutput(String outputFilePath, List<SurveyResponses> responsesList, int numberOfQuestions) {

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Sentiment");

        try {
            OutputStream fileOut = new FileOutputStream(outputFilePath);
            CreationHelper createHelper = wb.getCreationHelper();

            // Create a row and put some cells in it. Rows are 0 based.
            XSSFRow row = sheet.createRow(0);
            // Create a cell and put a value in it.
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Respondent");

            for (int i=0; i < numberOfQuestions; i++) {
                cell = row.createCell(i+1);
                cell.setCellValue("Question " + (i+1));
            }

            int rowCtr = 1;
            for (SurveyResponses surveyResponses : responsesList) {
                XSSFRow dataRow = sheet.createRow(rowCtr);
                List<SurveyQuestion> questionList = surveyResponses.getResponses();

                dataRow.createCell(0).setCellValue(surveyResponses.getId());
                for (SurveyQuestion q : questionList) {
                        dataRow.createCell(q.getQuestionNumber()).setCellValue(q.getSentiment());
                }

                rowCtr++;
            }

            wb.write(fileOut);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        responsesList.forEach(response -> {
            response.getResponses().forEach(sq-> {
                System.out.println(sq.toString());
            });
        });
    }

    public static void main(String[] args) throws Exception {

        String[] params = new String[] {"f74cb4ad728d46d10227bd11b9a0e92a6500",
                "https://westus.api.cognitive.microsoft.com/text/analytics/v2.0",
                "/Users/matthawkes/Matt2019Director360.xlsx",
                "/Users/matthawkes/Matt2019Director360WithSentiment.xlsx",
                "2"};

        SurveyMonkeySentiment sms = new SurveyMonkeySentiment(params);
        sms.parseSurveyMonkeyXLSX();
    }
}
