package com.example.jjwlzperformancetesting;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private static boolean IS_LTE = false;
    private static boolean IS_5G = false;
    private static final String UNAVAILABLE = "(N/A)";
    private static final String DBM = "dBm";
    private static final String DB = "dB";

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        } else {

            checkRunTimePermission();
        }

        final TextView textview_address = (TextView) findViewById(R.id.locationStr);
        final TextView testview_location = (TextView) findViewById(R.id.locationInfo);


        Button ShowLocationButton = (Button) findViewById(R.id.getLocation);
        ShowLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                gpsTracker = new GpsTracker(MainActivity.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getCurrentAddress(latitude, longitude);
                textview_address.setText(address);

                testview_location.setText("위도: " + latitude + ",  경도: " + longitude);

                Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
            }
        });
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void getNetworkName(View v) {
        // 권한체크, tm.getNetworkType()
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // TODO - debug
            System.out.println("권한체크 에러");
        }

        SignalStatus.getNetworkTypeName(tm.getNetworkType());
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getSignalStatus(View v) {

        String networkType = "";
        int sst = 0;
        int rsrp = 0;
        int rsrq = 0;
        int rssi = 0;
        int rssnr = 0;
        int csiRsrp = 0;
        int csiRsrq = 0;
        int csiSinr = 0;
        int ssRsrp = 0;
        int ssRsrq = 0;
        int ssSinr = 0;
        String signalStatus = "";

        // 권한체크, tm.getNetworkType()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // TODO - debug
            System.out.println("권한체크 에러 - READ_PHONE_STATE - 설정,어플리케이션,앱이름,권한,전화 허");
            return;
        }

        // 권한체크, tm.getAllCellInfo()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // TODO - debug
            System.out.println("권한체크 에러 - ACCESS_FINE_LOCATION");
            return;
        }

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        networkType = SignalStatus.getNetworkTypeName(tm.getNetworkType());
        TextView networkMode = (TextView) findViewById(R.id.networkMode);
        networkMode.setText("Network Mode: " + networkType);

        List<CellInfo> cellInfoList = tm.getAllCellInfo();
        // TODO - debug
        System.out.println("@@@@  " + cellInfoList.size());

        if (cellInfoList != null) {
            for (int i = 0; i < cellInfoList.size(); i++) {
                if (cellInfoList.get(i).isRegistered()) {
                    if (cellInfoList.get(i) instanceof CellInfoWcdma) {
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfoList.get(i);
                        CellSignalStrengthWcdma cs = cellInfoWcdma.getCellSignalStrength();
                        sst = cs.getDbm();
                    } else if (cellInfoList.get(i) instanceof CellInfoGsm) {
                        CellInfoGsm cellInfogsm = (CellInfoGsm) cellInfoList.get(i);
                        CellSignalStrengthGsm cs = cellInfogsm.getCellSignalStrength();
                        sst = cs.getDbm();
                    } else if (cellInfoList.get(i) instanceof CellInfoCdma) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfoList.get(i);
                        CellSignalStrengthCdma cs = cellInfoCdma.getCellSignalStrength();
                        sst = cs.getDbm();
                    } else if (cellInfoList.get(i) instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfoList.get(i);
                        CellSignalStrengthLte cs = cellInfoLte.getCellSignalStrength();
                        sst = cs.getDbm();
                        signalStatus = getSignalStatusLTE(cs);
                    } else if (cellInfoList.get(i) instanceof CellInfoNr) {
                        CellInfoNr cellInfoNr = (CellInfoNr) cellInfoList.get(i);
                        CellSignalStrengthNr cs = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();
                        sst = cs.getDbm();
                        signalStatus = getSignalStatusNR(cs);
                    }
                }
            }
        }

        TextView signalStrength = (TextView) findViewById(R.id.signalStrength);
        signalStrength.setText(String.valueOf("Signal Strength: " + sst + " " + DBM));

        System.out.println("Signal Status :" + signalStatus);
        TextView signalStatusStr = (TextView) findViewById(R.id.signalStatusStr);
        signalStatusStr.setText(signalStatus);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String getSignalStatusLTE (CellSignalStrengthLte cs) {

        StringBuilder sb = new StringBuilder();
        sb.append("RSRP/Rssi/RSRQ/Rssnr: ");

        sb.append(cs.getRsrp() + " " + DBM);

        sb.append("/");
        if (isUnavailable(cs.getRssi())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getRssi() + " " + DBM);
        }

        sb.append("/");
        if (isUnavailable(cs.getRsrq())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getRsrq());
        }

        sb.append("/");
        if (isUnavailable(cs.getRssnr())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getRssnr());
        }
        return sb.toString();

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String getSignalStatusNR (CellSignalStrengthNr cs) {

        StringBuilder sb = new StringBuilder();
        sb.append("CSI RSRP/RSRQ/SINR: ");

        if (isUnavailable(cs.getCsiRsrp())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getCsiRsrp() + " " + DBM);
        }

        sb.append("/");
        if (isUnavailable(cs.getCsiRsrq())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getCsiRsrq() + " " + DB);
        }

        if (isUnavailable(cs.getCsiSinr())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getCsiSinr() + " " + DB);
        }

        sb.append("\\r\\n");
        sb.append("SS RSRP/RSRQ/SINR: ");

        if (isUnavailable(cs.getSsRsrp())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getSsRsrp() + " " + DBM);
        }

        sb.append("/");
        if (isUnavailable(cs.getSsRsrq())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getSsRsrq() + " " + DB);
        }

        if (isUnavailable(cs.getSsSinr())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getSsSinr() + " " + DB);
        }
        return sb.toString();
    }

    private static boolean isUnavailable(int i) {
        if (i==2147483647) {
            return true;
        } else {
            return false;
        }
    }
    
    public void executeCmd(View v){

        System.out.println("@@실행됨");

        TextView testResult = (TextView) findViewById(R.id.testResult);

        String str = "ping -A -W 50 -c 2 223.62.93.226";
//        String str = "netperf -H 223.62.93.226 -l 100 -t TCP_RR -v 2 -- -o min_latency,mean_latency,max_latency,stddev_latency,transaction_rate";

        ShellExecutor se = new ShellExecutor();
        testResult.setText(se.execute(str));

    }

    public void executeCmdInstall (View v) throws IOException {

        String fileName = "iperf3";
        String fullName  = "/data/user/0/com.example.jjwlzperformancetesting/files/iperf3";
        String commandStr = "";
        ShellExecutor se = new ShellExecutor();

        // app/assets/iperf3파일을 핸드폰으로 복사
        copyAssets(fileName);

        // 파일권한 변경 "chmod 755 /data/user/0/com.example.jjwlzperformancetesting/files/iperf3"
        se.execute("chmod 755 " + fullName);

        // iperf3 실행 "/data/user/0/com.example.jjwlzperformancetesting/files/iperf3  -c 223.62.93.226  udp -b 1G -t 2 –J -R"
        commandStr = fullName + " -c 223.62.93.226  udp -b 1G -t 2 –J -R";
        System.out.println(se.execute(commandStr));

    }

    private void copyAssets(String filename) throws IOException {

        String appFileDirectory = getFilesDir().getPath();
        String executableFilePath = appFileDirectory + "/iperf3";

        AssetManager assetManager = getAssets();

        InputStream is = null;
        OutputStream os = null;
        Log.d("", "Attempting to copy this file: " + filename); // + " to: " +       assetCopyDestination);

        try {
            is = assetManager.open(filename);
            Log.d("", "outDir: " + appFileDirectory);
            File outFile = new File(appFileDirectory, filename);
            System.out.println("@@@@@@@@@@@@:  " +outFile.getAbsolutePath());
            os = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch(IOException e) {
            Log.e("", "Failed to copy asset file: " + filename, e);
        } finally {
            is.close();
            is = null;
            os.flush();
            os.close();
            os = null;
        }

        Log.d("", "Copy success: " + filename);
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}