package com.example.jjwlzperformancetesting;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DBInsertTask extends AsyncTask<String, Void, String> {
//    ProgressDialog loading;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("@@@@@@@@@" + s);
//        loading.dismiss();
        //Log.d("Tag : ", s); // php에서 가져온 값을 최종 출력함
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
    @Override
    protected String doInBackground(String... params) {
        System.out.println("@@ Start Method: doInBackground");

        String link = "http://13.125.28.176/wlz/GArWGDtGIlyOIhSNlDKp/insertData.php";

        try {
            String pr1 = (String) params[0];
            String pr2 = (String) params[1];
            String pr3 = (String) params[2];
            String pr4 = (String) params[3];
            String pr5 = (String) params[4];
            String pr6 = (String) params[5];
            String pr7 = (String) params[6];
            String pr8 = (String) params[7];
            String pr9 = (String) params[8];
            String pr10 = (String) params[9];
            String pr11 = (String) params[10];
            String pr12 = (String) params[11];
            String pr13 = (String) params[12];
            String pr14 = (String) params[13];
            String pr15 = (String) params[14];
            String pr16 = (String) params[15];
            String pr17 = (String) params[16];
            String pr18 = (String) params[17];
            String pr19 = (String) params[18];
            String pr20 = (String) params[19];
            String pr21 = (String) params[20];
            String pr22 = (String) params[21];
            String pr23 = (String) params[22];
            String pr24 = (String) params[23];
            String pr25 = (String) params[24];
            String pr26 = (String) params[25];
            String pr27 = (String) params[26];
            String pr28 = (String) params[27];
            String pr29 = (String) params[28];
            String pr30 = (String) params[29];
            String pr31 = (String) params[30];
            String pr32 = (String) params[31];
            String pr33 = (String) params[32];
            String pr34 = (String) params[33];
            String pr35 = (String) params[34];
            String pr36 = (String) params[35];
            String pr37 = (String) params[36];
            String pr38 = (String) params[37];
            String pr39 = (String) params[38];
            String pr40 = (String) params[39];
            String pr41 = (String) params[40];
            String pr42 = (String) params[41];
            String pr43 = (String) params[42];
            String pr44 = (String) params[43];
            String pr45 = (String) params[44];
            String pr46 = (String) params[45];
            String pr47 = (String) params[46];
            String pr48 = (String) params[47];
            String pr49 = (String) params[48];
            String pr50 = (String) params[49];
            String pr51 = (String) params[50];
            String pr52 = (String) params[51];
            String pr53 = (String) params[52];
            String pr54 = (String) params[53];
            String pr55 = (String) params[54];
            String pr56 = (String) params[55];
            String pr57 = (String) params[56];
            String pr58 = (String) params[57];
            String pr59 = (String) params[58];
            String pr60 = (String) params[59];
            String pr61 = (String) params[60];
            String pr62 = (String) params[61];
            String pr63 = (String) params[62];
            String pr64 = (String) params[63];
            String pr65 = (String) params[64];
            String pr66 = (String) params[65];
            String pr67 = (String) params[66];
            String pr68 = (String) params[67];
            String pr69 = (String) params[68];
            String pr70 = (String) params[69];
            String pr71 = (String) params[70];
            String pr72 = (String) params[71];
            String pr73 = (String) params[72];
            String pr74 = (String) params[73];
            String pr75 = (String) params[74];
            String pr76 = (String) params[75];
            String pr77 = (String) params[76];
            String pr78 = (String) params[77];
            String pr79 = (String) params[78];
            String pr80 = (String) params[79];
            String pr81 = (String) params[80];
            String pr82 = (String) params[81];
            String pr83 = (String) params[82];
            String pr84 = (String) params[83];
            String pr85 = (String) params[84];
            String pr86 = (String) params[85];
            String pr87 = (String) params[86];
            String pr88 = (String) params[87];
            String pr89 = (String) params[88];
            String pr90 = (String) params[89];
            String pr91 = (String) params[90];
            String pr92 = (String) params[91];
            String pr93 = (String) params[92];
            String pr94 = (String) params[93];
            String pr95 = (String) params[94];
            String pr96 = (String) params[95];
            String pr97 = (String) params[96];
            String pr98 = (String) params[97];
            String pr99 = (String) params[98];
            String pr100 = (String) params[99];
            String pr101 = (String) params[100];
            String pr102 = (String) params[101];
            String pr103 = (String) params[102];
            String pr104 = (String) params[103];
            String pr105 = (String) params[104];
            String pr106 = (String) params[105];
            String pr107 = (String) params[106];
            String pr108 = (String) params[107];
            String pr109 = (String) params[108];
            String pr110 = (String) params[109];
            String pr111 = (String) params[110];
            String pr112 = (String) params[111];
            String pr113 = (String) params[112];
            String pr114 = (String) params[113];
            String pr115 = (String) params[114];
            String pr116 = (String) params[115];
            String pr117 = (String) params[116];
            String pr118 = (String) params[117];
            String pr119 = (String) params[118];
            String pr120 = (String) params[119];

            String data = URLEncoder.encode("pr1", "UTF-8") + "=" + URLEncoder.encode(pr1, "UTF-8");
            data += "&" + URLEncoder.encode("pr2", "UTF-8") + "=" + URLEncoder.encode(pr2, "UTF-8");
            data += "&" + URLEncoder.encode("pr3", "UTF-8") + "=" + URLEncoder.encode(pr3, "UTF-8");
            data += "&" + URLEncoder.encode("pr4", "UTF-8") + "=" + URLEncoder.encode(pr4, "UTF-8");
            data += "&" + URLEncoder.encode("pr5", "UTF-8") + "=" + URLEncoder.encode(pr5, "UTF-8");
            data += "&" + URLEncoder.encode("pr6", "UTF-8") + "=" + URLEncoder.encode(pr6, "UTF-8");
            data += "&" + URLEncoder.encode("pr7", "UTF-8") + "=" + URLEncoder.encode(pr7, "UTF-8");
            data += "&" + URLEncoder.encode("pr8", "UTF-8") + "=" + URLEncoder.encode(pr8, "UTF-8");
            data += "&" + URLEncoder.encode("pr9", "UTF-8") + "=" + URLEncoder.encode(pr9, "UTF-8");
            data += "&" + URLEncoder.encode("pr10", "UTF-8") + "=" + URLEncoder.encode(pr10, "UTF-8");
            data += "&" + URLEncoder.encode("pr11", "UTF-8") + "=" + URLEncoder.encode(pr11, "UTF-8");
            data += "&" + URLEncoder.encode("pr12", "UTF-8") + "=" + URLEncoder.encode(pr12, "UTF-8");
            data += "&" + URLEncoder.encode("pr13", "UTF-8") + "=" + URLEncoder.encode(pr13, "UTF-8");
            data += "&" + URLEncoder.encode("pr14", "UTF-8") + "=" + URLEncoder.encode(pr14, "UTF-8");
            data += "&" + URLEncoder.encode("pr15", "UTF-8") + "=" + URLEncoder.encode(pr15, "UTF-8");
            data += "&" + URLEncoder.encode("pr16", "UTF-8") + "=" + URLEncoder.encode(pr16, "UTF-8");
            data += "&" + URLEncoder.encode("pr17", "UTF-8") + "=" + URLEncoder.encode(pr17, "UTF-8");
            data += "&" + URLEncoder.encode("pr18", "UTF-8") + "=" + URLEncoder.encode(pr18, "UTF-8");
            data += "&" + URLEncoder.encode("pr19", "UTF-8") + "=" + URLEncoder.encode(pr19, "UTF-8");
            data += "&" + URLEncoder.encode("pr20", "UTF-8") + "=" + URLEncoder.encode(pr20, "UTF-8");
            data += "&" + URLEncoder.encode("pr21", "UTF-8") + "=" + URLEncoder.encode(pr21, "UTF-8");
            data += "&" + URLEncoder.encode("pr22", "UTF-8") + "=" + URLEncoder.encode(pr22, "UTF-8");
            data += "&" + URLEncoder.encode("pr23", "UTF-8") + "=" + URLEncoder.encode(pr23, "UTF-8");
            data += "&" + URLEncoder.encode("pr24", "UTF-8") + "=" + URLEncoder.encode(pr24, "UTF-8");
            data += "&" + URLEncoder.encode("pr25", "UTF-8") + "=" + URLEncoder.encode(pr25, "UTF-8");
            data += "&" + URLEncoder.encode("pr26", "UTF-8") + "=" + URLEncoder.encode(pr26, "UTF-8");
            data += "&" + URLEncoder.encode("pr27", "UTF-8") + "=" + URLEncoder.encode(pr27, "UTF-8");
            data += "&" + URLEncoder.encode("pr28", "UTF-8") + "=" + URLEncoder.encode(pr28, "UTF-8");
            data += "&" + URLEncoder.encode("pr29", "UTF-8") + "=" + URLEncoder.encode(pr29, "UTF-8");
            data += "&" + URLEncoder.encode("pr30", "UTF-8") + "=" + URLEncoder.encode(pr30, "UTF-8");
            data += "&" + URLEncoder.encode("pr31", "UTF-8") + "=" + URLEncoder.encode(pr31, "UTF-8");
            data += "&" + URLEncoder.encode("pr32", "UTF-8") + "=" + URLEncoder.encode(pr32, "UTF-8");
            data += "&" + URLEncoder.encode("pr33", "UTF-8") + "=" + URLEncoder.encode(pr33, "UTF-8");
            data += "&" + URLEncoder.encode("pr34", "UTF-8") + "=" + URLEncoder.encode(pr34, "UTF-8");
            data += "&" + URLEncoder.encode("pr35", "UTF-8") + "=" + URLEncoder.encode(pr35, "UTF-8");
            data += "&" + URLEncoder.encode("pr36", "UTF-8") + "=" + URLEncoder.encode(pr36, "UTF-8");
            data += "&" + URLEncoder.encode("pr37", "UTF-8") + "=" + URLEncoder.encode(pr37, "UTF-8");
            data += "&" + URLEncoder.encode("pr38", "UTF-8") + "=" + URLEncoder.encode(pr38, "UTF-8");
            data += "&" + URLEncoder.encode("pr39", "UTF-8") + "=" + URLEncoder.encode(pr39, "UTF-8");
            data += "&" + URLEncoder.encode("pr40", "UTF-8") + "=" + URLEncoder.encode(pr40, "UTF-8");
            data += "&" + URLEncoder.encode("pr41", "UTF-8") + "=" + URLEncoder.encode(pr41, "UTF-8");
            data += "&" + URLEncoder.encode("pr42", "UTF-8") + "=" + URLEncoder.encode(pr42, "UTF-8");
            data += "&" + URLEncoder.encode("pr43", "UTF-8") + "=" + URLEncoder.encode(pr43, "UTF-8");
            data += "&" + URLEncoder.encode("pr44", "UTF-8") + "=" + URLEncoder.encode(pr44, "UTF-8");
            data += "&" + URLEncoder.encode("pr45", "UTF-8") + "=" + URLEncoder.encode(pr45, "UTF-8");
            data += "&" + URLEncoder.encode("pr46", "UTF-8") + "=" + URLEncoder.encode(pr46, "UTF-8");
            data += "&" + URLEncoder.encode("pr47", "UTF-8") + "=" + URLEncoder.encode(pr47, "UTF-8");
            data += "&" + URLEncoder.encode("pr48", "UTF-8") + "=" + URLEncoder.encode(pr48, "UTF-8");
            data += "&" + URLEncoder.encode("pr49", "UTF-8") + "=" + URLEncoder.encode(pr49, "UTF-8");
            data += "&" + URLEncoder.encode("pr50", "UTF-8") + "=" + URLEncoder.encode(pr50, "UTF-8");
            data += "&" + URLEncoder.encode("pr51", "UTF-8") + "=" + URLEncoder.encode(pr51, "UTF-8");
            data += "&" + URLEncoder.encode("pr52", "UTF-8") + "=" + URLEncoder.encode(pr52, "UTF-8");
            data += "&" + URLEncoder.encode("pr53", "UTF-8") + "=" + URLEncoder.encode(pr53, "UTF-8");
            data += "&" + URLEncoder.encode("pr54", "UTF-8") + "=" + URLEncoder.encode(pr54, "UTF-8");
            data += "&" + URLEncoder.encode("pr55", "UTF-8") + "=" + URLEncoder.encode(pr55, "UTF-8");
            data += "&" + URLEncoder.encode("pr56", "UTF-8") + "=" + URLEncoder.encode(pr56, "UTF-8");
            data += "&" + URLEncoder.encode("pr57", "UTF-8") + "=" + URLEncoder.encode(pr57, "UTF-8");
            data += "&" + URLEncoder.encode("pr58", "UTF-8") + "=" + URLEncoder.encode(pr58, "UTF-8");
            data += "&" + URLEncoder.encode("pr59", "UTF-8") + "=" + URLEncoder.encode(pr59, "UTF-8");
            data += "&" + URLEncoder.encode("pr60", "UTF-8") + "=" + URLEncoder.encode(pr60, "UTF-8");
            data += "&" + URLEncoder.encode("pr61", "UTF-8") + "=" + URLEncoder.encode(pr61, "UTF-8");
            data += "&" + URLEncoder.encode("pr62", "UTF-8") + "=" + URLEncoder.encode(pr62, "UTF-8");
            data += "&" + URLEncoder.encode("pr63", "UTF-8") + "=" + URLEncoder.encode(pr63, "UTF-8");
            data += "&" + URLEncoder.encode("pr64", "UTF-8") + "=" + URLEncoder.encode(pr64, "UTF-8");
            data += "&" + URLEncoder.encode("pr65", "UTF-8") + "=" + URLEncoder.encode(pr65, "UTF-8");
            data += "&" + URLEncoder.encode("pr66", "UTF-8") + "=" + URLEncoder.encode(pr66, "UTF-8");
            data += "&" + URLEncoder.encode("pr67", "UTF-8") + "=" + URLEncoder.encode(pr67, "UTF-8");
            data += "&" + URLEncoder.encode("pr68", "UTF-8") + "=" + URLEncoder.encode(pr68, "UTF-8");
            data += "&" + URLEncoder.encode("pr69", "UTF-8") + "=" + URLEncoder.encode(pr69, "UTF-8");
            data += "&" + URLEncoder.encode("pr70", "UTF-8") + "=" + URLEncoder.encode(pr70, "UTF-8");
            data += "&" + URLEncoder.encode("pr71", "UTF-8") + "=" + URLEncoder.encode(pr71, "UTF-8");
            data += "&" + URLEncoder.encode("pr72", "UTF-8") + "=" + URLEncoder.encode(pr72, "UTF-8");
            data += "&" + URLEncoder.encode("pr73", "UTF-8") + "=" + URLEncoder.encode(pr73, "UTF-8");
            data += "&" + URLEncoder.encode("pr74", "UTF-8") + "=" + URLEncoder.encode(pr74, "UTF-8");
            data += "&" + URLEncoder.encode("pr75", "UTF-8") + "=" + URLEncoder.encode(pr75, "UTF-8");
            data += "&" + URLEncoder.encode("pr76", "UTF-8") + "=" + URLEncoder.encode(pr76, "UTF-8");
            data += "&" + URLEncoder.encode("pr77", "UTF-8") + "=" + URLEncoder.encode(pr77, "UTF-8");
            data += "&" + URLEncoder.encode("pr78", "UTF-8") + "=" + URLEncoder.encode(pr78, "UTF-8");
            data += "&" + URLEncoder.encode("pr79", "UTF-8") + "=" + URLEncoder.encode(pr79, "UTF-8");
            data += "&" + URLEncoder.encode("pr80", "UTF-8") + "=" + URLEncoder.encode(pr80, "UTF-8");
            data += "&" + URLEncoder.encode("pr81", "UTF-8") + "=" + URLEncoder.encode(pr81, "UTF-8");
            data += "&" + URLEncoder.encode("pr82", "UTF-8") + "=" + URLEncoder.encode(pr82, "UTF-8");
            data += "&" + URLEncoder.encode("pr83", "UTF-8") + "=" + URLEncoder.encode(pr83, "UTF-8");
            data += "&" + URLEncoder.encode("pr84", "UTF-8") + "=" + URLEncoder.encode(pr84, "UTF-8");
            data += "&" + URLEncoder.encode("pr85", "UTF-8") + "=" + URLEncoder.encode(pr85, "UTF-8");
            data += "&" + URLEncoder.encode("pr86", "UTF-8") + "=" + URLEncoder.encode(pr86, "UTF-8");
            data += "&" + URLEncoder.encode("pr87", "UTF-8") + "=" + URLEncoder.encode(pr87, "UTF-8");
            data += "&" + URLEncoder.encode("pr88", "UTF-8") + "=" + URLEncoder.encode(pr88, "UTF-8");
            data += "&" + URLEncoder.encode("pr89", "UTF-8") + "=" + URLEncoder.encode(pr89, "UTF-8");
            data += "&" + URLEncoder.encode("pr90", "UTF-8") + "=" + URLEncoder.encode(pr90, "UTF-8");
            data += "&" + URLEncoder.encode("pr91", "UTF-8") + "=" + URLEncoder.encode(pr91, "UTF-8");
            data += "&" + URLEncoder.encode("pr92", "UTF-8") + "=" + URLEncoder.encode(pr92, "UTF-8");
            data += "&" + URLEncoder.encode("pr93", "UTF-8") + "=" + URLEncoder.encode(pr93, "UTF-8");
            data += "&" + URLEncoder.encode("pr94", "UTF-8") + "=" + URLEncoder.encode(pr94, "UTF-8");
            data += "&" + URLEncoder.encode("pr95", "UTF-8") + "=" + URLEncoder.encode(pr95, "UTF-8");
            data += "&" + URLEncoder.encode("pr96", "UTF-8") + "=" + URLEncoder.encode(pr96, "UTF-8");
            data += "&" + URLEncoder.encode("pr97", "UTF-8") + "=" + URLEncoder.encode(pr97, "UTF-8");
            data += "&" + URLEncoder.encode("pr98", "UTF-8") + "=" + URLEncoder.encode(pr98, "UTF-8");
            data += "&" + URLEncoder.encode("pr99", "UTF-8") + "=" + URLEncoder.encode(pr99, "UTF-8");
            data += "&" + URLEncoder.encode("pr100", "UTF-8") + "=" + URLEncoder.encode(pr100, "UTF-8");
            data += "&" + URLEncoder.encode("pr101", "UTF-8") + "=" + URLEncoder.encode(pr101, "UTF-8");
            data += "&" + URLEncoder.encode("pr102", "UTF-8") + "=" + URLEncoder.encode(pr102, "UTF-8");
            data += "&" + URLEncoder.encode("pr103", "UTF-8") + "=" + URLEncoder.encode(pr103, "UTF-8");
            data += "&" + URLEncoder.encode("pr104", "UTF-8") + "=" + URLEncoder.encode(pr104, "UTF-8");
            data += "&" + URLEncoder.encode("pr105", "UTF-8") + "=" + URLEncoder.encode(pr105, "UTF-8");
            data += "&" + URLEncoder.encode("pr106", "UTF-8") + "=" + URLEncoder.encode(pr106, "UTF-8");
            data += "&" + URLEncoder.encode("pr107", "UTF-8") + "=" + URLEncoder.encode(pr107, "UTF-8");
            data += "&" + URLEncoder.encode("pr108", "UTF-8") + "=" + URLEncoder.encode(pr108, "UTF-8");
            data += "&" + URLEncoder.encode("pr109", "UTF-8") + "=" + URLEncoder.encode(pr109, "UTF-8");
            data += "&" + URLEncoder.encode("pr110", "UTF-8") + "=" + URLEncoder.encode(pr110, "UTF-8");
            data += "&" + URLEncoder.encode("pr111", "UTF-8") + "=" + URLEncoder.encode(pr111, "UTF-8");
            data += "&" + URLEncoder.encode("pr112", "UTF-8") + "=" + URLEncoder.encode(pr112, "UTF-8");
            data += "&" + URLEncoder.encode("pr113", "UTF-8") + "=" + URLEncoder.encode(pr113, "UTF-8");
            data += "&" + URLEncoder.encode("pr114", "UTF-8") + "=" + URLEncoder.encode(pr114, "UTF-8");
            data += "&" + URLEncoder.encode("pr115", "UTF-8") + "=" + URLEncoder.encode(pr115, "UTF-8");
            data += "&" + URLEncoder.encode("pr116", "UTF-8") + "=" + URLEncoder.encode(pr116, "UTF-8");
            data += "&" + URLEncoder.encode("pr117", "UTF-8") + "=" + URLEncoder.encode(pr116, "UTF-8");
            data += "&" + URLEncoder.encode("pr118", "UTF-8") + "=" + URLEncoder.encode(pr116, "UTF-8");
            data += "&" + URLEncoder.encode("pr119", "UTF-8") + "=" + URLEncoder.encode(pr116, "UTF-8");
            data += "&" + URLEncoder.encode("pr120", "UTF-8") + "=" + URLEncoder.encode(pr116, "UTF-8");


            System.out.println("@@@@@@ data = " + data);

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
//            Log.d("tag : ", sb.toString()); // php에서 결과값을 리턴
            System.out.println("@@@@@@ result = " + sb.toString());
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return new String("Exception: " + e.getMessage());
        }
    }
}