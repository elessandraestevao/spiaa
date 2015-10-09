package com.spiaa.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by eless on 08/10/2015.
 */
public class Task extends AsyncTask<Void, Void, Void> {
    ProgressDialog progress;

    public Task(ProgressDialog progress) {
        this.progress = progress;
    }

    public void onPreExecute() {
        progress.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
         progress.getProgress();
        return null;
    }

    public void onPostExecute(Void unused) {
        progress.dismiss();
    }
}
