package com.blessedenterprises.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.blessedenterprises.R;
import com.blessedenterprises.dbhandlers.MyDBHandler;
import com.blessedenterprises.models.User;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import jxl.write.Number;

/**
 * Created by stephnoutsa on 9/27/16.
 */
public class WriteExcel {

    Context context;
    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private String inputFile;
    MyDBHandler dbHandler;
    List<User> users = new ArrayList<>();
    String rootDir = "Blessed Enterprise";
    String directory = "Excel Files";
    Date date;

    public WriteExcel() {

    }

    public void setOutputFile(Context context) {
        this.context = context;
        dbHandler = new MyDBHandler(context, null, null, 1);
        date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss_a", Locale.ENGLISH);
        String time = df.format(date.getTime());
        this.inputFile = "log_" + time + ".xls";
    }

    public void write() throws IOException, WriteException {
        File docsDir = Environment.getExternalStoragePublicDirectory(rootDir);
        File path = new File(docsDir, directory);
        path.mkdirs();
        File file = new File(path, inputFile);

        WorkbookSettings settings = new WorkbookSettings();
        settings.setLocale(Locale.ENGLISH);

        WritableWorkbook workbook = Workbook.createWorkbook(file, settings);
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss_a", Locale.ENGLISH);
        String time = df.format(date.getTime());
        String title = "log_" + time;
        workbook.createSheet(title, 0);

        WritableSheet sheet = workbook.getSheet(0);

        createLabel(sheet);
        boolean created = createContent(sheet);

        if (created) {
            workbook.write();
            Toast.makeText(context, context.getString(R.string.generate_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.generate_failed), Toast.LENGTH_SHORT).show();
        }

        workbook.close();
    }

    private void createLabel(WritableSheet sheet) throws WriteException {
        // Create a Times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);

        // Define cell format
        times = new WritableCellFormat(times10pt);

        // Automatically wrap the cells
        times.setWrap(true);

        // Create bold font with underlines
        WritableFont times10ptBU = new WritableFont(
                WritableFont.TIMES,
                10,
                WritableFont.BOLD,
                false,
                UnderlineStyle.NO_UNDERLINE
        );
        timesBoldUnderline = new WritableCellFormat(times10ptBU);

        // Automatically wrap bold cells
        timesBoldUnderline.setWrap(true);

        CellView cellView = new CellView();
        cellView.setFormat(times);
        cellView.setFormat(timesBoldUnderline);
        cellView.setAutosize(true);

        // Give headers
        addCaption(sheet, 0, 0, "SN");
        addCaption(sheet, 1, 0, "Name");
        addCaption(sheet, 2, 0, "Date");
        addCaption(sheet, 3, 0, "Login Time");
        addCaption(sheet, 4, 0, "Logout Time");
        addCaption(sheet, 5, 0, "Session Length");
        addCaption(sheet, 6, 0, "Host");
        addCaption(sheet, 7, 0, "Line");
    }

    private boolean createContent(WritableSheet sheet) throws WriteException, RowsExceededException {
        boolean ok = true;

        // Populate sheet
        users = dbHandler.getAllUsers();
        if (users.size() > 1) {
            users.remove(0);
            int sn;
            String name, date, login, logout, session = "", host, line;
            Date loginTime, logoutTime;
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH);
            for (int i = 0; i < users.size(); i++) {
                sn = i + 1;
                name = users.get(i).getName();
                date = users.get(i).getDate();
                login = users.get(i).getLoginTime();
                logout = users.get(i).getLogoutTime();
                host = users.get(i).getHost();
                line = users.get(i).getLine();

                if (logout.equals("Still active")) {
                    try {
                        Date now = new Date();
                        String nowTime = df.format(now);
                        Date time = df.parse(nowTime);
                        loginTime = df.parse(login);
                        long diffInMs = time.getTime() - loginTime.getTime();
                        long diffMin = TimeUnit.MILLISECONDS.toMinutes(diffInMs);
                        long diffHr = TimeUnit.MILLISECONDS.toHours(diffInMs);

                        if (diffMin < 1) {
                            session = "Less than a minute";
                        } else if (diffMin >= 1 && diffHr < 1) {
                            if (diffMin < 2) {
                                session = String.valueOf(diffMin) + " min";
                            } else {
                                session = String.valueOf(diffMin) + " mins";
                            }
                        } else if (diffHr >= 1) {
                            if (diffHr < 2) {
                                session = String.valueOf(diffHr) + " hr";
                            } else {
                                session = String.valueOf(diffHr) + " hrs";
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        loginTime = df.parse(login);
                        logoutTime = df.parse(logout);
                        long diffInMs = logoutTime.getTime() - loginTime.getTime();
                        long diffMin = diffInMs / (1000 * 60);
                        long diffHr = diffMin / 60;

                        if (diffMin < 1) {
                            session = "Less than a minute";
                        } else if (diffMin >= 1 && diffHr < 1) {
                            if (diffMin < 2) {
                                session = String.valueOf(diffMin) + " min";
                            } else {
                                session = String.valueOf(diffMin) + " mins";
                            }
                        } else if (diffHr >= 1) {
                            if (diffHr < 2) {
                                session = String.valueOf(diffHr) + " hr";
                            } else {
                                session = String.valueOf(diffHr) + " hrs";
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                addLabel(sheet, 0, i + 1, String.valueOf(sn));
                addLabel(sheet, 1, i + 1, name);
                addLabel(sheet, 2, i + 1, date);
                addLabel(sheet, 3, i + 1, login);
                addLabel(sheet, 4, i + 1, logout);
                addLabel(sheet, 5, i + 1, session);
                addLabel(sheet, 6, i + 1, host);
                addLabel(sheet, 7, i + 1, line);
            }
        } else {
            ok = false;
        }

        return ok;
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
        throws WriteException, RowsExceededException {
        Label label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row, Integer integer)
        throws WriteException, RowsExceededException {
        Number number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
        throws WriteException, RowsExceededException {
        Label label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

}
