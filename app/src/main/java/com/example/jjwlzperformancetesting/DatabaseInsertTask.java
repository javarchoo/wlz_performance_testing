package com.example.jjwlzperformancetesting;

import android.content.ContentValues;
import android.os.AsyncTask;

public class DatabaseInsertTask extends AsyncTask<Void, Void, String> {

    private String url;
    private ContentValues values;

    public DatabaseInsertTask(String url, ContentValues values) {

        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... params) {

        String result; // 요청 결과를 저장할 변수.
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@@@@@@@@@@@" +  s);
    }
}
