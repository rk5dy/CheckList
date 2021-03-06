package cs4720.cs.virginia.edu.checklist;

import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

public class UploadFileToDropbox extends AsyncTask<Void, Void, Boolean> {

    private DropboxAPI<?> dropbox;
    private String path;
    private Context context;
    private String fromFile;
    private String theName;
    public UploadFileToDropbox(Context context, DropboxAPI<?> dropbox,
                               String path, String fromFile, String fileName) {
        this.context = context.getApplicationContext();
        this.dropbox = dropbox;
        this.path = path;
        this.fromFile = fromFile;
        theName = fileName;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final File tempDir = context.getCacheDir();
        File tempFile;
        FileWriter fr;
        try {
            tempFile = File.createTempFile("file", ".txt", tempDir);
            fr = new FileWriter(tempFile);
            fr.write(fromFile);
            fr.close();

            FileInputStream fileInputStream = new FileInputStream(tempFile);
            dropbox.putFileOverwrite(path + theName + ".txt", fileInputStream,
                    tempFile.length(), null);
            tempFile.delete();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DropboxException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {

    }
}
